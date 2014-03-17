package sk.stuba.fiit.programmerproportion.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jgibblda.Inferencer;
import jgibblda.LDACmdOption;
import jgibblda.Model;
import uk.ac.open.crc.intt.IdentifierNameTokeniser;
import uk.ac.open.crc.intt.IdentifierNameTokeniserFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public final class SourceCode {
	
	private static final String SEPARATORS = "~^$&|?\\_,-.:\"'(){}[]=<>;%@+/*#!1234567890";

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
	
	public static final Model inference(final String[] words){
		LDACmdOption ldaOption = new LDACmdOption(); 
		ldaOption.inf = true; 
		ldaOption.dir = "/Users/feromakovi/Games/JGibbLDA-v.1.0/train"; 
		ldaOption.modelName = "model-final"; 
		ldaOption.niters = 100;
		Inferencer inferencer = new Inferencer(); 
		inferencer.init(ldaOption);
		Model newModel = inferencer.inference(words);
		return newModel;
	}
	
	public static final String[] tokenize(final String sourceCode){ //sourceCode.replaceAll("[(){}\n\",._':;?&!=@+/*<>]", " ").replaceAll("\\s+", " ").split("\\s");
		IdentifierNameTokeniserFactory factory = new IdentifierNameTokeniserFactory();
		factory.setSeparatorCharacters(SEPARATORS);
		IdentifierNameTokeniser tokeniser = factory.create();
		String[] tokens = tokeniser.tokenise(sourceCode);
		StringBuilder builder = new StringBuilder();
		for(String t : tokens)
			builder.append(t + " ");		
		String tokenized = builder.toString().replaceAll("\n", " ").toLowerCase();
		Iterator<String> iterator = StopWords.ALL.iterator();
		while(iterator.hasNext())
			tokenized = tokenized.replaceAll("\\b" + iterator.next().toLowerCase() + "\\b", "");
		tokenized = tokenized.replaceAll("\\b[^\\s]\\b", "");
		tokenized = tokenized.replaceAll("\\b.{2}\\b", "");
		tokenized = tokenized.replaceAll("\\s+", " ");
		if(tokenized.startsWith(" "))
			tokenized = tokenized.replaceFirst(" ", "");		
		return tokenized.split(" ");
	}
	
	public static void main(String... args) throws IOException{
		String code = Files.toString(Paths.get("/Users/feromakovi/Desktop/token.j").toFile(), Charset.defaultCharset());
		String[] tokens = tokenize(code);
		Model newModel = inference(tokens);
//		for(String l : tokenize(code))
//			System.out.println(l);
	}
}