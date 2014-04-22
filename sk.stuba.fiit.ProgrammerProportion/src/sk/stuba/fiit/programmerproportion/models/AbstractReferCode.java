package sk.stuba.fiit.programmerproportion.models;

public abstract class AbstractReferCode {

	public abstract String getStringRepresentation(); 
	
	@Override
	public String toString() {
		return getStringRepresentation();
	}
}
