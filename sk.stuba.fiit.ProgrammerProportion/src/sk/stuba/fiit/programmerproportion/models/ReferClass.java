package sk.stuba.fiit.programmerproportion.models;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.io.Files;

public final class ReferClass extends AbstractReferCode{
	
	/*
	 * Collection of method implemented in the class
	 */
	private final List<ReferMethod> mMethods = new ArrayList<ReferMethod>();
	
	/*
	 * Collection of topics infered from method by included LDAModel
	 */
	private final List<String> mTopics = new ArrayList<String>();
	
	/*
	 * String representation of file location
	 */
	private final String mFilePath;
	
	/*
	 * String identificator of author of the file (from Git)
	 */
	private String mAuthor;	
	
	public ReferClass(final String filePath) {
		this.mFilePath = filePath;
		try{
			onInferTopics();
		}catch(Exception e){}
	}
	
	private void onInferTopics() throws Exception{
		String source = Files.toString(Paths.get(this.mFilePath).toFile(), Charset.defaultCharset());
		
	}
	
	public void addMethod(ReferMethod method){
		this.mMethods.add(method);
	}
	
	public void addMethod(Collection<ReferMethod> methods){
		this.mMethods.addAll(methods);
	}

	@Override
	public String getStringRepresentation() {
		return this.mFilePath;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}
	
	public List<ReferMethod> getMethods(){return this.mMethods;}
	public List<String> getTopics(){return this.mTopics;}
}
