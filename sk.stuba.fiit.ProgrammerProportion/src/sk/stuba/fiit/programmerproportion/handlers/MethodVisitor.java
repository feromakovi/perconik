package sk.stuba.fiit.programmerproportion.handlers;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import sk.stuba.fiit.programmerproportion.models.MethodDataHolder;

public final class MethodVisitor extends ASTVisitor{
	
	private List<MethodDataHolder> mMethods = new ArrayList<MethodDataHolder>();
	private MethodDataHolder mHolder = null;
	private String mMethod = null; 

	@Override
	public boolean visit(MethodDeclaration node) {
		if(mMethod == null){
			mHolder = new MethodDataHolder(node);
			mMethod = node.getName().toString();
		}	
		return true;
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		if(mHolder != null)
			mHolder.addInvocation(node);
		return true;
	}
	
	public void endVisit(MethodDeclaration node) {
		if(mMethod != null && mMethod.equals(node.getName().toString())){
			mMethods.add(mHolder);
			mMethod = null;
		}		
	}
	
	public List<MethodDataHolder> getMethods(){
		return this.mMethods;
	}
}
