package sk.stuba.fiit.programmerproportion.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jgibblda.Inferencer;
import jgibblda.LDACmdOption;
import jgibblda.Model;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class ModelHelper {
	
	public static String BASE_PATH = null;
	public static int IDF_DOCUMENTS_COUNT = 0;
	
	private static final String LDA_MODEL_NAME = "model-final";
	private static final String TFIDF_MODEL_NAME = "words.txt";
	
	private static final String FOLDER_ALL = "all";
	private static final String FOLDER_WITHOUT_OFTEN = "without_often";
	
	private static final Map<String, Integer> IDF_MODEL = new HashMap<String, Integer>();
	
	public enum LDAModel{
		ALL, OFTEN_REMOVED;
		
		public String getPath(){
			switch (this) {
			case ALL:
				return BASE_PATH + File.separator + FOLDER_ALL + File.separator;
			case OFTEN_REMOVED:
				return BASE_PATH + File.separator + FOLDER_WITHOUT_OFTEN + File.separator;
			}
			return null;
		};
	}
	
	private static final int MAX_TOPIC_TERMS = 8;
	
	public static final Collection<String> inference(final String[] words, final LDAModel model){
		return inference(words, model, MAX_TOPIC_TERMS);
	}
	
	public static final Collection<String> inference(final String[] words, final LDAModel model, int maxTopicsCount){
//		String modelPath = null;
//		try{
//			Bundle bundle = Platform.getBundle("sk.stuba.fiit.ProgrammerProportion");
//			URL folderURL = bundle.getEntry("model");
//			modelPath = FileLocator.resolve(folderURL).getPath() + File.separator;	
//		}catch(Exception e){}	
		System.out.println("from inferencing model path: " + model.getPath());
		LDACmdOption ldaOption = new LDACmdOption(); 
		ldaOption.inf = true; 
		ldaOption.dir = model.getPath();
		ldaOption.modelName = LDA_MODEL_NAME; 
		ldaOption.niters = 100;
		Inferencer inferencer = new Inferencer(); 
		inferencer.init(ldaOption);
		Model m = inferencer.inference(words);
		return m.getTopicTerms(maxTopicsCount);
	}
	
	public static final void initIDF(){
		try {
			File words = new File(BASE_PATH + File.separator + FOLDER_ALL + File.separator + TFIDF_MODEL_NAME);
			LineProcessor<Set<String>> lineProcessor = new LineProcessor<Set<String>>() {

				@Override
				public Set<String> getResult() {return null;}

				@Override
				public boolean processLine(String line) throws IOException {
					if(line != null && line.length() > 0){
						String[] w = line.split(" ");
						Set<String> set = new HashSet<String>();
						if(w != null && w.length > 3){
							IDF_DOCUMENTS_COUNT++;
							for(String s : w)
								set.add(s);
							Iterator<String> i = set.iterator();
							while(i.hasNext()){
								String t = i.next();
								if(IDF_MODEL.containsKey(t))
									IDF_MODEL.put(t, (IDF_MODEL.get(t) + 1));
								else
									IDF_MODEL.put(t, 1);
							}
						}
					}
					return true;
				}
			};		
			Files.readLines(words, Charsets.UTF_8, lineProcessor);
			System.out.println("IDF model loaded successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getPassesDocs(String word) {
		if(IDF_MODEL.containsKey(word))
			return IDF_MODEL.get(word);
		return 1;
	}
}
