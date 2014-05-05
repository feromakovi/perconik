package sk.stuba.fiit.programmerproportion.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class Log {
	
	public static final String FOLDER_TECHNOLOGIES = "technologies";
	public static final String FOLDER_FAMILIARITY = "familiarity";
	public static final String FOLDER_COMPLEXITY = "complexity";

	private static final String DEFAULT_FILE_EXTENSION = ".txt";
	
	private static Log LOG = null;
	
	private String mFileName = null;
	private String mBasePath = null;
	
	public static Log get(){return LOG;}
	
	public static void init(String basePath, String filePath){
		LOG = new Log(basePath, filePath);
	}
	
	private Log(String basePath, String filePath){
		this.mBasePath = basePath;
		this.mFileName = filePath;
	}
	
	private boolean append(String append){
		return append(getFile("", mFileName), append);
	}
	
	private boolean append(File subor, String append){
		try{
			FileWriter fileWriter = new FileWriter(subor, true);
			fileWriter.append(append);
			fileWriter.flush();
			fileWriter.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}	
	
	public static void print(String toPrint){
		if(LOG == null) throw new NullPointerException("LOG instance has not been initialised");
		LOG.append(toPrint);
	}
	
	public static void println(String toPrint){
		print(toPrint + "\n");
	}
	
	private File getFile(String relativePath, String name){
		try {
			relativePath = checkPath(relativePath);
			File f = new File(relativePath + name);
			if(!f.exists())
				f.createNewFile();
			return f;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String checkPath(String relativePath){
		try{
			File f = new File(mBasePath + File.separator + relativePath);
			if(!f.exists())
				f.mkdir();
			return f.getPath() + File.separator;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void print(String relativeDir, String name, String content){
		File f = getFile(relativeDir, (name.endsWith(DEFAULT_FILE_EXTENSION)) ? name : name + DEFAULT_FILE_EXTENSION);
		append(f, content);
	}
	
	public void println(String relativeDir, String name, String content){
		print(relativeDir, name, content + "\n");
	}
	
	public static void main(String...strings){
		//Log.init("/Users/feromakovi/Desktop/log", "log.txt");
		//Log.println("ahoj");
		//Log.get().println("techs", "hovno", "nazdar");
	}
}
