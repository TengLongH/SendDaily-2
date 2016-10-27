package main;

import java.util.List;

import beans.PersonDaily;
import database.Database;
import email.Send;
import internet.Internet;

public class Excute {

	public boolean run(){

		Internet internet = null;
		Send send = null;
		boolean success = false;
		
		Database database = Database.getInstance();
		
		try{
			send = new Send();
			internet = new Internet();
		}catch( Exception e ){
			return false;
		}
		
		StringBuffer subject = new StringBuffer();
		subject.append("综合组项目日报");
		String content = database.getProjectDaily("综合组");
		//content = "send daily test";
		if( content.isEmpty() ){
			return true;
		}

		try {
			internet.connect();

			send.setSubject(subject.toString());
			send.setContent( content );

			List<PersonDaily> persons = database.getProjectPerson("综合组");
			
			for (PersonDaily p : persons) {
				send.getRecivers().add(p.getEmail());
			}
			
//			send.getRecivers().clear();
//			send.getRecivers().add("1739914236@qq.com");
//			send.getRecivers().add("tenglongh@126.com");
//			send.getRecivers().add("hongtenglong@sohu.com");
			
			success = send.send();

		} finally {
			 internet.disConnect();
		}
		return success;
	}
}
