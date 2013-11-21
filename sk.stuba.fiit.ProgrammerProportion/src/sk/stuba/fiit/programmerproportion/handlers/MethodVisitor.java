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

	@Override
	public boolean visit(MethodDeclaration node) {
		mHolder = new MethodDataHolder(node);
		return true;
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		if(mHolder != null)
			mHolder.addInvocation(node);			
		return false;
	}
	
	public void endVisit(MethodDeclaration node) {
		mMethods.add(mHolder);
	}
	
	public List<MethodDataHolder> getMethods(){
		return this.mMethods;
	}
}
