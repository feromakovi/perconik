package sk.stuba.fiit.programmerproportion.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

public final class SourceCode {

	public static final List<String> parseLines(String block){
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
	
	public static final String normalizeCode(String line){
		return line.replaceAll("\\s", "");
	}
	
	private static final boolean isValidLineOfCode(String line){
		boolean valid = true;
		valid &= line.replaceAll("\\s", "").length() > 1;
		return valid;
	}
	
	public static void main(String... args) throws IOException{
		String code = Files.toString(Paths.get("/Users/feromakovi/Dropbox/WorkspaceAndroid/balbies/src/com/balbies/android/RegistrationActivity.java").toFile(), Charset.defaultCharset());
		
		for(String l : parseLines(code))
			System.out.println(l);
	}
}
