package test.org.abin.core.util;

import java.util.Date;

import org.abin.core.util.HttpUtils;

public class MyThread implements Runnable {

	int i;
	
	public MyThread(int i) {
		this.i = i;
	}
	
	@Override
	public void run() {
		if(i==0 || i==199) {
			System.out.println("thread " + i + " " + new Date().getTime());	
		} else {
			System.out.println("thread " + i);
		}
		HttpUtils.post("http://localhost:8080/EBox_Clone/test/t.do", new byte[]{});
	}
}
