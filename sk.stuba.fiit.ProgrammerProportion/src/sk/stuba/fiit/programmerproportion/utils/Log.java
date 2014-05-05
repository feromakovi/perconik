package sk.stuba.fiit.programmerproportion.utils;

import java.io.FileWriter;

public final class Log {

	private static Log LOG = null;
	
	private String mFileName = null;
	
	private Log(String filePath){
		this.mFileName = filePath;
	}
	
	private boolean append(String append){
		try{
			FileWriter fileWriter = new FileWriter(mFileName, true);
			fileWriter.append(append);
			fileWriter.flush();
			fileWriter.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static void init(String filePath){
		LOG = new Log(filePath);
	}
	
	public static void print(String toPrint){
		if(LOG == null) throw new NullPointerException("LOG instance has not been initialised");
		LOG.append(toPrint);
	}
	
	public static void println(String toPrint){
		print(toPrint + "\n");
	}
}
