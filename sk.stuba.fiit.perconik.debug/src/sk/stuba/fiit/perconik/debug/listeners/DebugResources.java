package sk.stuba.fiit.perconik.debug.listeners;

import java.util.Set;
import sk.stuba.fiit.perconik.core.listeners.Resource;
import sk.stuba.fiit.perconik.core.listeners.Resources;
import sk.stuba.fiit.perconik.core.utilities.PluginConsole;
import sk.stuba.fiit.perconik.core.utilities.SmartStringBuilder;
import sk.stuba.fiit.perconik.core.utilities.Strings;

public final class DebugResources
{
	private DebugResources()
	{
		throw new AssertionError();
	}
	
	public static final String toString(final Resource<?> resource)
	{
		return Strings.toCanonicalString(resource);
	}
	
	public static final void printRegistered(final PluginConsole console)
	{
		console.put(dumpRegisteredMap());
	}	

	private static final String dumpRegisteredMap()
	{
		SmartStringBuilder builder = new SmartStringBuilder();
		
		builder.appendln("Registered resources:").tab();
		
		Set<Resource<?>> resources = Resources.getResources();

		if (!resources.isEmpty())
		{
			for (Resource<?> resource: resources)
			{
				builder.appendln(toString(resource));
			}
		}
		else
		{
			builder.appendln("none");
		}
		
		return builder.toString();
	}
}