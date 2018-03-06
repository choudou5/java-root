package com.choudou5.base.util;

/**
 * 日志打印 工具类
 */
public class LogPrintUtil {

    private static final String LOG_SPLIT = "__";
    private static boolean needEnableLogger = false;

    public static void setNeedEnableLogger(boolean needEnableLogger) {
        LogPrintUtil.needEnableLogger = needEnableLogger;
    }

    public static void debug(String message, Object ... params) {
        if (!needEnableLogger) {
            return;
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append(DateUtil.now());// 时间
        sb.append(LOG_SPLIT);
        sb.append(message);
        if(ArrayUtil.isNotEmpty(params)){
            sb.append(LOG_SPLIT);
            for (Object param : params) {
                sb.append(param);
            }
        }
        System.out.println(sb.toString());
    }

}