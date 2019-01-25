package ca.servo.ai.bayes;


public class TestNaiveBayesClassifier {

	public static void main(String[] args) {
		
		try{
			
			String lang = "en";
			
			BayesClassifierNOC2016 bayes = new BayesClassifierNOC2016(lang);
			String cvTestFolder = "./data/cv/";
			//String cvFile = "jobdesc_1111";
			String cvFile = "nurse_cv";
			//String cvFile = "welder_cv";
			
			String fileInput = cvTestFolder + cvFile;
			String strInput = bayes.processTextFile(fileInput);
			
	    	//-Set model path
	    	String[] fparams = new String[5]; 
	    	
	    	fparams[0] = "./data/";
	    	fparams[1]= fparams[0]+"bayes/"+lang+"/model"; //fparams[0];         
	    	fparams[2] = fparams[0]+"bayes/"+lang+"/labelindex"; //fparams[1];       
	    	fparams[3]= fparams[0]+ "bayes/"+lang+"/noc2016-vectors/dictionary.file-0";         
	    	fparams[4] = fparams[0]+"bayes/"+lang+"/noc2016-vectors/df-count/part-r-00000";
			
			//-Classify text input from resume
			String outBayes = bayes.getNBnocLabel(fparams,strInput,10,"en");
		
			System.out.println(outBayes);
		
		
		 } catch (Exception e) {
	 			e.printStackTrace();
	     }

	}

	
	
	
	
}
