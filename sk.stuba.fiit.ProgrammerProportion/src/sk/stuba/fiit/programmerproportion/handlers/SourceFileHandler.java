package sk.stuba.fiit.programmerproportion.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.data.DataProvider;
import sk.stuba.fiit.programmerproportion.files.JavaUnitFinder.JavaUnitListener;
import sk.stuba.fiit.programmerproportion.models.MethodDataHolder;
import sk.stuba.fiit.programmerproportion.models.ReferClass;
import sk.stuba.fiit.programmerproportion.models.ReferMethod;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;

public final class SourceFileHandler implements JavaUnitListener{
	
	private final Repository mRepository;
	
	public SourceFileHandler(final Repository repository){
		this.mRepository = repository;
	}
	
	@Override
	public void onCompilationUnit(final ICompilationUnit iCompilationUnit) {
		final String relativePath = iCompilationUnit.getPath().makeAbsolute().removeFirstSegments(1).toString();
		final String javaClassLocation = mRepository.getWorkTree() + File.separator + relativePath;
		final ReferClass mClass = new ReferClass(javaClassLocation);
		Iterator<RevCommit> revisions = GitRepositories.getFileLog(mRepository, relativePath).iterator();
		if(revisions.hasNext()){
			//snapshot of current project
			RevCommit commit = revisions.next();
			GitRepositories.checkoutFile(mRepository, relativePath, commit);
			List<ReferMethod> referMethods = getReferMethods(iCompilationUnit, -1, commit.getCommitterIdent().getEmailAddress());
			DataProvider.getInstance().insert(referMethods);
			mClass.addMethods(referMethods);
		}
		while(revisions.hasNext()){
			RevCommit commit = revisions.next();
			GitRepositories.checkoutFile(mRepository, relativePath, commit);
			List<ReferMethod> referMethods = getReferMethods(iCompilationUnit, commit.getCommitTime(), commit.getCommitterIdent().getEmailAddress());
			DataProvider.getInstance().update(referMethods);
			mClass.setAuthor(commit.getCommitterIdent().getEmailAddress());
		}
		GitRepositories.checkoutFileToHead(mRepository, relativePath);
		DataProvider.getInstance().addClass(mClass);
		DataProvider.getInstance().updateAuthorsContribution(mClass);
	}
	
	private List<ReferMethod> getReferMethods(final ICompilationUnit iCompilationUnit, final long time, final String author){
		List<ReferMethod> referMethods = new ArrayList<ReferMethod>();
		List<MethodDataHolder> methodHolders = getMethodDeclarations(iCompilationUnit);
		for(MethodDataHolder mdh : methodHolders){
			MethodDeclaration md = mdh.getMethodDeclaration();
			if(md.getBody() != null){
				ReferMethod rm = ReferMethod.from(md, mdh.getInvocatedMethods());
				if(rm != null && rm.isValid()){
					List<String> lines = SourceCode.parseLines(md.getBody().toString());
					rm.setLines(lines, time, author);
					referMethods.add(rm);
				}
			}				
		}
		return referMethods;
	}
	
	private List<MethodDataHolder> getMethodDeclarations(final ICompilationUnit iCompilationUnit){
		MethodVisitor visitor = new MethodVisitor();
		CompilationUnit u = (CompilationUnit) TreeParsers.parse(iCompilationUnit);
		if(u != null)
			u.accept(visitor);
		return visitor.getMethods();
	}
}
