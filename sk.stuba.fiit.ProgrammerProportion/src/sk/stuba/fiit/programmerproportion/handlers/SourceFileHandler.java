package sk.stuba.fiit.programmerproportion.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import sk.stuba.fiit.perconik.core.java.dom.NodeCollectors;
import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.files.FileFinder.FileFinderListener;
import sk.stuba.fiit.programmerproportion.utils.Strings;

public final class SourceFileHandler implements FileFinderListener{
	
	private final Repository mRepository;
	
	public SourceFileHandler(final Repository repository){
		this.mRepository = repository;
	}

	@Override
	public void onFileFind(File file) {
		Iterator<RevCommit> revisions = GitRepositories.getLogFile(mRepository, Strings.makeRelativePath(this.mRepository.getWorkTree().getAbsolutePath(), file.getAbsolutePath()));
		while(revisions.hasNext())
			System.out.println("reviszia:  " + revisions.next().getId());			
	}
	
	private List<MethodDeclaration> readMethods(File file){
		CompilationUnit u = null;
		try {
			u = (CompilationUnit) TreeParsers.parse(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return (u != null) ? NodeCollectors.ofClass(MethodDeclaration.class).apply(u) : new ArrayList<MethodDeclaration>();
	}
}
