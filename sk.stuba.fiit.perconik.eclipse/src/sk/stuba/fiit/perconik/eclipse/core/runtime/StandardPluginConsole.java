package sk.stuba.fiit.perconik.eclipse.core.runtime;

import java.io.PrintStream;
import javax.annotation.Nullable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import com.google.common.base.Preconditions;

final class StandardPluginConsole implements PluginConsole
{
	private final Plugin plugin;
	
	private final PrintStream out;
	
	StandardPluginConsole(final Plugin plugin, final PrintStream out)
	{
		this.plugin = Preconditions.checkNotNull(plugin);
		this.out    = Preconditions.checkNotNull(out);
	}
	
	private final void log(final Status status)
	{
		this.plugin.getLog().log(status);
	}
	
	public final void put(@Nullable final String message)
	{
		this.out.print(message);
	}

	public final void put(final String format, final Object ... args)
	{
		put(String.format(format, args));
	}

	public final void print(@Nullable final String message)
	{
		this.out.println(message);
	}
	
	public final void print(final String format, final Object ... args)
	{
		print(String.format(format, args));
	}

	public final void notice(final String message)
	{
		log(new Status(IStatus.INFO, this.getPluginId(), message));
	}
	
	public final void notice(final String format, Object ... args)
	{
		notice(String.format(format, args));
	}
	
	public final void warning(final String message)
	{
		log(new Status(IStatus.WARNING, this.getPluginId(), message));
	}

	public final void warning(final String format, Object ... args)
	{
		warning(String.format(format, args));
	}

	public final void error(final String message)
	{
		log(new Status(IStatus.ERROR, this.getPluginId(), message));
	}

	public final void error(final String format, Object ... args)
	{
		error(String.format(format, args));
	}

	public final void error(final Throwable failure, final String message)
	{
		log(new Status(IStatus.ERROR, this.getPluginId(), message, failure));
	}

	public final void error(final Throwable failure, final String format, Object ... args)
	{
		error(failure, String.format(format, args));
	}

	private final String getPluginId()
	{
		return this.plugin.getBundle().getSymbolicName();
	}
}