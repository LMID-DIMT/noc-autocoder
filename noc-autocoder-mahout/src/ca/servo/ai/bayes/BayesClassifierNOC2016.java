package ca.servo.ai.bayes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.StandardNaiveBayesClassifier;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.Vector.Element;
import org.apache.mahout.vectorizer.TFIDF;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import ca.servo.ai.datahandler.Utils;





public class BayesClassifierNOC2016 {
	
	
	
	protected String lang="en"; //default lang
	
	//-Constructor
	public BayesClassifierNOC2016(String lang){
		this.lang = lang;
	}
	
	//-Naive Bayes Text Classifier
    public String getNBnocLabel(String [] args,String strInput, int listSize,String lang) throws Exception{
    	
    	String nbcOut = "";
    	BayesClassifierNOC2016 bayes = new BayesClassifierNOC2016(lang);

    	
    		List<Entry<String, Double>> predictedNOCslist = new ArrayList<Entry<String, Double>>();
    		predictedNOCslist = bayes.classifiyInput2NOC2016(args,strInput, listSize);
    		
    		int c=0;
    		 //-Get Best Prediction
    		String dataPath = args[0];
            Utils fh = new Utils();
			String fileName = dataPath+"vocab/NOC_GROUP_TITLES.tsv";
			List<String> listText = new ArrayList<String>();
			listText = fh.file2Array(fileName);
    		 
 	        for(Entry<String, Double> entry:predictedNOCslist){
 	        	c++;
 	        	String noc = entry.getKey();
 	        	String nocGroupTitle = bayes.getNOCgroupTitle(listText, noc, lang);
 	        	String assyBayes = noc + " | " + nocGroupTitle;
 	        	System.out.println(assyBayes);
 	            if(c>listSize-1)break;
 	        }
    	
    	
    	return nbcOut;
    	
    }
    
   
    
    public String processTextFile(String fileInput) throws Exception {
    	
    	Utils fh = new Utils();
    	List<String> listText = new ArrayList<String>();
		listText = fh.file2Array(fileInput);
		String strOutput="";
		for(String lineText:listText) {
			strOutput = strOutput + lineText.trim();
			strOutput = strOutput.replaceAll("\\s+", " ");
		}//-end for
		return strOutput;
    	
    }
	
	private List<Entry<String, Double>> classifiyInput2NOC2016(String [] args,String strInput,int listSize)throws Exception{
		
		String dataPath = args[0]; //"./data/";
        String modelPath = args[1]; //dataPath+"bayes_noc2016/"+lang+"/model";         
        String labelIndexPath = args[2]; //dataPath+"bayes_noc2016/"+lang+"/labelindex";        
        String dictionaryPath = args[3]; //dataPath+ "bayes_noc2016/"+lang+"/noc2016-vectors/dictionary.file-0";         
        String documentFrequencyPath = args[4]; //dataPath+"bayes_noc2016/"+lang+"/noc2016-vectors/df-count/part-r-00000";          
       // String cvDataPath = dataPath+"/cv"; //args[4];  
        
        Configuration configuration = new Configuration();      
        NaiveBayesModel model = NaiveBayesModel.materialize(new Path(modelPath), configuration); 
        StandardNaiveBayesClassifier classifier = new StandardNaiveBayesClassifier(model); 
        // labels is a map label => classId
        Map<Integer, String> labels = BayesUtils.readLabelIndex(configuration, new Path(labelIndexPath));
        Map<String, Integer> dictionary = readDictionary(configuration, new Path(dictionaryPath));
        Map<Integer, Long> documentFrequency = readDocumentFrequency(configuration, new Path(documentFrequencyPath));
 
        // analyzer used to extract word from mail
        Analyzer analyzer = new StandardAnalyzer(); 
        
       // int labelCount = labels.size();
        int documentCount = documentFrequency.get(-1).intValue();
       // jsonNBLabelsObj.put("labelCount", labelCount);
       // jsonNBLabelsObj.put("documentCount", documentCount);
        
        	PreProcessorNOC2016 prep = new PreProcessorNOC2016();
        	
        	strInput = prep.sanitizeText(dataPath,strInput); //preProcessText(strInput.toLowerCase());
            
            ConcurrentHashMultiset<Object> words = ConcurrentHashMultiset.create(); 
            // extract words from mail
            TokenStream ts = analyzer.tokenStream("text", new StringReader(strInput));         
            CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
            ts.reset();
            int wordCount = 0;
            while (ts.incrementToken()) {
                if (termAtt.length() > 0) {
                    String word = ts.getAttribute(CharTermAttribute.class).toString();
                    Integer wordId = dictionary.get(word);
                    // if the word is not in the dictionary, skip it
                    if (wordId != null) {
                        words.add(word);
                        wordCount++;
                    }
                }
            }
 
            // create vector wordId - weight using tfidf
            Vector vector = new RandomAccessSparseVector(10000);
            TFIDF tfidf = new TFIDF();
            for (Multiset.Entry entry:words.entrySet()) {
                String word =  (String)entry.getElement();
                int count = entry.getCount();
                Integer wordId = dictionary.get(word);
                Long freq = documentFrequency.get(wordId);
                double tfIdfValue = tfidf.calculate(count, freq.intValue(), wordCount, documentCount);
                vector.setQuick(wordId, tfIdfValue);
            }
            // With the classifier, we get one score for each label
            // The label with the highest score is the one the mail is more likely to
            // be associated to
            Vector resultVector = classifier.classifyFull(vector);
            double bestScore = -Double.MAX_VALUE;
            int bestCategoryId = -1;
          
            
            Map<String, Double> mapBayes = new HashMap<String, Double>();
            
            for(int i=0 ;i<resultVector.size();i++) {
            	Element e1  = resultVector.getElement(i);
                int categoryId = e1.index();
                double score = e1.get();
                if (score > bestScore) {
                    bestScore = score;
                    bestCategoryId = categoryId;
                }
                mapBayes.put(labels.get(categoryId), score);
                
            }//-end for
            
           
		
		 Set<Entry<String, Double>> set = mapBayes.entrySet();
		List<Entry<String, Double>> predictedNOCslist = new ArrayList<Entry<String, Double>>(set);
		
		Collections.sort(predictedNOCslist, new Comparator<Map.Entry<String, Double>>()
        {
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
		
		 ts.close();
		 
		 return predictedNOCslist;

	}
	
	
	private String getNOCgroupTitle(List<String> listText, String noc, String lang)throws Exception{
		
		String nocGroupTitle="NA";
		int c=0;
		for(String ngTsv:listText){
			c++;
			//-Ignore header names on first row
			if(c>1){
				String [] ngData = ngTsv.split("\t"); //-tab delimited file
				String noc2 = ngData[0].trim();
				if(noc.equals(noc2)){
					nocGroupTitle=ngData[1].trim();
					if(lang.equals("fr"))nocGroupTitle=ngData[2].trim();
					break;
				}
			}//-end if
		}//-end for
		
		return nocGroupTitle;
	}
	
	
    private Map<String, Integer> readDictionary(Configuration conf, Path dictionaryPath) {
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(dictionaryPath, true, conf)) {
            dictionary.put(pair.getFirst().toString(), pair.getSecond().get());
        }
        return dictionary;
    }
 
    private Map<Integer, Long> readDocumentFrequency(Configuration conf, Path documentFrequencyPath) {
        Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
        for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf)) {
            documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
        }
        return documentFrequency;
    }
	

}
