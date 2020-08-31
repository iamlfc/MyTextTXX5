# MyTextTXX5（学习交流自用）
tbs x5 webview 打开office 文件(毕竟IOS webview自带这种功能，android 一句实现不了 很尴尬！)

原理：实际是把文件下载之后 用webview 打开（原生webview 好像不具备这个）

ps：demo中没有添加权限判断 自行打开权限判断或手动 在设置中同意存储权限

新增：2020年8月31日16:48:47

 1 优化的设置是在初始化之前的
 
 2 初始化的时候 必须是在获取内存读写权限之后，否则本次初始化失败 下载启动才生效
 
内部原理 可以看腾讯X5内核 官方文档 https://x5.tencent.com/tbs/guide/sdkInit.html

参考：https://github.com/ZhongXiaoHong/superFileView
                                                                                 
