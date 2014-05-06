package sk.stuba.fiit.programmerproportion.utils;

import java.util.*;
import java.lang.Math;
import jgibblda.Model.Term;

public final class CosineSimilarity {

	/**
	 * calculate the cosine similarity between feature vectors of two clusters
	 * 
	 * The feature vector is represented as HashMap<String, Double>. 
	 * 
	 * @param firstFeatures The feature vector of the first cluster
	 * @param secondFeatures The feature vector of the second cluster 
	 * @return the similarity measure
	 */
	public static Double calculateCosineSimilarity(HashMap<String, Double> firstFeatures, HashMap<String, Double> secondFeatures) {
		Double similarity = 0.0;
		Double sum = 0.0;	// the numerator of the cosine similarity
		Double fnorm = 0.0;	// the first part of the denominator of the cosine similarity
		Double snorm = 0.0;	// the second part of the denominator of the cosine similarity
		Set<String> fkeys = firstFeatures.keySet();
		Iterator<String> fit = fkeys.iterator();
		while (fit.hasNext()) {
			String featurename = fit.next();
			boolean containKey = secondFeatures.containsKey(featurename);
			if (containKey) {
				sum = sum + firstFeatures.get(featurename) * secondFeatures.get(featurename);
			}
		}
		fnorm = calculateNorm(firstFeatures);
		snorm = calculateNorm(secondFeatures);
		similarity = sum / (fnorm * snorm);
		return similarity;
	}
	
	/**
	 * calculate the norm of one feature vector
	 * 
	 * @param feature of one cluster
	 * @return
	 */
	public static Double calculateNorm(HashMap<String, Double> feature) {
		Double norm = 0.0;
		Set<String> keys = feature.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String featurename = it.next();
			norm = norm + Math.pow(feature.get(featurename), 2);
		}
		return Math.sqrt(norm);
	}

	public static double calculateCosineSimilarity(Map<String, Integer> topics, Collection<Term> terms) {
		HashMap<String, Double> f = new HashMap<String, Double>();
		HashMap<String, Double> s = new HashMap<String, Double>();
		Iterator<String> tIt = topics.keySet().iterator();
		while(tIt.hasNext()){
			String w = tIt.next();
			double i = topics.get(w);
			f.put(w, i);
		}
		for(Term t : terms)
			s.put(t.getWord(), t.getValue());
		return calculateCosineSimilarity(f, s);
	}
}
