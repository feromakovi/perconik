package com.gratex.perconik.activity.ide;

import java.net.URL;
import javax.xml.namespace.QName;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import sk.stuba.fiit.perconik.utilities.net.UniformResources;
import com.gratex.perconik.activity.ide.plugin.Activator;

public final class IdeActivityPreferences
{
	static final String key = Activator.PLUGIN_ID + ".preferences";
	
	static final String watcherUrl = key + ".watcher.url";
	
	static final String watcherNamespace = key + ".watcher.namespace";
	
	static final String watcherLocalPart = key + ".watcher.local";
	
	private IdeActivityPreferences()
	{
		throw new AssertionError();
	}
	
	public static final class Initializer extends AbstractPreferenceInitializer
	{
		public Initializer()
		{
		}

		@Override
		public final void initializeDefaultPreferences()
		{
			IPreferenceStore store = store();
			
			store.setDefault(watcherUrl, IdeActivityDefaults.watcherUrl.toString());
			
			store.setDefault(watcherNamespace, IdeActivityDefaults.watcherName.getNamespaceURI());
			store.setDefault(watcherLocalPart, IdeActivityDefaults.watcherName.getLocalPart());
		}
	}
	
	static final IPreferenceStore store()
	{
		return Activator.getDefault().getPreferenceStore();
	}
	
	static final URL getWatcherServiceUrl()
	{
		String content = store().getString(watcherUrl);
		
		return UniformResources.newUrl(content);
	}
	
	static final QName getWatcherServiceName()
	{
		IPreferenceStore store = store();
		
		return new QName(store.getString(watcherNamespace), store.getString(watcherLocalPart));
	}
}
