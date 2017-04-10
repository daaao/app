package io.zhijian.utils;

/**
 * @author Hao
 * @create 2017-04-10
 */
public class StringUtils {
    /**
     * 逗号相隔的字符串转Integer数组
     *
     * @param str
     * @return
     */
    public static Integer[] toInts(String str) {
        return toInts(str, ",");
    }

    /**
     * 根据分隔符把字符串转为Integer数组
     *
     * @param str
     * @param separator
     * @return
     */
    public static Integer[] toInts(String str, String separator) {
        if (str != null && str.trim().length() > 0) {
            String temp = trimComma(str);
            String[] strs = org.apache.commons.lang3.StringUtils.split(temp, separator);
            if (strs != null && strs.length > 0) {
                Integer[] ids = new Integer[strs.length];
                for (int i = 0; i < strs.length; i++) {
                    String s = strs[i];
                    if (s != null && s.trim().length() > 0) {
                        ids[i] = Integer.parseInt(strs[i]);
                    }
                }
                return ids;
            }
        }
        return null;
    }

    /**
     * 去掉字符串首尾逗号
     *
     * @param str
     * @return
     */
    public static String trimComma(String str) {
        String regex = "^,*|,*$";
        return str.replaceAll(regex, "");
    }
}
