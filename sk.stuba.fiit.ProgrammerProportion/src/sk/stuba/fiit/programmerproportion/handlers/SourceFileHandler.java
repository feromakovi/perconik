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
import sk.stuba.fiit.programmerproportion.data.DataProvider;
import sk.stuba.fiit.programmerproportion.files.FileFinder.FileFinderListener;
import sk.stuba.fiit.programmerproportion.models.ReferMethod;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;
import sk.stuba.fiit.programmerproportion.utils.Strings;

public final class SourceFileHandler implements FileFinderListener{
	
	private final Repository mRepository;
	
	public SourceFileHandler(final Repository repository){
		this.mRepository = repository;
	}

	@Override
	public void onFileFind(final File file) {
		System.out.println("=========================" + file.getName() + "=========================");
		final String relativePath = Strings.makeRelativePath(this.mRepository.getWorkTree().getAbsolutePath(), file.getAbsolutePath());
		Iterator<RevCommit> revisions = GitRepositories.getLogFile(mRepository, relativePath);
		if(revisions.hasNext()){
			//snapshot of current project
			RevCommit commit = revisions.next();
			System.out.println("revision: " + commit.toObjectId());
			GitRepositories.checkoutFile(mRepository, relativePath, commit);
			List<ReferMethod> referMethods = getReferMethods(file.getAbsolutePath(), -1, commit.getCommitterIdent().getEmailAddress());
			DataProvider.getInstance().insert(referMethods);
		}
		while(revisions.hasNext()){
			RevCommit commit = revisions.next();
			System.out.println("next revision: " + commit.toObjectId());
			GitRepositories.checkoutFile(mRepository, relativePath, commit);
			List<ReferMethod> referMethods = getReferMethods(file.getAbsolutePath(), commit.getCommitTime(), commit.getCommitterIdent().getEmailAddress());
			DataProvider.getInstance().update(referMethods);
		}	
		GitRepositories.checkoutFileToHEAD(mRepository, relativePath);
	}
	
	private List<ReferMethod> getReferMethods(final String absolutePath, final long time, final String author){
		List<ReferMethod> referMethods = new ArrayList<ReferMethod>();
		List<MethodDeclaration> methodDeclarations = getMethodDeclarations(absolutePath);
		for(MethodDeclaration md : methodDeclarations){
			if(md.getBody() != null){
				String codeBlock = md.getBody().toString();
				ReferMethod rm = new ReferMethod(md.getName().toString(), codeBlock, absolutePath);
				List<String> lines = SourceCode.parseLines(codeBlock);
				rm.setLines(lines, time, author);
				referMethods.add(rm);
			}				
		}
		return referMethods;
	}
	
	private List<MethodDeclaration> getMethodDeclarations(final String absolutePath){
		CompilationUnit u = null;
		try {
			u = (CompilationUnit) TreeParsers.parse(Paths.get(absolutePath));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return (u != null) ? NodeCollectors.ofClass(MethodDeclaration.class).apply(u) : new ArrayList<MethodDeclaration>();
	}
}