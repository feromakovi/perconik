package sk.stuba.fiit.programmerproportion.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import sk.stuba.fiit.programmerproportion.models.TfIdf;
import sk.stuba.fiit.programmerproportion.utils.ModelHelper;
import sk.stuba.fiit.programmerproportion.utils.ModelHelper.LDAModel;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;
import sk.stuba.fiit.programmerproportion.utils.StopWords;

public class ClassVisitor extends ASTVisitor{
	
	/*
	 * All java class tokens without java syntax words and without stop words
	 */
	private List<String> mTokens = new ArrayList<String>();

	@Override
	public boolean visit(TypeDeclaration node) {
		try{
			final String originalCode = node.toString();
			final String noJavaCode = SourceCode.removeSet(SourceCode.removeSeparators(originalCode), StopWords.JAVA, false); //Not ignore case because of INTT - it won't separate words 
			String[] tokens = SourceCode.tokenize(noJavaCode);
			if(tokens != null && tokens.length > 0){
				this.mTokens = arrayToCollection(mTokens, tokens);
			}
		}catch(Exception e){}
		return super.visit(node);
	}
	
	public void inferTopics(final List<String> mLDATopics, final LDAModel ldaModel) {
		String[] wordsToInfer = null;
		switch (ldaModel) {
		case ALL:
			wordsToInfer = mTokens.toArray(new String[mTokens.size()]);
			break;
		case OFTEN_REMOVED:
			wordsToInfer = getTokensWithoutOften();
			break;
		}
		mLDATopics.addAll(ModelHelper.inference(wordsToInfer, ldaModel));
	}
	
	public void calculateTfIdf(final Map<String, TfIdf> mTfIdf) {
		Map<String, Integer> wToC = new HashMap<String, Integer>(); //mapping every word from document to count of appearance
		for(String w : mTokens){
			if(wToC.containsKey(w))
				wToC.put(w, (wToC.get(w) + 1));
			else
				wToC.put(w, 1);
		}
		Iterator<String> wIterator = wToC.keySet().iterator();
		while(wIterator.hasNext()){
			String word = wIterator.next();
			double tf = (double) ((double) wToC.get(word) / (double) mTokens.size());
			TfIdf h = new TfIdf(word, tf);
			h.setIDF(ModelHelper.getPassesDocs(word), ModelHelper.IDF_DOCUMENTS_COUNT);
			mTfIdf.put(word, h);
		}
	}
	
	public List<String> getTokens(){
		return this.mTokens;
	}
	
	public String[] getTokensWithoutOften(){
		return SourceCode.removeSet(SourceCode.representationOf(" ", mTokens.toArray(new String[mTokens.size()])), StopWords.OFTEN, true).split(" ");
	}
	
	private static List<String> arrayToCollection(List<String> collection, String[] array){
		for(String s : array)
			collection.add(s);
		return collection;
	}
}
