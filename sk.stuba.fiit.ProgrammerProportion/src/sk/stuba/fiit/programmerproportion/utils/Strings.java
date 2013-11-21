package sk.stuba.fiit.programmerproportion.utils;

import java.io.IOException;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

public final class Strings {

	public static final String makeRelativePath(String absoluteDir, final String absoluteFile){
		if(!absoluteDir.endsWith("/"))
			absoluteDir += "/";
		return absoluteFile.replaceFirst(absoluteDir, "");
	}
	
	public static final String representationOf(final IMethodBinding methodBinding){
		String key = null;
		if(methodBinding != null){
			ITypeBinding c = methodBinding.getDeclaringClass();
			key = new String(c.getPackage().getName() + "-" + c.getName() + "-" + methodBinding.getName());
		}
		return key;
	}
	
//	public static final String removeLast(final String path, final String toRemove) {
//		return path.substring(0, path.length() - toRemove.length());
//	}
	
	public static void main(String... args) throws IOException{
		String absoluteDir = "/Users/feromakovi/Dropbox/WorkspaceJava/wikiparser/";
		String absoluteFile = "/Users/feromakovi/Dropbox/WorkspaceJava/wikiparser/src/sk/feromakovi/wikiparser/exception/BreakParsingException.java";
		System.out.println(makeRelativePath(absoluteDir, absoluteFile));
	}
}