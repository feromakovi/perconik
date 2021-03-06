package sk.stuba.fiit.perconik.core;

import javax.annotation.Nullable;

/**
 * Thrown when one of the core service classes tries to load in a listener
 * through its class but no definition of the listener could be found.
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public class ListenerNotFoundException extends IllegalStateException
{
	private static final long serialVersionUID = 0;

	/**
	 * Creates a new instance with no detail message.
	 */
	public ListenerNotFoundException()
	{
		super();
	}

	/**
	 * Creates a new instance with the given detail message.
	 */
	public ListenerNotFoundException(@Nullable String message)
	{
		super(message);
	}

	/**
	 * Creates a new instance with the given detail message and cause.
	 */
	public ListenerNotFoundException(@Nullable String message, @Nullable Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Creates a new instance with the given cause.
	 */
	public ListenerNotFoundException(@Nullable Throwable cause)
	{
		super(cause);
	}
}
