package sk.stuba.fiit.programmerproportion.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import sk.stuba.fiit.perconik.core.java.dom.NodeCollectors;
import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.programmerproportion.files.FileFinder.FileFinderListener;

public class SourceFileHandler implements FileFinderListener{

	@Override
	public void onFileFind(File file) {
		CompilationUnit u = null;
		try {
			u = (CompilationUnit) TreeParsers.parse(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		if(u != null){
			List<MethodDeclaration> declarations = NodeCollectors.ofClass(MethodDeclaration.class).apply(u);
			for(MethodDeclaration md : declarations)
				System.out.println("method: " + md.getName() + "\n" + md.getBody());
		}		
	}
}
