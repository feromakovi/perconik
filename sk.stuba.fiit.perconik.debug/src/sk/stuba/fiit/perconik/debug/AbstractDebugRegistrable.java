package sk.stuba.fiit.perconik.debug;

import sk.stuba.fiit.perconik.debug.runtime.DebugConsole;

public abstract class AbstractDebugRegistrable extends AbstractDebugObject implements DebugRegistrable
{
	protected AbstractDebugRegistrable()
	{
	}
	
	protected AbstractDebugRegistrable(final DebugConsole console)
	{
		super(console);
	}
	
	public void preRegister()
	{
	}

	public void postRegister()
	{
	}

	public void preUnregister()
	{
	}

	public void postUnregister()
	{
	}
}
