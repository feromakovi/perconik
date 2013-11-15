package sk.stuba.fiit.programmerproportion.models;

import sk.stuba.fiit.programmerproportion.utils.SourceCode;

public final class ReferLine extends AbstractReferCode{
	
	private final String mLine;
	private String mAuthor;
	private long mTime;
	private final int mLineNumber;
	
	public ReferLine(final String line, final long time, final int lineNumber){
		this.mLine = line;
		this.mTime = time;
		this.mLineNumber = lineNumber;
	}
	
	public void setAuthor(final String author){
		this.mAuthor = author;
	}

	@Override
	public String getStringRepresentation() {
		return SourceCode.normalizeCode(mLine);
	}	
	
	public String getAuthor(){
		return this.mAuthor;
	}
	
	public String getLine(){
		return this.mLine;
	}
	
	public long getTime(){
		return this.mTime;
	}
	
	public int getLineNumber(){
		return this.mLineNumber;
	}
	
	@Override
	public String toString() {
		return "author: " + this.mAuthor + "---------code: " + this.mLine;
	}
}
