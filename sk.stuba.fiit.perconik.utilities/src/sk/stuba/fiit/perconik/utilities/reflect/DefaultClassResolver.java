package sk.stuba.fiit.perconik.utilities.reflect;

enum DefaultClassResolver implements ClassResolver
{
	INSTANCE;

	public final Class<?> forName(String name) throws ClassNotFoundException
	{
		return Class.forName(name);
	}
}
