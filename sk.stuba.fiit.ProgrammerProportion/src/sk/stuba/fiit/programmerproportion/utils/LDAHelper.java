package sk.stuba.fiit.programmerproportion.utils;

import jgibblda.Inferencer;
import jgibblda.LDACmdOption;
import jgibblda.Model;

public class LDAHelper {
	
	public static final Model inference(final String[] words){
		LDACmdOption ldaOption = new LDACmdOption(); 
		ldaOption.inf = true; 
		ldaOption.dir = "/Users/feromakovi/Games/JGibbLDA-v.1.0/train"; 
		ldaOption.modelName = "model-final"; 
		ldaOption.niters = 100;
		Inferencer inferencer = new Inferencer(); 
		inferencer.init(ldaOption);
		Model newModel = inferencer.inference(words);
		return newModel;
	}
}
