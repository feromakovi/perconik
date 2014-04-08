package sk.stuba.fiit.programmerproportion.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import sk.stuba.fiit.programmerproportion.utils.LDAHelper;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;
import sk.stuba.fiit.programmerproportion.utils.StopWords;

public class ClassVisitor extends ASTVisitor{
	
	private List<String> mTokens = new ArrayList<String>();

	@Override
	public boolean visit(TypeDeclaration node) {
		try{
			final String originalCode = node.toString();
			final String noJavaCode = SourceCode.removeSet(SourceCode.removeSeparators(originalCode), StopWords.JAVA, false); //Not ignore case because of INTT - it won't separate words 
			String[] tokens = SourceCode.tokenize(noJavaCode);
			if(tokens != null){
				tokens = SourceCode.removeSet(SourceCode.representationOf(" ", tokens), StopWords.OFTEN, true).split(" ");
				for(String token : tokens)
					mTokens.add(token);
			}
		}catch(Exception e){}
		return super.visit(node);
	}
	
	public Collection<String> inference(){
		return LDAHelper.inference(mTokens.toArray(new String[mTokens.size()]));
	}
}
