package sk.stuba.fiit.programmerproportion.models;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

import sk.stuba.fiit.programmerproportion.utils.SourceCode;

public class CodeComplexity {
	
	/*
	 * Source code of entire method
	 */
	protected final Block mBlock;
	
	/*
	 * Count of used types in the method's source code
	 */
	protected int mTypesCount = 0;
	
	/*
	 * Count of invoked methods in the method's source code
	 */
	protected int mInvokedMethodsCount = 0;
	
	/*
	 * Count of method's source code lines
	 */
	protected int mLinesCount = 0;
	
	/*
	 * Cyclomatic complexity value
	 */
	protected int mCyclomaticComplexity = 0;	
	
	/*
	 * Maximum depth in cycle statements
	 */
	protected int mMaxDepth = 0;
	
	/*
	 * Constructor
	 */
	public CodeComplexity(final Block sourceCode){
		this.mBlock = sourceCode;
		StatementVisitor visitor = new StatementVisitor();
		this.mBlock.accept(visitor);
		this.mLinesCount = SourceCode.parseLines(sourceCode.toString()).size();
	}
	
	private class StatementVisitor extends ASTVisitor{
		
		int actualNest = 0;
		
		@Override
		public boolean visit(TypeDeclaration node) {
			mTypesCount++;
			return true;
		}
		
		@Override
		public boolean visit(MethodInvocation node) {
			mInvokedMethodsCount++;
			return true;
		}	
		
		@Override
		public boolean visit(IfStatement node) {
			++mCyclomaticComplexity;
			return true;
		}
		
		@Override
		public boolean visit(DoStatement node) {
			++mCyclomaticComplexity;
			actualNest++;
			mMaxDepth = Math.max(mMaxDepth, actualNest);
			return true;
		}
		
		@Override
		public void endVisit(DoStatement node) {
			--actualNest;
		}
		
		@Override
		public boolean visit(ForStatement node) {
			++mCyclomaticComplexity;
			actualNest++;
			mMaxDepth = Math.max(mMaxDepth, actualNest);
			return true;
		}
		
		@Override
		public void endVisit(ForStatement node) {
			--actualNest;
		}
		
		@Override
		public boolean visit(WhileStatement node) {
			++mCyclomaticComplexity;
			actualNest++;
			mMaxDepth = Math.max(mMaxDepth, actualNest);
			return true;
		}
				
		@Override
		public void endVisit(WhileStatement node) {
			--actualNest;
		}			
	}
	
	@Override
	public String toString() {
		return "cType: " + mTypesCount + " cInvoked: " + mInvokedMethodsCount + " cLine: " + mLinesCount + " mDepth: " + mMaxDepth + " CC: " + mCyclomaticComplexity;
	}
}
