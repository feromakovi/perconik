package com.gratex.perconik.activity.ide.listeners;

import org.eclipse.core.commands.CommandEvent;
import sk.stuba.fiit.perconik.core.annotations.Unsupported;
import sk.stuba.fiit.perconik.core.listeners.CommandListener;
import com.gratex.perconik.services.vs.IdeFindOperationDto;

/**
 * A listener of {@code IdeFindOperation} events. This listener creates
 * {@link IdeFindOperationDto} data transfer objects and passes them to
 * the <i>Activity Watcher Service</i> to be transferred into the
 * <i>User Activity Client Application</i> for further processing.
 * 
 * <p> TODO document how DTOs are build and what data they contain
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
@Unsupported
public final class IdeFindListener extends IdeListener implements CommandListener
{
	public IdeFindListener()
	{
	}
	
	// TODO impl

	public void commandChanged(CommandEvent commandEvent)
	{
	}
}
