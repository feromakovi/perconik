package sk.stuba.fiit.perconik.core.resources;

import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.Resource;
import sk.stuba.fiit.perconik.core.listeners.CommandListener;
import sk.stuba.fiit.perconik.core.listeners.CommandExecutionListener;
import sk.stuba.fiit.perconik.core.listeners.CommandManagerListener;
import sk.stuba.fiit.perconik.core.listeners.CompletionListener;
import sk.stuba.fiit.perconik.core.listeners.DebugEventsListener;
import sk.stuba.fiit.perconik.core.listeners.DocumentListener;
import sk.stuba.fiit.perconik.core.listeners.FileBufferListener;
import sk.stuba.fiit.perconik.core.listeners.JavaElementListener;
import sk.stuba.fiit.perconik.core.listeners.LaunchConfigurationListener;
import sk.stuba.fiit.perconik.core.listeners.LaunchListener;
import sk.stuba.fiit.perconik.core.listeners.LaunchesListener;
import sk.stuba.fiit.perconik.core.listeners.OperationHistoryListener;
import sk.stuba.fiit.perconik.core.listeners.PageListener;
import sk.stuba.fiit.perconik.core.listeners.PartListener;
import sk.stuba.fiit.perconik.core.listeners.PerspectiveListener;
import sk.stuba.fiit.perconik.core.listeners.RefactoringExecutionListener;
import sk.stuba.fiit.perconik.core.listeners.RefactoringHistoryListener;
import sk.stuba.fiit.perconik.core.listeners.ResourceListener;
import sk.stuba.fiit.perconik.core.listeners.SelectionListener;
import sk.stuba.fiit.perconik.core.listeners.TestRunListener;
import sk.stuba.fiit.perconik.core.listeners.WindowListener;
import sk.stuba.fiit.perconik.core.listeners.WorkbenchListener;
import sk.stuba.fiit.perconik.core.services.resources.ResourceManagers;
import sk.stuba.fiit.perconik.core.services.resources.ResourceProvider;
import sk.stuba.fiit.perconik.core.services.resources.ResourceProvider.Builder;
import sk.stuba.fiit.perconik.core.services.resources.ResourceProviders;
import sk.stuba.fiit.perconik.core.services.resources.ResourceService;
import sk.stuba.fiit.perconik.core.services.resources.ResourceServices;

// TODO consider adding
/*

add experimental

StyledTextListener extends org.eclipse.swt.widgets.Listener

 */

public class DefaultResources
{
	static final Resource<CommandListener> commandChange;

	static final Resource<CommandExecutionListener> commandExecution;

	static final Resource<CommandManagerListener> commandManagerChange;

	static final Resource<CompletionListener> completion;

	static final Resource<DebugEventsListener> debugEvents;

	static final Resource<DocumentListener> documentChange;

	static final Resource<FileBufferListener> fileBuffer;

	static final Resource<JavaElementListener> javaElementChange;

	static final Resource<LaunchListener> launch;
	
	static final Resource<LaunchesListener> launches;

	static final Resource<LaunchConfigurationListener> launchConfiguration;
	
	static final Resource<OperationHistoryListener> operationHistory;
	
	static final Resource<PageListener> page;
	
	static final Resource<PartListener> part;
	
	static final Resource<PerspectiveListener> perspective;

	static final Resource<RefactoringExecutionListener> refactoringExecution;

	static final Resource<RefactoringHistoryListener> refactoringHistory;

	static final Resource<ResourceListener> resourceChange;

	static final Resource<SelectionListener> selection;
	
	static final Resource<TestRunListener> testRun;

	static final Resource<WindowListener> window;

	static final Resource<WorkbenchListener> workbench;
	
	static final ResourceProvider provider;

	static
	{
		Builder builder = ResourceProviders.builder();
		
		commandChange        = forge(CommandListener.class, CommandHandler.INSTANCE, builder);
		commandExecution     = forge(CommandExecutionListener.class, CommandExecutionHandler.INSTANCE, builder);
		commandManagerChange = forge(CommandManagerListener.class, CommandManagerHandler.INSTANCE, builder);
		completion           = forge(CompletionListener.class, CompletionHandler.INSTANCE, builder);
		debugEvents          = forge(DebugEventsListener.class, DebugEventsHandler.INSTANCE, builder);
		documentChange       = forge(DocumentListener.class, DocumentHandler.INSTANCE, builder);
		fileBuffer           = forge(FileBufferListener.class, FileBufferHandler.INSTANCE, builder);
		javaElementChange    = forge(JavaElementListener.class, JavaElementHandler.INSTANCE, builder);
		launch               = forge(LaunchListener.class, LaunchHandler.INSTANCE, builder);
		launchConfiguration  = forge(LaunchConfigurationListener.class, LaunchConfigurationHandler.INSTANCE, builder);
		launches             = forge(LaunchesListener.class, LaunchesHandler.INSTANCE, builder);
		operationHistory     = forge(OperationHistoryListener.class, OperationHistoryHandler.INSTANCE, builder);
		page                 = forge(PageListener.class, PageHandler.INSTANCE, builder);
		part                 = forge(PartListener.class, PartHandler.INSTANCE, builder);
		perspective          = forge(PerspectiveListener.class, PerspectiveHandler.INSTANCE, builder);
		refactoringExecution = forge(RefactoringExecutionListener.class, RefactoringExecutionHandler.INSTANCE, builder);
		refactoringHistory   = forge(RefactoringHistoryListener.class, RefactoringHistoryHandler.INSTANCE, builder);
		resourceChange       = forge(ResourceListener.class, ResourceHandler.INSTANCE, builder);
		selection            = forge(SelectionListener.class, SelectionHandler.INSTANCE, builder);
		testRun              = forge(TestRunListener.class, TestRunHandler.INSTANCE, builder);
		window               = forge(WindowListener.class, WindowHandler.INSTANCE, builder);
		workbench            = forge(WorkbenchListener.class, WorkbenchHandler.INSTANCE, builder);
		
		provider = builder.build();
	}
	
	private DefaultResources()
	{
		throw new AssertionError();
	}
	
	private static final class ServiceHolder
	{
		static final ResourceService service;
		
		static
		{
			service = ResourceServices.create(provider, ResourceManagers.create());
		}
		
		private ServiceHolder()
		{
			throw new AssertionError();
		}
	}

	public static final ResourceService getDefaultResourceService()
	{
		return ServiceHolder.service;
	}
	
	public static final String getName(final Resource<?> resource)
	{
		if (resource instanceof GenericResource)
		{
			return ((GenericResource<?>) resource).getName();
		}
		
		return null;
	}

	private static final <L extends Listener> Resource<L> forge(final Class<L> type, final Handler<L> handler, final Builder builder)
	{
		Resource<L> resource = new GenericResource<>(Pools.getListenerPoolFactory().create(handler));

		builder.add(type, resource);
		
		return resource;
	}

	public static final Resource<CommandListener> getCommandChangeResource()
	{
		return DefaultResources.commandChange;
	}

	public static final Resource<CommandExecutionListener> getCommandExecutionResource()
	{
		return DefaultResources.commandExecution;
	}

	public static final Resource<CommandManagerListener> getCommandManagerChangeResource()
	{
		return DefaultResources.commandManagerChange;
	}

	public static final Resource<CompletionListener> getCompletionResource()
	{
		return DefaultResources.completion;
	}

	public static final Resource<DebugEventsListener> getDebugEventsResource()
	{
		return DefaultResources.debugEvents;
	}

	public static final Resource<DocumentListener> getDocumentChangeResource()
	{
		return DefaultResources.documentChange;
	}

	public static final Resource<FileBufferListener> getFileBufferResource()
	{
		return DefaultResources.fileBuffer;
	}

	public static final Resource<JavaElementListener> getJavaElementChangeResource()
	{
		return DefaultResources.javaElementChange;
	}

	public static final Resource<LaunchListener> getLaunchResource()
	{
		return DefaultResources.launch;
	}
	
	public static final Resource<LaunchesListener> getLaunchesResource()
	{
		return DefaultResources.launches;
	}

	public static final Resource<LaunchConfigurationListener> getLaunchConfigurationResource()
	{
		return DefaultResources.launchConfiguration;
	}

	public static final Resource<OperationHistoryListener> getOperationHistoryResource()
	{
		return DefaultResources.operationHistory;
	}

	public static final Resource<PageListener> getPageResource()
	{
		return DefaultResources.page;
	}

	public static final Resource<PartListener> getPartResource()
	{
		return DefaultResources.part;
	}

	public static final Resource<PerspectiveListener> getPerspectiveResource()
	{
		return DefaultResources.perspective;
	}

	public static final Resource<RefactoringExecutionListener> getRefactoringExecutionResource()
	{
		return DefaultResources.refactoringExecution;
	}

	public static final Resource<RefactoringHistoryListener> getRefactoringHistoryResource()
	{
		return DefaultResources.refactoringHistory;
	}

	public static final Resource<ResourceListener> getResourceChangeResource()
	{
		return DefaultResources.resourceChange;
	}

	public static final Resource<SelectionListener> getSelectionResource()
	{
		return DefaultResources.selection;
	}

	public static final Resource<TestRunListener> getTestRunResource()
	{
		return DefaultResources.testRun;
	}

	public static final Resource<WindowListener> getWindowResource()
	{
		return DefaultResources.window;
	}

	public static final Resource<WorkbenchListener> getWorkbenchResource()
	{
		return DefaultResources.workbench;
	}
}
