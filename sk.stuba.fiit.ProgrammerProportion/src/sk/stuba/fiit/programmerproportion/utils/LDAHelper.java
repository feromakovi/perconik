package sk.stuba.fiit.programmerproportion.utils;

import java.io.File;
import java.net.URL;

import jgibblda.Inferencer;
import jgibblda.LDACmdOption;
import jgibblda.Model;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class LDAHelper {
	
	public static final Model inference(final String[] words){
		String modelPath = null;
		try{
			Bundle bundle = Platform.getBundle("sk.stuba.fiit.ProgrammerProportion");
			URL folderURL = bundle.getEntry("model");
			modelPath = FileLocator.resolve(folderURL).getPath() + File.separator;	
		}catch(Exception e){}	
		LDACmdOption ldaOption = new LDACmdOption(); 
		ldaOption.inf = true; 
		ldaOption.dir = modelPath;
		ldaOption.modelName = "model-final"; 
		ldaOption.niters = 100;
		Inferencer inferencer = new Inferencer(); 
		inferencer.init(ldaOption);
		Model newModel = inferencer.inference(words);
		return newModel;
	}
}
