package sk.stuba.fiit.programmerproportion.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sk.stuba.fiit.programmerproportion.utils.SourceCode;

public class ReferMethod extends AbstractReferCode{

	private final Set<String> mNames = new HashSet<String>();
	private final Map<String,ReferLine> mLines = new HashMap<String,ReferLine>();
	private final String mPath;
	private final double mComplexity;
	
	public ReferMethod(final String name, final String codeBlock, final String path){
		this.mNames.add(name);
		this.mPath = path;
		this.mComplexity = onCalculateComplexity(codeBlock);
	}

	private double onCalculateComplexity(final String codeBlock) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void updateLineAuthors(final Iterator<ReferLine> lineIterator){
		while(lineIterator.hasNext()){
			ReferLine newLine = lineIterator.next();
			if(this.mLines.containsKey(newLine.getStringRepresentation()))
				this.mLines.get(newLine.getStringRepresentation()).setAuthor(newLine.getAuthor());
		}
	}
	
	public void setLines(final List<String> lines, final long time, final String author){
		for(int i = 0; i < lines.size(); i++){
			String line = lines.get(i);
			ReferLine rl = new ReferLine(line, time, i);
			rl.setAuthor(author);
			mLines.put(rl.getStringRepresentation(), rl);
		}
	}
	
	public Iterator<ReferLine> getLines(){
		return this.mLines.values().iterator();
	}
	
	public boolean hasName(String name){
		return this.mNames.contains(name);
	}
	
	public void addName(String name){
		this.mNames.add(name);
	}
	
	public double getComplexity(){
		return this.mComplexity;
	}

	public String getPath(){
		return this.mPath;
	}

	@Override
	public String getStringRepresentation() {
		return SourceCode.normalizeCode(this.mPath + this.mNames.iterator().next());
	}
	
	@Override
	public String toString() {
		String o = new String("methodName: " + this.mNames.iterator().next() + "\n");
		Iterator<ReferLine> lns = this.getLines();
		while(lns.hasNext())
			o += "		" + lns.next().toString() + "\n";
		return o;
	}
}
