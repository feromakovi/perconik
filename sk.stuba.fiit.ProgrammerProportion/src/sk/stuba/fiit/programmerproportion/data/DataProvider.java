package sk.stuba.fiit.programmerproportion.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.programmerproportion.models.ReferMethod;

public final class DataProvider {
	
	private final Map<String,ReferMethod> mMethods = new HashMap<String,ReferMethod>();

	private static final DataProvider INSTANCE = new DataProvider();
	
	public final static DataProvider getInstance(){
		return INSTANCE;
	}
	
	public void update(final List<ReferMethod> methods){
		for(ReferMethod m : methods){
			System.out.println("method.toString()  "+ m.toString());
			if(this.mMethods.containsKey(m.getStringRepresentation())){
				ReferMethod selected = this.mMethods.get(m.getStringRepresentation());
				selected.updateLineAuthors(m.getLines());
			}
		}
	}
	
	public void insert(final List<ReferMethod> methods){
		for(ReferMethod m : methods)
			this.mMethods.put(m.getStringRepresentation(), m);
	}
}
