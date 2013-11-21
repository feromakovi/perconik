package sk.stuba.fiit.programmerproportion.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.programmerproportion.models.ReferMethod;

public final class DataProvider {
	
	private final Map<String, HashMap<String, ReferMethod>> mMethods = new HashMap<String,HashMap<String,ReferMethod>>();//mapping path to hashmap of method representation and method

	private static final DataProvider INSTANCE = new DataProvider();
	
	public final static DataProvider getInstance(){
		return INSTANCE;
	}
	
	public void initialize(){
		if(!this.mMethods.isEmpty())
			this.mMethods.clear();
	}
	
	public void update(final List<ReferMethod> methods){
		for(ReferMethod m : methods){
			final Map<String, ReferMethod> refs = getMethodsForPath(m.getPath());
			if(refs != null){
				if(refs.containsKey(m.getStringRepresentation())){
					ReferMethod selected = refs.get(m.getStringRepresentation());
					selected.updateLineAuthors(m.getLines());
				}
			}			
		}
	}
	
	public void insert(final List<ReferMethod> methods){
		for(ReferMethod m : methods){
			final Map<String, ReferMethod> refs = getMethodsForPath(m.getPath());
			if(refs != null)
				refs.put(m.getStringRepresentation(), m);
		}
	}
	
	private Map<String,ReferMethod> getMethodsForPath(final String path){
		if(!this.mMethods.containsKey(path))
			this.mMethods.put(path, new HashMap<String,ReferMethod>());
		return this.mMethods.get(path);
	}
	
	public void iterate(final IterationListener listener){
		if(listener == null)
			throw new NullPointerException("IterationListener isntance is null");
		
		Iterator<HashMap<String, ReferMethod>> fileIterator = this.mMethods.values().iterator();
		while(fileIterator.hasNext()){
			Iterator<ReferMethod> methodIterator = fileIterator.next().values().iterator();
			while(methodIterator.hasNext())
				listener.onIterate(methodIterator.next());
		}
	}
	
	public ReferMethod getReferMethod(final String referMethodPath, final String referMethodRepresentation){
		if(this.mMethods.containsKey(referMethodPath)){
			Map<String,ReferMethod> mapMethods = this.mMethods.get(referMethodPath);
			if(mapMethods.containsKey(referMethodRepresentation))
				return mapMethods.get(referMethodRepresentation);
		}			
		return null;
	}
	
	public static interface IterationListener{
		public void onIterate(ReferMethod method);
	}
	
	@Override
	public String toString() {
		String value = new String();
		Iterator<String> files = this.mMethods.keySet().iterator();
		while(files.hasNext()){
			final String file = files.next();
			final Map<String,ReferMethod> refs = getMethodsForPath(file);
			if(refs != null){
				value += "############################ File - " + file + "############################" + "\n";
				Iterator<ReferMethod> methods = refs.values().iterator();
				while(methods.hasNext()){
					value += methods.next().toString();
				}
			}
			value += "\n";
		}
		return value;
	}
}
