package com.gratex.perconik.activity.ide.listeners;

import static com.gratex.perconik.activity.ide.IdeActivityServices.performWatcherServiceOperation;
import static com.gratex.perconik.activity.ide.IdeDataTransferObjects.setApplicationData;
import static com.gratex.perconik.activity.ide.IdeDataTransferObjects.setEventData;
import java.io.File;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import sk.stuba.fiit.perconik.core.listeners.GitReferenceListener;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.gratex.perconik.activity.ide.IdeActivityServices.WatcherServiceOperation;
import com.gratex.perconik.activity.ide.IdeDataTransferObjects;
import com.gratex.perconik.services.vs.IVsActivityWatcherService;
import com.gratex.perconik.services.vs.IdeCheckinDto;

/**
 * A listener of {@code IdeCommit} events. This listener creates
 * {@link IdeCheckinDto} data transfer objects and passes them to
 * the <i>Activity Watcher Service</i> to be transferred into the
 * <i>User Activity Client Application</i> for further processing.
 * 
 * <p> TODO document how DTOs are build and what data they contain
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public final class IdeCommitListener extends IdeListener implements GitReferenceListener
{
	private final Object lock = new Object();
	
	@GuardedBy("lock")
	private final Map<File, Map<String, String>> cache;
	
	public IdeCommitListener()
	{
		this.cache = Maps.newHashMap();
	}
	
	private final boolean updateLastCommit(final File directory, final String branch, final String id)
	{
		Map<String, String> cache;
		
		synchronized (this.lock)
		{
			cache = this.cache.get(directory);
			
			if (cache == null)
			{
				this.cache.put(directory, cache = Maps.newHashMap());
			}
		}
		
		synchronized (cache)
		{
			String last = cache.get(branch);
			
			if (!id.equals(last))
			{
				cache.put(branch, id);
				
				return last != null;
			}
		}
		
		return false;
	}

	static final void send(final IdeCheckinDto data)
	{
		performWatcherServiceOperation(new WatcherServiceOperation()
		{
			public final void perform(final IVsActivityWatcherService service)
			{
				service.notifyIdeCheckin(data);
			}
		});
	}
	
	static final IdeCheckinDto build(final long time, final String url, final String id)
	{
		final IdeCheckinDto data = new IdeCheckinDto();

		data.setIdInRcs(id);
		data.setRcsServer(IdeDataTransferObjects.newGitServerData(url));

		setApplicationData(data);
		setEventData(data, time);
		
		return data;
	}
	
	final void process(final long time, final RefsChangedEvent event)
	{
		Repository repository = event.getRepository();
		File       directory  = repository.getDirectory();
		String     url        = GitRepositories.getRemoteOriginUrl(repository);
		
		Preconditions.checkArgument(url != null, "Unable to get remote origin url from %s", directory);
		
		String    branch = GitRepositories.getBranch(repository);
		RevCommit commit = GitRepositories.getMostRecentCommit(repository);
		
		String id = commit.getName();
		
		if (this.updateLastCommit(directory, branch, id))
		{
			send(build(time, url, id));
		}
	}

	public final void onRefsChanged(final RefsChangedEvent event)
	{
		final long time = currentTime();
		
		executor.execute(new Runnable()
		{
			public final void run()
			{
				process(time, event);
			}
		});
	}
}
