package com.lfc.textx5.utils;

import android.util.Log;

/**
 * LogUtils工具说明:
 * 1 只输出等级大于等于LEVEL的日志
 * 所以在开发和产品发布后通过修改LEVEL来选择性输出日志.
 * 当LEVEL=NOTHING则屏蔽了所有的日志.
 * 2 v,d,i,w,e均对应两个方法.
 * 若不设置TAG或者TAG为空则为设置默认TAG
 */
public class LgU {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int LEVEL = VERBOSE;
    public static final String Tag = "TextX5";

    public static void v(String message) {
        if (LEVEL <= VERBOSE) {
            Log.v(Tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void d(String message) {
        if (LEVEL <= DEBUG) {
            Log.i(Tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (LEVEL <= DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void i(String message) {
        if (LEVEL <= INFO) {
            Log.i(Tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {

            Log.i(tag, message);
        }
    }

    public static void w(String message) {
        if (LEVEL <= WARN) {

            Log.w(Tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {

            Log.e(tag, message);
        }
    }

    public static void e(String s) {
        if (LEVEL <= WARN) {

            Log.e(Tag, s);
        }
    }

    private static int LENGTH = 4000;

    /**
     * 显示更多 log
     *
     * @param msg
     */
    public static void more(String msg) {
        String tag = Tag;
        if (LEVEL <= DEBUG) {
            if (msg.length() > LENGTH) {
                for (int i = 0; i < msg.length(); i += LENGTH) {
                    if (i + LENGTH < msg.length()) {
                        Log.i(tag, msg.substring(i, i + LENGTH));
                    } else {
                        Log.i(tag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void more(String tag, String msg) {

        if (LEVEL <= DEBUG) {
            if (msg.length() > LENGTH) {
                for (int i = 0; i < msg.length(); i += LENGTH) {
                    if (i + LENGTH < msg.length()) {
                        Log.i(tag, msg.substring(i, i + LENGTH));
                    } else {
                        Log.i(tag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                Log.i(tag, msg);
            }
        }
    }

}