package sk.stuba.fiit.programmerproportion.handlers;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import sk.stuba.fiit.perconik.core.java.dom.NodeCollectors;
import sk.stuba.fiit.perconik.core.java.dom.TreeParsers;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.files.JavaUnitFinder;
import sk.stuba.fiit.programmerproportion.files.JavaUnitFinder.JavaUnitListener;

public class AuthorsHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
	    IWorkbenchPage activePage = window.getActivePage();
	    ISelection selection = activePage.getSelection();
	    StructuredSelection structuredSelection = (selection != null && selection instanceof StructuredSelection) ? (StructuredSelection) selection : null;
	    
	    if (structuredSelection != null && structuredSelection.getFirstElement() instanceof IJavaProject) {
	    	IJavaProject javaProject = (IJavaProject) structuredSelection.getFirstElement();
	    	IProject projectRes = javaProject.getProject();
	    	Repository repo = setupRepository(projectRes);
	    	JavaUnitFinder javaFinder = new JavaUnitFinder(javaProject);
	    	SourceFileHandler sourceFile = new SourceFileHandler(repo);
	    	javaFinder.setFileFinderListener(sourceFile);
	    	javaFinder.find();
	    } else {
	      MessageDialog.openInformation(shell, "Info",
	          "Please select a Java source file");
	    }
//	    System.out.println("Assign authors to methods");
//	    DataProvider.getInstance().iterate(new IterationListener() {
//			
//			@Override
//			public void onIterate(ReferMethod method) {
//				if(method.hasAuthor())
//					System.out.println("method: " + method.getStringRepresentation() + "   author: " + method.getAuthor());
//			}
//		});
	    //System.out.println(DataProvider.getInstance().toString());
	    return null;
	  }

	private Repository setupRepository(IProject projectRes) {
		Repository repo = GitRepositories.fromProject(projectRes);
		String branch = GitRepositories.getBranch(repo);
		if(!"master".equalsIgnoreCase(branch))
			GitRepositories.switchBranch(repo, "master");
		return repo;
	}
}
