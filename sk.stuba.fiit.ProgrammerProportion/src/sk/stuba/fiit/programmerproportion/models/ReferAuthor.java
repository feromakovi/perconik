package sk.stuba.fiit.programmerproportion.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.programmerproportion.utils.Strings;
import jgibblda.Model.Term;

public class ReferAuthor extends AbstractReferCode {
	
	private String mID;
	
	/*
	 * Topics collections mapping the topic term to count of lines of code
	 */	
	private final Map<String, Integer> mAllLDA = new HashMap<String, Integer>();
	private final Map<String, Integer> mNoOftenLDA = new HashMap<String, Integer>();
	private final Map<String, Integer> mTfIdf = new HashMap<String, Integer>();
	
	private double mFamiliarityAllLDA = -1;
	private double mFamiliarityNoOftenLDA = -1;
	
	/*
	 * Map of packagename-class name to count of lines of code extracted from every method where author has participated
	 */
	private final Map<String, Integer> mTechnologies = new HashMap<String, Integer>();
	
	public ReferAuthor(String author){
		this.mID = author;
	}
	
	public void updateTopics(String topic, Integer linesCount){
		updateTopicMaps(topic, linesCount, this.mAllLDA);
	}
	
	private static void updateTopicMaps(String topic, Integer linesCount, Map<String, Integer> map){
		if(map.containsKey(topic))
			map.put(topic, map.get(topic) + linesCount);
		else
			map.put(topic, linesCount);
	}

	@Override
	public String getStringRepresentation() {
		return mID;
	}
	
	@Override
	public int hashCode() {
		return this.mID.hashCode();
	}
	
	@Override
	public String toString() {
		return ""; 
//		StringBuilder b = new StringBuilder();
//		b = b.append("======== ").append(this.mID).append(" ========").append("\n");
//		List<Term> terms = Term.fromHashMap(mTopicsToContribution);
//		Collections.sort(terms);
//		for(Term t : terms)
//			b = b.append("	").append(t.getWord()).append(":").append(t.getValue()).append("\n");
//		return b.toString();
	}

	public void updateByClass(ReferClass clas, Integer lCount) {
		for(String s : clas.getAllLDATopics()) updateTopicMaps(s, lCount, mAllLDA);
		for(String s : clas.getNoOftenLDATopics()) updateTopicMaps(s, lCount, mNoOftenLDA);
		for(TfIdf t : clas.getTfIdfs()) updateTopicMaps(t.getWord(), lCount, this.mTfIdf);
		for(ReferMethod m : clas.getMethods()){
			Map<String, Integer> methodContributors = m.getContributors();
			if(methodContributors.containsKey(mID)){
				int mMethodLines = methodContributors.get(mID);
				for(InvokedMethod i : m.getInvokedMethods())
					updateTopicMaps(i.getPath(), mMethodLines, this.mTechnologies);
			}
		}
	}

	public void onCalculateFamiliarity(Collection<String> aInferenced,
			Collection<String> orInferenced) {
		this.mFamiliarityAllLDA = Strings.equalsCollections(mAllLDA.keySet(), aInferenced);
		this.mFamiliarityNoOftenLDA = Strings.equalsCollections(mNoOftenLDA.keySet(), orInferenced);
	}
}
