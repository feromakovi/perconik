package sk.stuba.fiit.programmerproportion.models;

import java.util.HashMap;
import java.util.Map;

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
}
