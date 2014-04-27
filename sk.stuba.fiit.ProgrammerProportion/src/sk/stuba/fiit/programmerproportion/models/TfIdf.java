package sk.stuba.fiit.programmerproportion.models;

public final class TfIdf implements Comparable<TfIdf>{
	
	private final String mWord;
	
	private double mTermFrequency = -1;
	
	private double mInverseDocumentFrequency = -1;
	
	public TfIdf(final String word, double termFrequency){
		this.mWord = word;
		this.mTermFrequency = termFrequency;
	}
	
	public void setIDF(double idf){
		this.mInverseDocumentFrequency = idf;
	}
	
	public void setIDF(int passedDocs, int allDocs){
		this.mInverseDocumentFrequency = Math.log10((double)((double)allDocs / (double)passedDocs));
	}
	
	public double getTF(){return this.mTermFrequency;}
	public double getIDF(){return this.mInverseDocumentFrequency;}
	public String getWord(){return this.mWord;}
	
	public double calculateTfIdf(){
		if(mTermFrequency != -1 && mInverseDocumentFrequency != -1)
			return mTermFrequency * mInverseDocumentFrequency;
		else
			throw new IllegalArgumentException("Some of the fields was not set properly");
	}

	@Override
	public int compareTo(TfIdf o) {
		if(o.calculateTfIdf() < this.calculateTfIdf()) return -1;
		else if(o.calculateTfIdf() > this.calculateTfIdf()) return 1;
		else return 0;
	}
	
	@Override
	public String toString() {
		return "	" + mWord + ": " + calculateTfIdf();		
	}
}
