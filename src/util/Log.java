package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {

	private String filename;
	private StringBuffer buffer;
	private static Log instance;
	
	private Log() {
		buffer = new StringBuffer();
		filename = System.getProperty("user.dir") + File.separator + "log.txt";
		File file = new File(filename);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("create log file error");;
		}
	}
	
	public static Log getInstance(){
		if( null == instance )
			instance = new Log();
		return instance;
	}
	public void bufferWrite(){
		if( buffer.length() <= 0 )return ;
		buffer.append("\n");
		message( buffer.toString() );
		buffer.delete(0, buffer.length());
	}
	
	public void message( String msg ){
		if( null == msg || msg.isEmpty() )return ;
		try( FileWriter out = new FileWriter( filename, true )) {
			out.write( new Date().toString() );
			out.write(":");
			out.write( msg );
			out.write("\n");
		} catch (IOException e) {
			System.out.println("write to log file error");
		}
	}
	
	public StringBuffer getBuffer(){
		return this.buffer;
	}
}
