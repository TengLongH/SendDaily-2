package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import util.CheckTime;

public class Go {
	
	public static void main(String[] args) {
		
		Thread.currentThread().setName("SendDaily");
		String filename = System.getProperty("user.dir") + File.separator + "log.txt";
		try {
			System.setErr(  new PrintStream(filename) );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while( true ){
		
			if( CheckTime.isSendTime() ){
				Excute excute = new Excute();
				boolean success = excute.run();
				if( success ){
					CheckTime.turnTime();
				}
			} else{
				try {
					long wait = CheckTime.waitTime();
					wait = wait < 0 ? wait:10000;
					Thread.sleep( wait );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}