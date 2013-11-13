package sk.stuba.fiit.programmerproportion.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.files.FileFinder;
import sk.stuba.fiit.programmerproportion.files.FileFinder.FileFinderListener;

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
	    	FileFinder ff = new FileFinder(FileFinder.PATTERN_JAVA);
	    	SourceFileHandler sourceFile = new SourceFileHandler();
	    	ff.setFileFinderListener(sourceFile);
	    	ff.find(new File(projectRes.getLocationURI().getPath()));
//	    	Repository repo = GitRepositories.fromProject(projectRes);
//	    	String branch = GitRepositories.getBranch(repo);
//	    	System.out.println("Currently working branch is: " + branch);
	    	
	    } else {
	      MessageDialog.openInformation(shell, "Info",
	          "Please select a Java source file");
	    }
	    return null;
	  }
}
