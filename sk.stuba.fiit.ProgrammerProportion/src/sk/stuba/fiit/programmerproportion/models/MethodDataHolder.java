package sk.stuba.fiit.programmerproportion.models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

public final class MethodDataHolder {

	private final MethodDeclaration mMethodDeclaration;
	private final List<MethodInvocation> mInvocations = new ArrayList<MethodInvocation>();
	
	public MethodDataHolder(MethodDeclaration mDeclaration){
		this.mMethodDeclaration = mDeclaration;
	}
	
	public void addInvocation(MethodInvocation mInvocation){
		this.mInvocations.add(mInvocation);
	}
	
	public MethodDeclaration getMethodDeclaration(){
		return this.mMethodDeclaration;
	}
	
	public List<MethodInvocation> getInvocatedMethods(){
		return this.mInvocations;
	}
}
