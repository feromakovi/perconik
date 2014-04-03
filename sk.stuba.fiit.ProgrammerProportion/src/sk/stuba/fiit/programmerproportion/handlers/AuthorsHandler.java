package sk.stuba.fiit.programmerproportion.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.data.DataProvider;
import sk.stuba.fiit.programmerproportion.data.DataProvider.IterationListener;
import sk.stuba.fiit.programmerproportion.files.JavaUnitFinder;
import sk.stuba.fiit.programmerproportion.models.InvokedMethod;
import sk.stuba.fiit.programmerproportion.models.ReferMethod;

public class AuthorsHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
	    IWorkbenchPage activePage = window.getActivePage();
	    ISelection selection = activePage.getSelection();
	    StructuredSelection structuredSelection = (selection != null && selection instanceof StructuredSelection) ? (StructuredSelection) selection : null;
	    
	    if (structuredSelection != null && structuredSelection.getFirstElement() instanceof IJavaProject) {
	    	DataProvider.getInstance().initialize();
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
	    System.out.println("Assign authors to methods and invoked number");
	    DataProvider.getInstance().iterate(new IterationListener() {
			
			@Override
			public void onIterate(ReferMethod method) {
				method.hasAuthor();
				for(InvokedMethod im : method.getInvokedMethods()){
					ReferMethod mapped = DataProvider.getInstance().getReferMethod(im.getPath(), im.getStringRepresentation());
					if(mapped != null)
						mapped.incrementInvocation();
				}
			}
		});
	    System.out.println("Invoked numbers to all methods assigned");
	    DataProvider.getInstance().iterate(new IterationListener() {
			
			@Override
			public void onIterate(ReferMethod method) {
				if(method.hasAuthor())
					System.out.println("author: " + method.getAuthor() + "  invocation count: " + method.getInvocationCount() + "  desc: " + method.getStringRepresentation());
			}
		});
	    
	    //tu si mozem prejst svojim zoznamom triedy ktore maju topics a refer methody
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
