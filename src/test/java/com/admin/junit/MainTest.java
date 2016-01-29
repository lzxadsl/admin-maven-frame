package com.admin.junit;

import org.apache.commons.io.FilenameUtils;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-1-27 下午3:30:51
 */
public class MainTest {

	public static void main(String[] args) {
		String str = "brrow.zip";
		String extension = FilenameUtils.getExtension(str);
		System.out.println(extension);
	}
}
