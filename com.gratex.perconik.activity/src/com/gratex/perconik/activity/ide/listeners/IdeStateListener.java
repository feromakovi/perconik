package com.gratex.perconik.activity.ide.listeners;

import static com.gratex.perconik.activity.ide.IdeActivityServices.performWatcherServiceOperation;
import static com.gratex.perconik.activity.ide.IdeDataTransferObjects.setApplicationData;
import static com.gratex.perconik.activity.ide.IdeDataTransferObjects.setEventData;
import static com.gratex.perconik.activity.ide.IdeDataTransferObjects.setProjectData;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import sk.stuba.fiit.perconik.core.listeners.LaunchListener;
import sk.stuba.fiit.perconik.core.listeners.PerspectiveListener;
import sk.stuba.fiit.perconik.eclipse.core.resources.Projects;
import com.gratex.perconik.activity.ide.IdeActivityServices.WatcherServiceOperation;
import com.gratex.perconik.services.vs.IVsActivityWatcherService;
import com.gratex.perconik.services.vs.IdeStateChangeDto;

/**
 * A listener of {@code IdeStateChange} events. This listener creates
 * {@link IdeStateChangeDto} data transfer objects and passes them to
 * the <i>Activity Watcher Service</i> to be transferred into the
 * <i>User Activity Client Application</i> for further processing.
 * 
 * <p> TODO document how DTOs are build and what data they contain
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public final class IdeStateListener extends IdeListener implements LaunchListener, PerspectiveListener
{
	public IdeStateListener()
	{
	}
	
	static final void send(final IdeStateChangeDto data)
	{
		performWatcherServiceOperation(new WatcherServiceOperation()
		{
			public final void perform(final IVsActivityWatcherService service)
			{
				service.notifyIdeStateChange(data);
			}
		});
	}
	
	static final IdeStateChangeDto build(final long time, final IProject project, final String state)
	{
		final IdeStateChangeDto data = new IdeStateChangeDto();

		data.setStateType(state);

		setProjectData(data, project);
		setApplicationData(data);
		setEventData(data, time);
		
		return data;
	}
	
	public final void launchAdded(final ILaunch launch)
	{
		final long time = currentTime();
		
		executor.execute(new Runnable()
		{
			public final void run()
			{
				IProject project = Projects.fromLaunch(launch).iterator().next();
				
				String state = launch.getLaunchMode() + " (launch)";
				
				send(build(time, project, state));
			}
		});
	}

	public final void launchRemoved(final ILaunch launch)
	{
	}

	public final void launchChanged(final ILaunch launch)
	{
	}

	public final void perspectiveOpened(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor)
	{
	}

	public final void perspectiveClosed(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor)
	{
	}

	public final void perspectiveActivated(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor)
	{
		final long time = currentTime();
		
		executor.execute(new Runnable()
		{
			public final void run()
			{
				IProject project = Projects.fromPage(page);

				String state = descriptor.getLabel().toLowerCase() + " (perspective)";

				send(build(time, project, state));
			}
		});
	}

	public final void perspectiveDeactivated(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor)
	{
	}

	public final void perspectivePreDeactivate(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor)
	{
	}

	public final void perspectiveChanged(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor, final String change)
	{
	}

	public final void perspectiveChanged(final IWorkbenchPage page, final IPerspectiveDescriptor descriptor, final IWorkbenchPartReference reference, final String change)
	{
	}

	public final void perspectiveSavedAs(final IWorkbenchPage page, final IPerspectiveDescriptor before, final IPerspectiveDescriptor after)
	{
	}
}
