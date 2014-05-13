package sk.stuba.fiit.programmerproportion.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import sk.stuba.fiit.programmerproportion.utils.Strings;

public final class ReferMethod extends AbstractReferCode{
	
	private static final double TRESHOLD_AUTHOR_PARTICIPATION = 0.5;

	private final String mName;
	private final Map<String,ReferLine> mLines = new HashMap<String,ReferLine>();
	private final List<InvokedMethod> mInvocatedMethods = new ArrayList<InvokedMethod>();
	private final String mClass;
	private final String mPackage;
	private final CodeComplexity mComplexity;
	private String mAuthor = null;
	private int mInvocationCount = 0;
	
	// Mapping all authors from the method to count of lines of code they've written 
	private Map<String,Integer> authorsParticipation = new HashMap<String,Integer>();
	
	public ReferMethod(final String name, final Block codeBlock, final String mclass, final String mpackage, final List<MethodInvocation> iMethods){
		this.mName = name;
		this.mClass = mclass;
		this.mPackage = mpackage;
		this.mComplexity = this.onCalculateComplexity(codeBlock);
		//Log.println(getStringRepresentation() + mComplexity);
		for(MethodInvocation m : iMethods){
			InvokedMethod im = InvokedMethod.from(m);
			if(im != null)
				mInvocatedMethods.add(im);
		}		
	}
	
	public static ReferMethod from(final MethodDeclaration method, final List<MethodInvocation> iMethods){
		String name = method.getName().toString();
		Block code = method.getBody();
		ITypeBinding mclass;
		ReferMethod rm = null;
		if(method.resolveBinding() != null){
			mclass = method.resolveBinding().getDeclaringClass();//class name
			rm = new ReferMethod(name, code, mclass.getName(), mclass.getPackage().getName(), iMethods);
		}
		return rm;
	}

	/**
	 * Method calculates complexity of entire method
	 * 
	 * @param codeBlock source code of entire method
	 * @return calculated complexity of source code
	 */
	private CodeComplexity onCalculateComplexity(final Block codeBlock) {
		CodeComplexity cComplexity = new CodeComplexity(codeBlock);
		return cComplexity;
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
	
	/**
	 * Simple getter of List of methods, which are invoked
	 * from body of this method.
	 *  
	 * @return
	 * 		List of invoked method
	 * @see InvokedMethod
	 */
	public List<InvokedMethod> getInvokedMethods(){
		return this.mInvocatedMethods;
	}
	
	public String getName(){
		return this.mName;
	}
	
	public CodeComplexity getComplexity(){
		return this.mComplexity;
	}

	/**
	 * Method returns representation of method's path in project.
	 * Project path is composite from package name and from class
	 * name, where is entire method declared.
	 * 
	 * @return Java project path to source file, where is the method declared
	 * @see ITypeBinding
	 * @see IPackageBinding
	 */
	public String getPath(){
		return Strings.representationOf(mPackage, mClass);
	}
	
	public void incrementInvocation(){
		this.mInvocationCount++;
	}
	
	/**
	 * Invocation count describes how many times has this method
	 * to be invoked from another fragment of source code. It's
	 * usually when e.g. some another method invokes this method
	 * in it's code block.
	 * 
	 * @return
	 * 		Count, how many times is this method invoked from
	 * 		from another place of source code.
	 */
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
		return mComplexity.mLinesCount > 1;
	}
	
	public boolean hasAuthor(){
		if(this.mAuthor != null && this.mAuthor.length() > 0)
			return true;
		this.onAssignAuthor();
		if(this.mAuthor != null && this.mAuthor.length() > 0)
			return true;
		return false;
	}
	
	public String getAuthor(){
		return this.mAuthor;
	}
	
	public Map<String, Integer> getContributors(){
		authorsParticipation.clear();
		onAssignAuthor();
		return this.authorsParticipation;
	}
	
	private void onAssignAuthor(){
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
	
	public Set<String> getLinesSet(){
		return mLines.keySet();
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
