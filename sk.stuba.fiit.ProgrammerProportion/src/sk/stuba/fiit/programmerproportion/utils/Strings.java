package sk.stuba.fiit.programmerproportion.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
			key = representationOf(c.getPackage().getName(), c.getName(), methodBinding.getName());
		}
		return key;
	}
	
	public static final String representationOf(final String... strings){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < strings.length; i++){
			builder.append(strings[i]);
			if(i < (strings.length -1))
				builder.append("-");
		}
		return builder.toString();
	}
	
	public static final double equalsCollections(Set<String> set, Collection<String> aInferenced){
		int hit = 0;
		for(String s : aInferenced)
			if(set.contains(s))
				hit++;
		return ((double)hit / Math.max(set.size(), aInferenced.size()));
	}
	
	public static final <T> String collectionToString(Collection<T> col){
		StringBuilder builder = new StringBuilder();
		for(T o : col){
			builder.append(o.toString()).append("\n");
		}
		return builder.toString();
	}
	
//	public static final String representationOf(final String delimiter, final String... strings){
//		StringBuilder builder = new StringBuilder();
//		for(int i = 0; i < strings.length; i++){
//			builder.append(strings[i]);
//			if(i < (strings.length -1))
//				builder.append(delimiter);
//		}
//		return builder.toString();
//	}
	
//	public static final String removeLast(final String path, final String toRemove) {
//		return path.substring(0, path.length() - toRemove.length());
//	}
	
	public static void main(String... args) throws IOException{
//		String absoluteDir = "/Users/feromakovi/Dropbox/WorkspaceJava/wikiparser/";
//		String absoluteFile = "/Users/feromakovi/Dropbox/WorkspaceJava/wikiparser/src/sk/feromakovi/wikiparser/exception/BreakParsingException.java";
//		Log.println(makeRelativePath(absoluteDir, absoluteFile));
		Set<String> set = new HashSet<String>();
		set.add("one");set.add("two");set.add("three");set.add("four");set.add("five");
		Collection<String> col = new ArrayList<String>();
		col.add("one");col.add("two");col.add("three");col.add("four");
		System.out.println(equalsCollections(set, col));
	}
}