package com.admin.junit;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FilenameUtils;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-1-27 下午3:30:51
 */
public class MainTest {

	private int x = 0;
	Lock lock = new ReentrantLock();
	public static void main(String[] args) {
		String str = "brrow.zip";
		String extension = FilenameUtils.getExtension(str);
		System.out.println(extension);
	}
	
	public void add(){
		
		x++;
		System.out.println("结果："+x);
	}
}
