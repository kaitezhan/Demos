/***
 * Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com)
 ***/
package com.boneix.base.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class StringUtils {
    public static final String EMPTY = "";

    public static final int INDEX_NOT_FOUND = -1;

    public static final String LEFT_WHITESPACE_REGEX = "^\\s+";

    public static final String RIGHT_WHITESPACE_REGEX = "\\s+$";

    public static final String WHITESPACE_REGEX = "\\s+";

    public static final int INT_UPPER_A = 65;

    public static final int INT_LOWER_A = 97;

    public static final int INT_UPPER_Z = 90;

    public static final int INT_LOWER_Z = 122;

    public static String trimLeft(String str) {
        return ((null == str) ? str : str.replaceAll("^\\s+", ""));
    }

    public static String trimRight(String str) {
        return ((null == str) ? str : str.trim());
    }

    public static boolean isEmpty(CharSequence cs) {
        return ((null == cs) || (0 == cs.length()));
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return ((null != cs) && (0 != cs.length()));
    }

    public static boolean isBlank(CharSequence cs) {
        if (isEmpty(cs)) {
            return true;
        }

        for (int index = 0; index < cs.length(); ++index) {
            if (false == Character.isWhitespace(cs.charAt(index))) {
                return false;
            }

        }

        return true;
    }

    public static String trim(String str) {
        return trimRight(str);
    }

    public static String trimBoth(String str) {
        return trimLeft(trimRight(str));
    }

    public static final int stringDifference(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int len = (len1 < len2) ? len1 : len2;
        for (int i = 0; i < len; ++i) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return i;
            }
        }
        return len;
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        if ((null == str1) || (null == str2)) {
            return (str1 == str2);
        }

        int length = Math.max(str1.length(), str2.length());
        if ((str1 instanceof String) && (str2 instanceof String)) {
            return ((String) str1).regionMatches(true, 0, (String) str2, 0, length);
        }
        return str1.toString().regionMatches(true, 0, str2.toString(), 0, length);
    }

    public static boolean equalsIgnoreCase2(CharSequence str1, CharSequence str2) {
        if ((null == str1) || (null == str2)) {
            return (str1 == str2);
        }

        return upperCase(str1).equals(upperCase(str2));
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return ((cs1 == null) ? false : (cs2 == null) ? true : cs1.equals(cs2));
    }

    public static int indexOf(CharSequence cs, int search) {
        return indexOf(cs, search, 0);
    }

    public static int indexOf(CharSequence cs, int search, int start) {
        if (isEmpty(cs)) {
            return -1;
        }

        return cs.toString().indexOf(search, start);
    }

    public static int indexOf(CharSequence cs, CharSequence search) {
        return indexOf(cs, search, 0);
    }

    public static int indexOf(CharSequence cs, CharSequence search, int start) {
        if (isEmpty(cs)) {
            return -1;
        }

        return cs.toString().indexOf(search.toString(), start);
    }

    public static int lastIndexOf(CharSequence cs, int search) {
        return lastIndexOf(cs, search, 0);
    }

    public static int lastIndexOf(CharSequence cs, int search, int start) {
        if (isEmpty(cs)) {
            return -1;
        }

        return cs.toString().lastIndexOf(search, start);
    }

    public static int lastIndexOf(CharSequence cs, CharSequence search) {
        return lastIndexOf(cs, search, 0);
    }

    public static int lastIndexOf(CharSequence cs, CharSequence search, int start) {
        if (isEmpty(cs)) {
            return -1;
        }

        return cs.toString().lastIndexOf(search.toString(), start);
    }

    public static boolean contains(CharSequence cs, int search) {
        if (isEmpty(cs)) {
            return false;
        }

        return (cs.toString().indexOf(search) != -1);
    }

    public static boolean constains(CharSequence cs, CharSequence search) {
        if (isEmpty(cs)) {
            return false;
        }

        return cs.toString().contains(search);
    }

    public static boolean containsIgnoreCase(CharSequence cs, CharSequence search) {
        if (isEmpty(cs)) {
            return false;
        }

        if (null == search) {
            throw new NullPointerException();
        }

        return upperCase(cs).contains(upperCase(search));
    }

    public String substring(String str, int start) {
        return str.substring(start);
    }

    public String substring(String str, int start, int end) {
        return str.substring(start, end);
    }

    public String left(String str, int length) {
        if (null == str) {
            return null;
        }

        if (length < 0) {
            return "";
        }

        return str.substring(0, length);
    }

    public String right(String str, int length) {
        if (null == str) {
            return null;
        }

        if (length < 0) {
            return "";
        }

        int len = str.length();
        if (length > len) {
            return str;
        }

        return str.substring(len - length);
    }

    public String substringBefore(String str, String separator) {
        if ((isEmpty(str)) || (isEmpty(separator))) {
            return str;
        }

        int end = indexOf(str, separator);
        if (end == -1) {
            return str;
        }

        return str.substring(0, end);
    }

    public String substringAfter(String str, String separator) {
        if ((isEmpty(str)) || (isEmpty(separator))) {
            return str;
        }

        int start = indexOf(str, separator);
        if (start == -1) {
            return str;
        }

        int length = separator.length();

        return str.substring(start + length);
    }

    public static int length(CharSequence cs) {
        return ((cs == null) ? 0 : cs.length());
    }

    public static String upperCase(String str) {
        return ((null == str) ? null : str.toUpperCase());
    }

    public static String lowerCase(String str) {
        return ((null == str) ? null : str.toLowerCase());
    }

    public static String upperCase(String str, Locale locale) {
        return ((null == str) ? null : str.toUpperCase(locale));
    }

    public static String lowerCase(String str, Locale locale) {
        return ((null == str) ? null : str.toUpperCase(locale));
    }

    public String reverse(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();
    }

    public boolean startWith(CharSequence cs1, CharSequence cs2) {
        return startWith(cs1, cs2, false);
    }

    public boolean startWithIgnoreCase(CharSequence cs1, CharSequence cs2) {
        return startWith(cs1, cs2, true);
    }

    public boolean startWith(CharSequence cs1, CharSequence cs2, boolean isIgnoreCase) {
        if ((cs1 == null) || (cs2 == null)) {
            return ((cs1 == null) && (cs2 == null));
        }
        if (cs2.length() > cs1.length()) {
            return false;
        }

        if ((cs1 instanceof String) && (cs2 instanceof String)) {
            return ((String) cs1).regionMatches(isIgnoreCase, 0, (String) cs2, 0, cs2.length());
        }
        return cs1.toString().regionMatches(isIgnoreCase, 0, cs2.toString(), 0, cs2.length());
    }

    public boolean endWith(CharSequence cs1, CharSequence cs2) {
        return endWith(cs1, cs2, false);
    }

    public boolean endWithIgnoreCase(CharSequence cs1, CharSequence cs2) {
        return endWith(cs1, cs2, true);
    }

    public boolean endWith(CharSequence cs1, CharSequence cs2, boolean isIgnoreCase) {
        if ((cs1 == null) || (cs2 == null)) {
            return ((cs1 == null) && (cs2 == null));
        }
        if (cs2.length() > cs1.length()) {
            return false;
        }

        int start = cs1.length() - cs2.length();
        if ((cs1 instanceof String) && (cs2 instanceof String)) {
            return ((String) cs1).regionMatches(isIgnoreCase, start, (String) cs2, 0, cs2.length());
        }
        return cs1.toString().regionMatches(isIgnoreCase, start, cs2.toString(), 0, cs2.length());
    }

    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                chs[(count++)] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    public String toString(byte[] buf)
            throws UnsupportedEncodingException {
        return toString(buf, null);
    }

    public String toString(byte[] buf, String charset)
            throws UnsupportedEncodingException {
        return new String(buf, charset);
    }

    public String replace(String str, char oldChar, char newChar) {
        if ((isEmpty(str)) || (oldChar == newChar)) {
            return str;
        }

        return str.replace(oldChar, newChar);
    }

    public static String replace(String str, String oldString, String newString) {
        if ((isEmpty(str)) || (isEmpty(oldString)) || (isEmpty(newString))) {
            return str;
        }

        return str.replace(oldString, newString);
    }

    public static String replace(String str, String oldString, String newString, int count) {
        String ret = str;
        if ((isEmpty(str)) || (isEmpty(oldString)) || (isEmpty(newString))) {
            return str;
        }

        int appear = appearNumber(str, oldString);
        int min = (count > appear) ? appear : count;

        if (count <= 0) {
            return str;
        }

        for (int cycle = 0; cycle < min; ++cycle) {
            ret = replace(str, oldString, newString);
        }

        return ret;
    }

    public static int appearNumber(CharSequence str, CharSequence sub) {
        if ((isEmpty(str)) || (isEmpty(sub))) {
            return 0;
        }
        int num = 0;
        int pos = 0;
        while (-1 != (pos = str.toString().indexOf(sub.toString(), pos))) {
            ++num;
            pos += sub.length();
        }
        return num;
    }

    public static String remove(String str, String remove) {
        return replace(str, remove, "");
    }

    public static String[] split(String str, String regex) {
        if (isEmpty(str)) {
            String[] ret = {
                    str
            };
            return ret;
        }

        return str.split(regex);
    }

    private static String upperCase(CharSequence str) {
        StringBuffer sb = new StringBuffer();
        if (null == str) {
            return null;
        }

        for (int index = 0; index < str.length(); ++index) {
            char temp = str.charAt(index);
            if (('a' < temp) && ('z' > temp))
                sb.append((char) (temp + 'A' - 97));
            else {
                sb.append(temp);
            }
        }
        return sb.toString();
    }
}