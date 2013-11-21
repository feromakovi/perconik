package sk.stuba.fiit.programmerproportion.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import sk.stuba.fiit.programmerproportion.utils.Strings;

public final class ReferMethod extends AbstractReferCode{
	
	private static final double TRESHOLD_AUTHOR_PARTICIPATION = 0.5;

	private final String mName;
	private final Map<String,ReferLine> mLines = new HashMap<String,ReferLine>();
	private final Map<String,MethodInvocation> mInvocatedMethods = new HashMap<String,MethodInvocation>();
	private final String mFilePath;
	private final String mClass;
	private final String mPackage;
	private final double mComplexity;
	private String mAuthor = null;
	private int mInvocationCount = 0;
	
	public ReferMethod(final String name, final String codeBlock, final String path, final String mclass, final String mpackage, final List<MethodInvocation> iMethods){
		this.mName = name;
		this.mFilePath = path;
		this.mClass = mclass;
		this.mPackage = mpackage;
		this.mComplexity = this.onCalculateComplexity(codeBlock);
		for(MethodInvocation m : iMethods){
			String key = Strings.representationOf(m.resolveMethodBinding());	
			if(key != null)
				mInvocatedMethods.put(key, m);
		}
	}
	
	public static ReferMethod fromMethodDeclaration(final MethodDeclaration method, final String path, final List<MethodInvocation> iMethods){
		String name = method.getName().toString();
		String code = method.getBody().toString();
		ITypeBinding mclass;
		ReferMethod rm = null;
		if(method.resolveBinding() != null){
			mclass = method.resolveBinding().getDeclaringClass();//class name
			rm = new ReferMethod(name, code, path, mclass.getName(), mclass.getPackage().getName(), iMethods);
		}
		return rm;
	}

	/**
	 * Method calculates complexity of entire method
	 * 
	 * @param codeBlock source code of entire method
	 * @return calculated complexity of source code
	 */
	private double onCalculateComplexity(final String codeBlock) {
		// TODO Implement calculating complexity
		return 0;
	}
	
	public void updateLineAuthors(final Iterator<ReferLine> lineIterator){
		while(lineIterator.hasNext()){
			ReferLine newLine = lineIterator.next();
			if(this.mLines.containsKey(newLine.getStringRepresentation()))
				this.mLines.get(newLine.getStringRepresentation()).update(newLine.getAuthor(), newLine.getTime());;
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
	
	public String getName(){
		return this.mName;
	}
	
	public double getComplexity(){
		return this.mComplexity;
	}

	public String getPath(){
		return this.mFilePath;
	}
	
	public void incrementInvocation(){
		this.mInvocationCount++;
	}
	
	public int getInvocationCount(){
		return this.mInvocationCount;
	}

	@Override
	public String getStringRepresentation() {
		return Strings.representationOf(mPackage, mClass, mName);
	}
	
	/**
	 * Returns @true if the complexity of method is more than 1.
	 * Method validity depends on it's complexity. 
	 * If method is only getter/setter it's complexity is too small.
	 * 
	 * @return if the method valid or no
	 */
	public boolean isValid(){
		//TODO: return validity by complexity of method
		return true;
	}
	
	public boolean hasAuthor(){
		this.onAssignAuthor();
		if(this.mAuthor != null && this.mAuthor.length() > 0)
			return true;
		return false;
	}
	
	public String getAuthor(){
		return this.mAuthor;
	}
	
	private void onAssignAuthor(){
		Map<String,Integer> authorsParticipation = new HashMap<String,Integer>();
		Iterator<ReferLine> lineIterator = this.mLines.values().iterator();
		while(lineIterator.hasNext()){
			ReferLine line = lineIterator.next();
			int value = 0;
			if(authorsParticipation.containsKey(line.getAuthor()))
				value = authorsParticipation.get(line.getAuthor());
			authorsParticipation.put(line.getAuthor(), ++value);
		}
		Iterator<String> authorIterator = authorsParticipation.keySet().iterator();
		while(authorIterator.hasNext()){
			String author = authorIterator.next();
			double participation = ((double) authorsParticipation.get(author) / (double) this.mLines.size());
			if(participation > TRESHOLD_AUTHOR_PARTICIPATION){
				this.mAuthor = author;
				return;
			}				
		}
	}
	
	@Override
	public String toString() {
		String o = new String("  Method: " + this.mName + "\n");
		Iterator<ReferLine> lns = this.getLines();
		final List<ReferLine> sortedLines = new ArrayList<ReferLine>();
		while(lns.hasNext())
			sortedLines.add(lns.next());
		Collections.sort(sortedLines);
		for(ReferLine l : sortedLines)
			o += "    " + l.toString() + "\n";
		return o;
	}
}
