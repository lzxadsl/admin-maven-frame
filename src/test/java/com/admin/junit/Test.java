package com.admin.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-1 上午8:52:08
 */
public class Test {

	public Test(){
		System.out.println("Test被创建.......");
		InputStream in;
		try {
			in = new FileInputStream(new File(""));
			Reader r = new InputStreamReader(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
