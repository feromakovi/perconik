package sk.stuba.fiit.programmerproportion.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import jgibblda.Model.Term;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import sk.stuba.fiit.programmerproportion.data.DataProvider;
import sk.stuba.fiit.programmerproportion.data.DataProvider.IterationListener;
import sk.stuba.fiit.programmerproportion.files.JavaUnitFinder;
import sk.stuba.fiit.programmerproportion.models.InvokedMethod;
import sk.stuba.fiit.programmerproportion.models.ReferAuthor;
import sk.stuba.fiit.programmerproportion.models.ReferMethod;
import sk.stuba.fiit.programmerproportion.utils.Log;
import sk.stuba.fiit.programmerproportion.utils.ModelHelper;
import sk.stuba.fiit.programmerproportion.utils.ModelHelper.LDAModel;
import sk.stuba.fiit.programmerproportion.utils.Strings;

public class AuthorsHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Shell shell = HandlerUtil.getActiveShell(event);
		FileDialog fd = new FileDialog(shell);
		fd.open();
		Log.init(fd.getFilterPath(), fd.getFileName());
		ModelHelper.BASE_PATH = fd.getFilterPath();
		ModelHelper.initIDF();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
	    IWorkbenchPage activePage = window.getActivePage();
	    ISelection selection = activePage.getSelection();
	    final StructuredSelection structuredSelection = (selection != null && selection instanceof StructuredSelection) ? (StructuredSelection) selection : null;
	   
	    ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell); 
	    
	    try {
			dialog.run(false, false, new IRunnableWithProgress(){
			    public void run(IProgressMonitor monitor) {
			        monitor.beginTask("Get some beer ...", 100);
			        
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
			        
			      //Log.println("Assign authors to methods and invoked number");
				    DataProvider.getInstance().iterate(new IterationListener() {
						
						@Override
						public void onIterate(ReferMethod method) {
							Log.get().println(Log.FOLDER_COMPLEXITY, "project", method.getStringRepresentation() + " " + method.getComplexity().toString());
							final int cLines = method.getComplexity().getLinesCount();
							Map<String,Integer> contr = method.getContributors();
							Iterator<String> authorIterator = contr.keySet().iterator();
							while(authorIterator.hasNext()){
								String author = authorIterator.next();
								Log.get().println(Log.FOLDER_COMPLEXITY, author, method.getStringRepresentation() + " " + (double)( (double)contr.get(author) / (double)cLines ));
								for(InvokedMethod im : method.getInvokedMethods())
									Log.get().println(Log.FOLDER_TECHNOLOGIES, author + "_METHOD", im.getPath() + " " + method.getStringRepresentation());
							}
							method.hasAuthor();
							for(InvokedMethod im : method.getInvokedMethods()){
								ReferMethod mapped = DataProvider.getInstance().getReferMethod(im.getPath(), im.getStringRepresentation());
								if(mapped != null)
									mapped.incrementInvocation();
							}
						}
					});
				    final Collection<Term> aInferenced = DataProvider.getInstance().inferenceProject(LDAModel.ALL);
				    Log.println("AutorsHandler, all infered: " + aInferenced.size());
				    for(Term t : aInferenced)
				    	Log.get().println(Log.FOLDER_FAMILIARITY, "projectLDA_All.txt", t.toString());
				    
				    final Collection<Term> orInferenced = DataProvider.getInstance().inferenceProject(LDAModel.OFTEN_REMOVED);
				    Log.println("AutorsHandler, no often infered: " + orInferenced.size());
				    for(Term t : orInferenced)
				    	Log.get().println(Log.FOLDER_FAMILIARITY, "projectLDA_NoOften.txt", t.toString());
				    
				    //Log.println("Write programmers knowledges, authors count: " + DataProvider.getInstance().getAuthors().size());
				    for(ReferAuthor a : DataProvider.getInstance().getAuthors()){
				    	a.onCalculateFamiliarity(aInferenced, orInferenced);
				    	Log.get().print(Log.FOLDER_FAMILIARITY, a.getStringRepresentation() + "_LDA_ALL", Strings.collectionToString(a.allLDAToCollection()));
				    	Log.get().print(Log.FOLDER_FAMILIARITY, a.getStringRepresentation() + "_LDA_NO", Strings.collectionToString(a.noOftenLDAToCollection()));
				    	Log.get().print(Log.FOLDER_FAMILIARITY, a.getStringRepresentation() + "_TFIDF", Strings.collectionToString(a.tfidfToCollection()));
				    	Log.get().print(Log.FOLDER_TECHNOLOGIES, a.getStringRepresentation() + "_ALL", Strings.collectionToString(a.technologiesToCollection()));
				    	Log.get().println(Log.FOLDER_FAMILIARITY, "cosine_similarity", a.getCosineFamiliarityInfo());
				    }

				    //Log.println(DataProvider.getInstance().toString());
				    MessageDialog.openInformation(shell, "Info", "Hotovo");
			        
			        monitor.done();
			    }
			});
		} catch (InvocationTargetException | InterruptedException e) {
			MessageDialog.openError(shell, "Error", "Exception " + e);
			e.printStackTrace();
		} 
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
