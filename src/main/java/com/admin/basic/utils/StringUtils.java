package com.admin.basic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author lzx
 * @version 1.0
 * @date 下午3:00:38
 */
public class StringUtils {

	/**
	 * 判断字符串为空
	 */
	public static boolean isEmpty(String str){
		return ((str == null) || (str.length() == 0));
    }
	/**
	 * 判断字符串不为空
	 */
    public static boolean isNotEmpty(String str){
    	return (!(isEmpty(str)));
    }
    
    /**
	 * 转化字段首字母为大写  
	 * @param fieldName 字段名
	 */
    public static String UpperCaseField(String fieldName) {  
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName  
                .substring(0, 1).toUpperCase());  
        return fieldName;  
    } 
    
    /**
	 * 转化字段首字母为小写 
	 * @param fieldName 字段名.
	 *  @return String.
	 */
    public static String LowerCaseField(String fieldName) {  
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName  
                .substring(0, 1).toLowerCase());  
        return fieldName;  
    } 
    
	/**
	 * 不足多少位时，在字符串前补空格
	 * @param s 字符串
	 * @param length 结果长度
	 * @return String
	 */
	public static String prepad(String s, int length){
		return prepad(s, length, ' ');
	}

	/**
	 * 不足多少位时，在字符串前补相应的字符
	 * @param s 字符串
	 * @param length 结果长度
	 * @param c 字符
	 * @return String
	 */
	public static String prepad(String s, int length, char c){
		int needed = length - s.length();
		if (needed <= 0){
			return s;
		}
		char padding[] = new char[needed];
		java.util.Arrays.fill(padding, c);
		StringBuffer sb = new StringBuffer(length);
		sb.append(padding);
		sb.append(s);
		return sb.toString();
	}
	
	/**
	 * 不足多少位时，在字符串后补空格
	 * @param s 字符串
	 * @param length 结果长度
	 * @return String
	 */
	public static String postpad(String s, int length){
		return postpad(s, length, ' ');
	}

	/**
	 * 不足多少位时，在字符串后补相应的字符
	 * @param s 字符串
	 * @param length 结果长度
	 * @param c 字符
	 * @return String
	 */
	public static String postpad(String s, int length, char c){
		int needed = length - s.length();
		if (needed <= 0){
			return s;
		}
		char padding[] = new char[needed];
		java.util.Arrays.fill(padding, c);
		StringBuffer sb = new StringBuffer(length);
		sb.append(s);
		sb.append(padding);
		return sb.toString();
	}
	
	/**
	 * 数组转字符串，连接符是空格
	 * @param array 字符串数组
	 * @return String
	 */
	public static String join(String[] array){
		return join(array, "");
	}

	/**
	 * 数组按指定连接符转字符串
	 * @param array 字符串数组
	 * @param delimiter 连接符
	 * @return String
	 */
	public static String join(String[] array, String delimiter){
		int delimiterLength = delimiter.length();
		if (array.length == 0) return "";
		if (array.length == 1){
			if (array[0] == null) return "";
			return array[0];
		}
		int length = 0;
		for (int i=0; i<array.length; i++){
			if (array[i] != null) length+=array[i].length();
			if (i<array.length-1) length+=delimiterLength;
		}

		StringBuffer result = new StringBuffer(length);
		for (int i=0; i<array.length; i++){
			if (array[i] != null) result.append(array[i]);
			if (i<array.length-1) result.append(delimiter);
		}

		return result.toString();
	}
	
	/**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
    
    /**
     * 判断字符是否是中文
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

	/**
	 * HTML转换
	 * @param s HTML字符串
	 * @return String
	 */
	public static String escapeHTML(String s){
		int length = s.length();
		int newLength = length;
		boolean someCharacterEscaped = false;
		// first check for characters that might
		// be dangerous and calculate a length
		// of the string that has escapes.
		for (int i=0; i<length; i++){
			char c = s.charAt(i);
			int cint = 0xffff & c;
			if (cint < 32){
				switch(c){
					case '\r':
					case '\n':
					case '\t':
					case '\f':{
					} break;
					default: {
						newLength -= 1;
						someCharacterEscaped = true;
					}
				}
			} else {
				switch(c){
					case '\"':{
						newLength += 5;
						someCharacterEscaped = true;
					} break;
					case '&':
					case '\'':{
						newLength += 4;
						someCharacterEscaped = true;
					} break;
					case '<':
					case '>':{
						newLength += 3;
						someCharacterEscaped = true;
					} break;
				}
			}
		}
		if (!someCharacterEscaped){
			// nothing to escape in the string
			return s;
		}
		StringBuffer sb = new StringBuffer(newLength);
		for (int i=0; i<length; i++){
			char c = s.charAt(i);
			int cint = 0xffff & c;
			if (cint < 32){
				switch(c){
					case '\r':
					case '\n':
					case '\t':
					case '\f':{
						sb.append(c);
					} break;
					default: {
						// Remove this character
					}
				}
			} else {
				switch(c){
					case '\"':{
						sb.append("&quot;");
					} break;
					case '\'':{
						sb.append("&#39;");
					} break;
					case '&':{
						sb.append("&amp;");
					} break;
					case '<':{
						sb.append("&lt;");
					} break;
					case '>':{
						sb.append("&gt;");
					} break;
					default: {
						sb.append(c);
					}
				}
			}
		}
		return sb.toString();
	}
}
