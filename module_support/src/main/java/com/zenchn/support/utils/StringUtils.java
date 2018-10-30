package com.zenchn.support.utils;


import com.zenchn.support.kit.RegexKit;

/**
 * 作    者：wangr on 2017/4/24 11:05
 * 描    述：字符串相关工具类
 * 修订记录：
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断一个字符串有内容(非空且非空字符串)
     *
     * @param charSequence
     * @return
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    /**
     * 判断多个字符串有内容(非空且非空字符串)
     *
     * @param charSequences
     * @return
     */
    public static boolean isEmpty(CharSequence... charSequences) {
        for (CharSequence charSequence : charSequences) {
            if (isEmpty(charSequence))
                return true;
        }
        return false;
    }

    /**
     * 判断是否是网址
     *
     * @param charSequences
     * @return
     */
    public static boolean isIDCARD(CharSequence charSequences) {
        return isNonNull(charSequences) && (charSequences.toString().matches(RegexKit.REGEX_ID_CARD15)||(charSequences.toString().matches(RegexKit.REGEX_ID_CARD18)));
    }

    /**
     * 判断是否是网址
     *
     * @param charSequences
     * @return
     */
    public static boolean isURL(CharSequence charSequences) {
        return isNonNull(charSequences) && charSequences.toString().matches(RegexKit.REGEX_URL);
    }


    /**
     * 判断一个字符串有内容(非空且非空字符串)
     *
     * @param charSequence
     * @return
     */
    public static boolean isNonNull(CharSequence charSequence) {
        return null != charSequence && charSequence.length() > 0;
    }

    /**
     * 判断一列字符串有内容(非空且非空字符串)
     *
     * @param charSequences
     * @return
     */
    public static boolean isNonNull(CharSequence... charSequences) {
        for (CharSequence charSequence : charSequences) {
            if (isEmpty(charSequence))
                return false;
        }
        return true;
    }

    /**
     * 对字符串进行非空处理
     *
     * @param charSequences
     * @return
     */
    public static CharSequence getNonNull(CharSequence charSequences) {
        return isNonNull(charSequences) ? charSequences : "";
    }

    /**
     * 对字符串进行非空处理
     *
     * @param charSequences
     * @param defaultCharSequences
     * @return
     */
    public static CharSequence getNonNull(CharSequence charSequences, CharSequence defaultCharSequences) {
        return isNonNull(charSequences) ? charSequences : defaultCharSequences;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param character 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String character) {
        return (character == null || character.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param aCharacter 待校验字符串a
     * @param bCharacter 待校验字符串b
     */
    public static boolean equalsIgnoreCase(String aCharacter, String bCharacter) {
        return (aCharacter == bCharacter) || (aCharacter != null) && (aCharacter.length() == bCharacter.length()) && aCharacter.regionMatches(true, 0, bCharacter, 0, bCharacter.length());
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int getNonNullLength(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = getNonNullLength(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }
}