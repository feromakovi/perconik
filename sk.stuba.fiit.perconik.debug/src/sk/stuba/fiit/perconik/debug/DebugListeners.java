package sk.stuba.fiit.perconik.debug;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.Listeners;
import sk.stuba.fiit.perconik.core.Resource;
import sk.stuba.fiit.perconik.core.Resources;
import sk.stuba.fiit.perconik.debug.listeners.CommandChangeDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.CommandExecutionDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.CommandManagerChangeDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.CompletionDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.DebugEventsDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.DocumentChangeDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.FileBufferDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.JavaElementChangeDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.LaunchesDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.OperationHistoryDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.PageDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.PartDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.PerspectiveDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.RefactoringExecutionDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.RefactoringHistoryDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.ResourceChangeDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.SelectionDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.TestRunDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.WindowDebugListener;
import sk.stuba.fiit.perconik.debug.listeners.WorkbenchDebugListener;
import sk.stuba.fiit.perconik.debug.plugin.Activator;
import sk.stuba.fiit.perconik.eclipse.core.runtime.PluginConsole;
import sk.stuba.fiit.perconik.utilities.SmartStringBuilder;
import sk.stuba.fiit.perconik.utilities.Strings;

public final class DebugListeners
{
	private DebugListeners()
	{
		throw new AssertionError();
	}
	
	public static final void registerAll()
	{
		PluginConsole console = Activator.getDefault().getConsole();
		
		// TODO load from configuration
				
//		Listeners.register(new CommandChangeDebugListener(console));
//		Listeners.register(new CommandExecutionDebugListener(console));
//		Listeners.register(new CommandManagerChangeDebugListener(console));
//		Listeners.register(new CompletionDebugListener(console));
//		Listeners.register(new DebugEventsDebugListener(console));
//		Listeners.register(new DocumentChangeDebugListener(console));
//		Listeners.register(new FileBufferDebugListener(console));
//		Listeners.register(new JavaElementChangeDebugListener(console));
//		//Listeners.register(new LaunchDebugListener(console));
//		Listeners.register(new LaunchesDebugListener(console));
//		//Listeners.register(new LaunchConfigurationDebugListener(console));
//		Listeners.register(new OperationHistoryDebugListener(console));
//		Listeners.register(new PageDebugListener(console));
//		Listeners.register(new PartDebugListener(console));
//		Listeners.register(new PerspectiveDebugListener(console));
//		Listeners.register(new RefactoringExecutionDebugListener(console));
//		Listeners.register(new RefactoringHistoryDebugListener(console));
//		Listeners.register(new ResourceChangeDebugListener(console));
		Listeners.register(new SelectionDebugListener(console));
//		Listeners.register(new TestRunDebugListener(console));
//		Listeners.register(new WindowDebugListener(console));
//		Listeners.register(new WorkbenchDebugListener(console));
	}
	
	public static final void unregisterAll()
	{
		for (Resource<?> resource: Resources.assignable(Listener.class))
		{
			resource.unregisterAll(DebugListener.class);
		}
	}

	public static final String toString(final Listener listener)
	{
		return Strings.toCanonicalString(listener);
	}

	public static final void printRegistered(final PluginConsole console)
	{
		printRegistered(console, Listener.class);
	}
	
	public static final void printRegistered(final PluginConsole console, final Class<? extends Listener> type)
	{
		console.put(dumpRegistered(type));
	}
	
	public static final void printRegisteredMap(final PluginConsole console)
	{
		console.put(dumpRegisteredMap());
	}

	static final String dumpRegistered(final Class<? extends Listener> type)
	{
		SmartStringBuilder builder = new SmartStringBuilder();
		
		builder.appendln("Registered listeners:").tab();
		
		Collection<? extends Listener> listeners = Listeners.registered(type);
		
		if (!listeners.isEmpty())
		{
			for (Listener listener: listeners)
			{
				builder.appendln(toString(listener));
			}
		}
		else
		{
			builder.appendln("none");
		}

		return builder.toString();
	}

	static final String dumpRegisteredMap()
	{
		SmartStringBuilder builder = new SmartStringBuilder();
		
		builder.appendln("Registered resource to listeners map:").tab();
		
		Map<Resource<?>, Collection<Listener>> map = Listeners.registeredMap();
		
		if (!map.isEmpty())
		{
			for (Entry<Resource<?>, Collection<Listener>> entry: map.entrySet())
			{
				Resource<?> resource = entry.getKey();
				
				builder.appendln(DebugResources.toString(resource)).tab();
	
				Collection<Listener> listeners = entry.getValue();
				
				if (!listeners.isEmpty())
				{
					for (Listener listener: listeners)
					{
						builder.appendln(toString(listener));
					}
				}
				else
				{
					builder.appendln("none");
				}
				
				builder.untab();
			}
		}
		else
		{
			builder.appendln("none");
		}

		return builder.toString();
	}
}
