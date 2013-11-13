package sk.stuba.fiit.programmerproportion.files;

import java.io.File;
import java.io.FilenameFilter;

public final class FileFinder {
	
	public static final String PATTERN_JAVA = ".+\\.java$";
	
	private FileFinderListener mListener = null;
	private RegexFilter mFilter = null;
	
	public FileFinder(String pattern){
		this.mFilter = new RegexFilter(pattern);
	}
	
	public FileFinder(){
		this(null);
	}
	
	public void find(File file){
		try{
			if(!callbackListener(file) && file.isDirectory()){
				File[] files = file.listFiles(mFilter);
				for(File f : files)
					find(f);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private boolean callbackListener(File file){
		if(file != null && file.isFile()){
			if(this.mListener != null)
				this.mListener.onFileFind(file);				
			return true;
		}
		return false;
	}
	
	public void setMatchPattern(String pattern){
		this.mFilter = new RegexFilter(pattern);
	}
	
	public void setFileFinderListener(FileFinderListener listener){
		this.mListener = listener;
	}
	
	public static interface FileFinderListener{
		public abstract void onFileFind(File file);
	}
	
	private final class RegexFilter implements FilenameFilter{
		
		private String mPattern = null;
		
		public RegexFilter(String pattern){
			this.mPattern = pattern;
		}

		@Override
		public boolean accept(File dir, String name) {
			File f = new File(dir, name);
			if(f.isFile() && this.mPattern != null && !name.matches(mPattern))
				return false;
			return true;
		}
	}
}
