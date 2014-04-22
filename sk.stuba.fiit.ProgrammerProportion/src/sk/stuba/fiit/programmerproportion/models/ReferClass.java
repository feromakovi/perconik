package sk.stuba.fiit.programmerproportion.models;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.CompilationUnit;

import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.programmerproportion.handlers.ClassVisitor;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;

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
		ClassVisitor classVisitor = new ClassVisitor();
		CompilationUnit compilationUnit = (CompilationUnit) TreeParsers.parse(Paths.get(this.mFilePath));
		compilationUnit.accept(classVisitor);
		this.mTopics.addAll(classVisitor.inference());	
		
		System.out.println("path: " + this.mFilePath);
		System.out.println(SourceCode.representationOf(" ", mTopics.toArray(new String[mTopics.size()])));
		System.out.println("");
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
	
	@Override
	public String toString() {
		return this.mFilePath;
	}
	// Method returns map of authors referenced how many lines of code they've written 
	public Map<String, Integer> getContribution(){
		final Map<String, Integer> authorsToLines = new HashMap<String, Integer>();
		for(ReferMethod m : this.mMethods){
			Map<String, Integer> methodAuthors = m.getContributors();
			Iterator<String> authors = methodAuthors.keySet().iterator();
			while(authors.hasNext()){
				String author = authors.next();
				int linesCount = methodAuthors.get(author);
				if(authorsToLines.containsKey(author))
					authorsToLines.put(author, (authorsToLines.get(author) + linesCount));
				else
					authorsToLines.put(author, linesCount);
			}
		}
		return authorsToLines;
	}
}
