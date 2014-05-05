package sk.stuba.fiit.programmerproportion.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.open.crc.intt.IdentifierNameTokeniser;
import uk.ac.open.crc.intt.IdentifierNameTokeniserFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public final class SourceCode {
	
	private static final String SEPARATORS = "~^$&|?\\_,-.:\"'(){}[]=<>;%@+/*#!1234567890";
	static Pattern mPackagePattern = Pattern.compile("package (.*?);");

	public static final List<String> parseLines(final String block){
		List<String> validLines = new ArrayList<String>();
		if(block != null){
			String[] lines = block.split("\n");
			if(lines != null && lines.length > 0)
				for(String line : lines)
					if(isValidLineOfCode(line))
						validLines.add(line);
		}
		return validLines;
	}
	
	public static final String normalizeCode(final String line){
		return line.replaceAll("\\s", "");
	}
	
	private static final boolean isValidLineOfCode(final String line){
		boolean valid = true;
		valid &= line.replaceAll("\\s", "").length() > 1;
		return valid;
	}
	
	public static final String[] tokenize(final File sourceFile){
		String[] tokens = new String[0];
		try {
			tokens = tokenize(Files.toString(sourceFile, Charsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokens;
	}
	
	public static final String extractPackage(final File sourceFile){
		String extractedPackage = null;
		try {
			extractedPackage = extractPackage(Files.toString(sourceFile, Charsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return extractedPackage;
	}
	
	public static final String extractPackage(final String text){
		Matcher matcher = mPackagePattern.matcher(text);
		if(matcher.find())
			return matcher.group(1);
		return null;
	}
	
	public static final String[] tokenize(final String sourceCode){ //sourceCode.replaceAll("[(){}\n\",._':;?&!=@+/*<>]", " ").replaceAll("\\s+", " ").split("\\s");
		IdentifierNameTokeniserFactory factory = new IdentifierNameTokeniserFactory();
		factory.setSeparatorCharacters(SEPARATORS);
		IdentifierNameTokeniser tokeniser = factory.create();
		String[] tokens = tokeniser.tokenise(sourceCode);
		String tokenized = removeSet(representationOf(" ", tokens).replaceAll("\n", " "), StopWords.ENGLISH, true); 
		tokenized = tokenized.replaceAll("\\b[^\\s]\\b", ""); //remove all one character words
		tokenized = tokenized.replaceAll("\\b.{2}\\b", ""); //remove all two characters words
		tokenized = tokenized.replaceAll("\\s+", " ");
		if(tokenized.startsWith(" "))
			tokenized = tokenized.replaceFirst(" ", "");		
		return tokenized.split(" ");
	}
	
	public static final String removeSeparators(final String code){
        return code.replaceAll("[~^$&|?\\_,-\\.:\"'(){}\\[\\]=<>;%@+/*#!]", " ");
	}
	
	public static final String removeSet(String code, Set<String> words, boolean ignoreCase){
		if(ignoreCase)
			code = code.toLowerCase();
		Iterator<String> iterator = words.iterator();
		while(iterator.hasNext()){
			String replacement = (ignoreCase) ? iterator.next().toLowerCase() : iterator.next();
			code = code.replaceAll("\\b" + replacement + "\\b", "");
		}			
		return code.replaceAll("\\s+", " ");
	}
	
	public static final String representationOf(final String delimiter, final String... strings){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < strings.length; i++){
			builder.append(strings[i]);
			if(i < (strings.length -1))
				builder.append(delimiter);
		}
		return builder.toString();
	}

	public static void main(String... args) throws IOException{
		String code = Files.toString(Paths.get("/Users/feromakovi/Desktop/token.j").toFile(), Charset.defaultCharset());
		String[] tokens = tokenize(code);
		//Model newModel = inference(tokens);
//		for(String l : tokenize(code))
//			Log.println(l);
	}
}