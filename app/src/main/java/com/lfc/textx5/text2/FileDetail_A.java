package com.lfc.textx5.text2;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lfc.textx5.R;
import com.lfc.textx5.text2.view.Md5Tool;
import com.lfc.textx5.text2.view.WebView_X5;
import com.lfc.textx5.utils.LgU;
import com.lfc.textx5.utils.PreferencesUtils;
import com.lfc.textx5.utils.TimeUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 文件详情
 *
 * @author Administrator-LFC
 * created at 2018/8/4 14:48
 */


public class FileDetail_A extends AppCompatActivity {


    private Context baseContext;
    String filePath;
    private ProgressDialog dialog_pross;
    private RelativeLayout mActivityFileDisplay;
    private WebView_X5 mWebX5;
    private long mTaskId = 0;//下载ID
    private DownloadManager downloadManager;
    private TextView mTvTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_detail);
        baseContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            filePath = (String) intent.getSerializableExtra("path");
        }
        initView();
    }

    public static void EnterThis(Context context, String url) {
        // TODO: 2018/8/4 自行添加权限判断 不然GG
        Intent intent = new Intent(context, FileDetail_A.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("path", url);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    private void initView() {
        mActivityFileDisplay = (RelativeLayout) findViewById(R.id.activity_file_display);
        mWebX5 = (WebView_X5) findViewById(R.id.web_x5);
        dialog_pross = new ProgressDialog(baseContext);

        mWebX5.setOnGetFilePathListener(new WebView_X5.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(WebView_X5 mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });
        if (!TextUtils.isEmpty(filePath)) {
            LgU.d("文件path:" + filePath);
            setFilePath(filePath);
        }
        mWebX5.show();
        mTvTop = (TextView) findViewById(R.id.tv_top);
        mTvTop.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(baseContext, "ok", Toast.LENGTH_SHORT).show();
                mTvTop.setVisibility(View.GONE);
                return false;
            }
        });
    }

    public void setFilePath(String fileUrl) {
        this.filePath = fileUrl;
    }

    /**
     * 获取文件路径 并展示文件
     *
     * @param mSuperFileView2
     */
    private void getFilePathAndShowFile(WebView_X5 mSuperFileView2) {


        if (getFilePath().contains("http")) {//网络地址要先下载

            downLoadFromNet(getFilePath(), mSuperFileView2);

        } else {
            mSuperFileView2.displayFile(new File(getFilePath()));
        }
    }

    private void downLoadFromNet(final String url, final WebView_X5 mSuperFileView2) {
        //1.网络下载、存储路径、
        File cacheFile = getCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                LgU.d("删除空文件！！");
                cacheFile.delete();
                return;
            } else {
//                如果文件存在 且文件大小不为空  根据时间 判断打开本地还是网络
//                1  本地 2 网络
                int opeType = getOpenType(cacheFile);
                if (opeType == 1) {
//                    直接展示本地文件
                    mWebX5.displayFile(cacheFile);
                    return;
                } else {
//                    网络文件  先删除本地  然后下载展示
                    cacheFile.delete();
                }
            }

        }
// 测试下 到底成功没


        if (dialog_pross != null) {
            dialog_pross.setTitle("拼命加载中...");
            dialog_pross.show();
        }


        DownloadManager.Request request_down = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        String str_filename = getCacheFile(url).getName().toString().trim();
        LgU.d("--lfc", "文件名称： " + str_filename);
// TODO: 2018/8/30  这里需要权限处理

        request_down.setDestinationInExternalPublicDir("/WebX5/", str_filename);
        request_down.setAllowedOverRoaming(true);//漫游网络是否可以下载
        /**设置通知栏是否可见*/
        request_down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//获取下载管理器
        downloadManager = (DownloadManager) baseContext.getSystemService(Context.DOWNLOAD_SERVICE);

//将下载任务加入下载队列，否则不会进行下载
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        mTaskId = downloadManager.enqueue(request_down);
        //注册广播接收者，监听下载状态
        registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


    }

    /**
     * 根据时间判断
     *
     * @param cacheFile
     * @return 1  本地 2 网络
     */
    private int getOpenType(File cacheFile) {
        // TODO: 2018/8/4  粗略保证一定时间间隔内 读取sd文件 而不是重复获取网络文件
        // TODO: 2018/8/4  记录下打开app 应用的时间 如果文件最后修改时间 在打开app之前 则需要删除并新建文件
        // TODO: 2018/8/4  判断文件写入时间 如果超过时间差则删除并新建文件 否则继续使用原始文件

        int type = 2;
//        先判断 是否 APP打开时间 和文件最后修改时间
//                                如果 是在应用打开的时间创建的文件  则判断文件时效性
        long AppTime = PreferencesUtils.getLong(baseContext, "APPStartTime");
        long FileTime = cacheFile.lastModified();
        LgU.d("AppTime: " + AppTime + " FileTime : " + FileTime);
        if (FileTime < AppTime) {
//            app启动后首次加载该文件 使用网络文件
            type = 2;
        } else {
            double timeout = mWebX5.getTimeOut();
            long timeoutL = (long) (timeout * 60 * 60 * 1000); //单位毫秒

            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            String ee = dff.format(new Date());
            LgU.i("当前时间： " + ee);
            long nowtime = TimeUtils.dateToStamp(ee);
            if (nowtime - FileTime < timeoutL) {
//                当前查看时间 在 超时时间内 查看本地文件
                type = 1;
            } else
                type = 2;
        }
        return type;
    }

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    LgU.i(">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    LgU.i(">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    LgU.i(">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    LgU.i(">>>下载完成");
//                    Toast.makeText(baseContext, "下载完成", Toast.LENGTH_SHORT).show();
                    File fileN = getCacheFile(getFilePath());//new File(getCacheDir(url), getFileName(url))
                    if (fileN.exists())
                        mWebX5.displayFile(fileN);
                    if (dialog_pross != null && dialog_pross.isShowing()) {
                        dialog_pross.cancel();
                    }
                    break;
                case DownloadManager.STATUS_FAILED:
                    LgU.i(">>>下载失败");
                    break;
            }
        }
    }


    /**
     * 多正式 QAQ
     *
     * @return
     */
    private String getFilePath() {
        return filePath;
    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private File getCacheFile(String url) {
        File cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WebX5/"
                + getFileName(url));
        LgU.d("缓存文件 = " + cacheFile.toString());
        return cacheFile;
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */

    private String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        return fileName;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            LgU.d("paramString---->null");
            return str;
        }
        LgU.d("paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            LgU.d("i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        LgU.d("paramString.substring(i + 1)------>" + str);
        return str;
    }

    @Override
    public void onDestroy() {
        LgU.d("FileDisplayActivity-->onDestroy");

        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mWebX5 != null) {
            mWebX5.onStopDisplay();
        }
        super.onDestroy();
    }


}
