package sk.stuba.fiit.programmerproportion.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import sk.stuba.fiit.perconik.core.java.dom.NodeCollectors;
import sk.stuba.fiit.perconik.core.java.dom.NodePathExtractor;
import sk.stuba.fiit.perconik.core.java.dom.NodePaths;
import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.data.DataProvider;
import sk.stuba.fiit.programmerproportion.files.FileFinder.FileFinderListener;
import sk.stuba.fiit.programmerproportion.files.JavaUnitFinder.JavaUnitListener;
import sk.stuba.fiit.programmerproportion.models.ReferMethod;
import sk.stuba.fiit.programmerproportion.utils.SourceCode;
import sk.stuba.fiit.programmerproportion.utils.Strings;

public final class SourceFileHandler implements JavaUnitListener{
	
	private final Repository mRepository;
	
	public SourceFileHandler(final Repository repository){
		this.mRepository = repository;
	}

	
//	public void onFileFind(final File file) {
//		final String relativePath = Strings.makeRelativePath(this.mRepository.getWorkTree().getAbsolutePath(), file.getAbsolutePath());
//		Iterator<RevCommit> revisions = GitRepositories.getFileLog(mRepository, relativePath).iterator();
//		if(revisions.hasNext()){
//			//snapshot of current project
//			RevCommit commit = revisions.next();
//			GitRepositories.checkoutFile(mRepository, relativePath, commit);
//			List<ReferMethod> referMethods = getReferMethods(file.getAbsolutePath(), -1, commit.getCommitterIdent().getEmailAddress());
//			DataProvider.getInstance().insert(referMethods);
//		}
//		while(revisions.hasNext()){
//			RevCommit commit = revisions.next();
//			GitRepositories.checkoutFile(mRepository, relativePath, commit);
//			List<ReferMethod> referMethods = getReferMethods(file.getAbsolutePath(), commit.getCommitTime(), commit.getCommitterIdent().getEmailAddress());
//			DataProvider.getInstance().update(referMethods);
//		}	
//		GitRepositories.checkoutFileToHead(mRepository, relativePath);
//	}
	
//	private List<ReferMethod> getReferMethods(final String absolutePath, final long time, final String author){
//		List<ReferMethod> referMethods = new ArrayList<ReferMethod>();
//		List<MethodDeclaration> methodDeclarations = getMethodDeclarations(absolutePath);
//		//final NodePathExtractor<ASTNode> pn = NodePaths.namePathExtractor();
//		for(MethodDeclaration md : methodDeclarations){
//			if(md.getBody() != null){
//				String codeBlock = md.getBody().toString();
//				IMethodBinding imb = md.resolveBinding();
//				String c = null; String p = null;
//				if(imb != null){
//					c = imb.getDeclaringClass().getName();//class name
//					p = imb.getDeclaringClass().getPackage().getName();//package name
//				}
//				ReferMethod rm = ReferMethod.fromMethodDeclaration(md, absolutePath, null);
//				if(rm.isValid()){
//					List<String> lines = SourceCode.parseLines(codeBlock);
//					rm.setLines(lines, time, author);
//					referMethods.add(rm);
//				}
//			}				
//		}
//		return referMethods;
//	}
	
	private List<MethodDeclaration> getMethodDeclarations(final ICompilationUnit iCompilationUnit){
		//TODO: parse method invocations
		CompilationUnit u = (CompilationUnit) TreeParsers.parse(iCompilationUnit);
		return (u != null) ? NodeCollectors.ofClass(MethodDeclaration.class).apply(u) : new ArrayList<MethodDeclaration>();
	}


	@Override
	public void onCompilationUnit(final ICompilationUnit iCompilationUnit) {
		System.out.println("path: " + iCompilationUnit.getPath());
		CompilationUnit u = (CompilationUnit) TreeParsers.parse(iCompilationUnit);
		List<MethodDeclaration> methods = NodeCollectors.ofClass(MethodDeclaration.class).apply(u);
		for(MethodDeclaration md : methods){
			IMethodBinding imb = md.resolveBinding();
			if(imb != null)
				System.out.println("name: " + md.getName().toString() + "declaring class je: "+imb.getDeclaringClass().getName() + "   in: " + imb.getDeclaringClass().getPackage().getName());
		}
	}
}
