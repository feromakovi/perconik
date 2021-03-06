package sk.stuba.fiit.perconik.utilities;

import javax.annotation.Nullable;
import com.google.common.annotations.Beta;

/**
 * Static utility methods for unsorted and preferably small arrays.
 * Methods in this class use basic and very simple algorithms for
 * array operations. These algorithms perform best on unsorted small
 * arrays.
 *
 * @author Pavol Zbell
 * @since 1.0
 */
@Beta
public final class MoreArrays
{
	private MoreArrays()
	{
		throw new AssertionError();
	}
	
	public static final boolean contains(Object[] a, @Nullable Object key)
	{
		return search(a, key) >= 0;
	}
	
	public static final int search(Object[] a, @Nullable Object key)
	{
		int length = a.length;
		
		for (int i = 0; i < length; i ++)
		{
			Object o = a[i];
			
			if (key == null ? o == null : key.equals(o))
			{
				return i;
			}
		}
		
		return -1;
	}
}
