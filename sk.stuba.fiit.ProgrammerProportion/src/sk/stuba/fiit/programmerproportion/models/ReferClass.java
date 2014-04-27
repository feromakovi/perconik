package sk.stuba.fiit.programmerproportion.models;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.CompilationUnit;

import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.programmerproportion.handlers.ClassVisitor;
import sk.stuba.fiit.programmerproportion.utils.LDAHelper;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;

public final class ReferClass extends AbstractReferCode{
	
	/*
	 * Collection of method implemented in the class
	 */
	private final List<ReferMethod> mMethods = new ArrayList<ReferMethod>();
	
	/*
	 * Collection of LDA topics infered from method by included LDAModel
	 */
	private final List<String> mLDATopics = new ArrayList<String>();
	
	/*
	 * String representation of file location
	 */
	private final String mFilePath;
	
	/*
	 * String identificator of author of the file (from Git)
	 */
	private String mAuthor;
	
	/*
	 * Map of every word from the document pointing to it's Term Frequency (count of the word / count of all words in the document)
	 */
	private final Map<String, TfIdf> mTfIdf = new HashMap<String, TfIdf>();
	
	/*
	 * Final instance of ClassVisitor for parsing and holding all words from this document
	 */
	private final ClassVisitor mClassVisitor = new ClassVisitor();	
	
	public ReferClass(final String filePath) {
		this.mFilePath = filePath;
		try{
			CompilationUnit compilationUnit = onCreateCompilationUnit();
			compilationUnit.accept(mClassVisitor);
			onCalculateTF();
			onInferTopics();			
		}catch(Exception e){}
	}

	private CompilationUnit onCreateCompilationUnit() throws Exception{
		return (CompilationUnit) TreeParsers.parse(Paths.get(this.mFilePath));
	}

	private void onCalculateTF() {
		mClassVisitor.calculateTermFrequency(mTfIdf);
	}
	
	private void onInferTopics(){
		mClassVisitor.inferTopics(this.mLDATopics, LDAHelper.LDAModel.ALL);
		
		System.out.println("path: " + this.mFilePath);
		System.out.println(SourceCode.representationOf(" ", mLDATopics.toArray(new String[mLDATopics.size()])));
		System.out.println("");
	}
	
	public boolean containsWord(String word){
		return this.mTfIdf.containsKey(word);
	}
	
	public void addMethod(ReferMethod method){
		this.mMethods.add(method);
	}
	
	public void addMethods(Collection<ReferMethod> methods){
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
	
	public Map<String, TfIdf> getTfIdfMap(){return this.mTfIdf;}
	public List<ReferMethod> getMethods(){return this.mMethods;}
	public List<String> getTopics(){return this.mLDATopics;}
	
	public List<TfIdf> getTfIdfs(){
		List<TfIdf> list = new ArrayList<>(this.mTfIdf.values());
		Collections.sort(list);
		return list;
	}
	
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
