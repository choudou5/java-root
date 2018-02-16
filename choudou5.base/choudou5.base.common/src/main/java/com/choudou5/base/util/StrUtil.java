package com.choudou5.base.util;


import org.apache.commons.lang3.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Name：字符串 工具类
 * @Author：xuhaowende
 * @Date：2018-02-16
 */
public class StrUtil extends cn.hutool.core.util.StrUtil {


    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";


    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)){
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html){
        if (html == null){
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int currentLength = 0;
        for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
            currentLength++;
            if (currentLength <= length - 3) {
                sb.append(c);
            } else {
                sb.append("...");
                break;
            }
        }
        return sb.toString();
    }


    /**
     * 驼峰命名法 "hello_world" ==> "helloWorld"
     * @return
     */
    public static String toCamelCase(String s) {
        if (s == null)
            return null;
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名法 "hello_world" ==> "HelloWorld"
     * @return
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null)
            return null;
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法 "helloWorld" ==> "hello_world"
     * @return
     */
    public static String toUnderScoreCase(String s) {
        if (s == null)
            return null;
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }


    /**
     * 拼接 非空数组 成字符串
     * @param separator
     * @param array
     * @return
     */
    public static String joinIgnoreBlank(final String separator, final String ... array) {
        if (array == null) {
            return null;
        }
        final int noOfItems = array.length;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = 0; i < noOfItems; i++) {
            if (isNotBlank(array[i])) {
                if (i > 0)
                    buf.append(separator);
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 拼接 非空数组 成字符串
     * @param separator
     * @param array
     * @return
     */
    public static String joinIgnoreNull(final String separator, final Object ... array) {
        if (array == null) {
            return null;
        }
        final int noOfItems = array.length;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = 0; i < noOfItems; i++) {
            if (array[i] != null) {
                if (i > 0)
                    buf.append(separator);
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

}
