package com.admin.junit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-1-27 下午3:30:51
 */
public class MainTest {

	private static int x = 0;
	private final static MainTest main = new MainTest();
	private static Lock lock = new ReentrantLock();
	public static MainTest getMain(){
		return main;
	}
	public static void main(String[] args) {
		//String str = "brrow.zip";
		//String extension = FilenameUtils.getExtension(str);
		//System.out.println(extension);
		//System.out.println(Math.pow(2, 31));
		MainTest main = new MainTest();
		System.out.println(main.getClass().getSimpleName());
		/*for(int i=0;i<10;i++){
			Thread t = new Thread(){
				public void run() {
					MainTest.getMain().add();
				};
			};
			t.start();
		}*/
		//String json = "{"id":"","costName":"出差报销","amount":"1500","description":"","itemList":[{"itemName":"车费","category":"车补","amount":"1000","operation":""},{"itemName":"住宿飞","category":"","amount":"500","operation":""}]}"
	}
	
	public void add(){
		//lock.lock();
		try {
			//lock.lockInterruptibly();
			if(lock.tryLock(2,TimeUnit.SECONDS)){//获取锁成功,（等待5秒时间，如果还获取不到锁就会走else）
				x++;
				System.out.println("结果："+x);
				
				//Thread.sleep(100000);
				/*synchronized (test) {
					System.out.println(Thread.currentThread().getName()+"进入");
					test.wait();
				}*/
				lock.unlock();
			}else{
				System.out.println(Thread.currentThread().getName()+"获取锁失败。。。");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("------------------");
		}
	}
}
