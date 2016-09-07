import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Class to perform sentiment analysis
 *
 */
public class SentimentAnalysis {
	static StanfordCoreNLP nlpobject;

	public static LinkedList<String> revlist = new LinkedList<String>();

	public static void init() {
		
		Properties props = new Properties();
        props.setProperty("annotators",
                "tokenize, ssplit, pos, lemma, parse, sentiment");
        nlpobject = new StanfordCoreNLP(props);
	}

	

	public static int findSentiment(String rev) {
		int score = 0;
		String text = "";
		if (rev != null && rev.length() > 0) {
			int min = text.length();
			Annotation annotation = nlpobject.process(rev);
			for (int i = 0; i < annotation.get(CoreAnnotations.SentencesAnnotation.class).size(); i++) {
			
				Tree tree = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(i).get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				int sentscore = RNNCoreAnnotations.getPredictedClass(tree);
				String scoret = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(i).toString();
				if (scoret.length() > min) {
					score = sentscore;
					min = scoret.length();
				}

			}
		}
		return score;
	}
	
}