package sk.stuba.fiit.programmerproportion.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgibblda.Model.Term;

public class ReferAuthor extends AbstractReferCode {
	
	private String mID;
	
	private final Map<String, Integer> mTopicsToContribution = new HashMap<String, Integer>();
	
	public ReferAuthor(String author){
		this.mID = author;
	}
	
	public void updateTopics(String topic, Integer linesCount){
		if(mTopicsToContribution.containsKey(topic))
			mTopicsToContribution.put(topic, mTopicsToContribution.get(topic) + linesCount);
		else
			mTopicsToContribution.put(topic, linesCount);
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
		StringBuilder b = new StringBuilder();
		b = b.append("======== ").append(this.mID).append(" ========").append("\n");
		List<Term> terms = Term.fromHashMap(mTopicsToContribution);
		Collections.sort(terms);
		for(Term t : terms)
			b = b.append("	").append(t.getWord()).append(":").append(t.getValue()).append("\n");
		return b.toString();
	}
}
