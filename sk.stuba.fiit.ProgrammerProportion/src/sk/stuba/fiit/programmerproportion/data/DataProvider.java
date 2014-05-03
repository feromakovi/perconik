package sk.stuba.fiit.programmerproportion.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.programmerproportion.models.ReferAuthor;
import sk.stuba.fiit.programmerproportion.models.ReferClass;
import sk.stuba.fiit.programmerproportion.models.ReferMethod;

public final class DataProvider {
	
	private final Map<String, HashMap<String, ReferMethod>> mMethods = new HashMap<String,HashMap<String,ReferMethod>>();//mapping path to hashmap of method representation and method
	private final Map<String, ReferClass> mClasses = new HashMap<String, ReferClass>();
	private final Map<String, ReferAuthor> mAuthors = new HashMap<String, ReferAuthor>();

	private static final DataProvider INSTANCE = new DataProvider();
	
	public final static DataProvider getInstance(){
		return INSTANCE;
	}
	
	public void initialize(){
		if(!this.mMethods.isEmpty())
			this.mMethods.clear();
	}
	
	// Handling methods	
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
	
	public Map<String,ReferMethod> getMethodsForPath(final String path){
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
	
	// Handling classes
	public void addClass(ReferClass clas){
		this.mClasses.put(clas.getStringRepresentation(), clas);
	}
	
	public ReferClass getClass(String filePath){
		if(this.mClasses.containsKey(filePath))
			return this.mClasses.get(filePath);
		return null;
	}
	
	public Collection<ReferClass> getClasses(){
		return this.mClasses.values();
	}
	
	// Handling authors
	public ReferAuthor addAuthor(ReferAuthor author){
		this.mAuthors.put(author.getStringRepresentation(), author);
		return this.getAuthor(author.getStringRepresentation());
	}
	
	public void updateAuthorsContribution(ReferClass clas){
		Map<String, Integer> aToL = clas.getContribution();
		Iterator<String> aIterator = aToL.keySet().iterator();
		while(aIterator.hasNext()){
			String authRep = aIterator.next();
			Integer lCount = aToL.get(authRep);
			ReferAuthor refAuth = getAuthor(authRep);
			if(refAuth == null)
				refAuth = addAuthor(new ReferAuthor(authRep));
			refAuth.updateByClass(clas, lCount);
//			for(String t : clas.getAllLDATopics())
//				refAuth.updateTopics(t, lCount);
		}
	}
	
	public ReferAuthor getAuthor(String authorRepresentation){
		if(this.mAuthors.containsKey(authorRepresentation))
			return this.mAuthors.get(authorRepresentation);
		return null;
	}
	
	public Collection<ReferAuthor> getAuthors(){
		return this.mAuthors.values();
	}
}
