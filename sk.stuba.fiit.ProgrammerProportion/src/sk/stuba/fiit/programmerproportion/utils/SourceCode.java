package sk.stuba.fiit.programmerproportion.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public final class SourceCode {

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
	
	public static final String[] tokenize(final String sourceCode){
		return sourceCode.replaceAll("[(){}\n\",._':;?&!=@+/*<>]", " ").replaceAll("\\s+", " ").split("\\s");
	}
	
	public static void main(String... args) throws IOException{
		String code = Files.toString(Paths.get("/Users/feromakovi/Dropbox/WorkspaceAndroid/balbies/src/com/balbies/android/RegistrationActivity.java").toFile(), Charset.defaultCharset());
		
		for(String l : tokenize(code))
			System.out.print(l + " ");
	}
}