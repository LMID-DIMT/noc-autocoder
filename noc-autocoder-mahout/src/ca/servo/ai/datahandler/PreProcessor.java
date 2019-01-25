package ca.servo.ai.datahandler;

import java.util.ArrayList;
import java.util.List;

import org.tartarus.snowball.ext.PorterStemmer;
import org.tartarus.snowball.ext.FrenchStemmer;

public class PreProcessor {
	
	
	public String extractNocTerms(String noc,String fileName,String lang) throws Exception{
		
		String nocTerms="";
		List<String> listNocWords = new ArrayList<String>();
		
		FileHandler fh = new FileHandler();
		listNocWords = fh.file2Array(fileName);
		
		//System.out.println(fileName+"=> "+listNocWords.size());
		
		
		for(String nocWords:listNocWords){
			
			String [] nocData = nocWords.split("\t"); //-tab delimited file
			
			if(nocData.length>1){
				String noc2 = nocData[0].trim();
				String nocText=nocData[1].trim();
				
				
				if(noc.equals(noc2)){
					
					if(lang.equals("en"))nocText = sanitizeText(nocText);
					if(lang.equals("fr"))nocText = sanitizeTextFr(nocText);
					
					nocTerms = nocTerms+" "+nocText;
					
				}//-end if
			
			
			}
			
		}
		
		
		return nocTerms.trim();
		
		
	}
	
	
	
	public String sanitizeText(String text) throws Exception{
		String stopWordFile = "./data/vocab/en/stop-word-list.txt";
		String noiseWordFile = "./data/vocab/en/noise_en.txt";
		
		List<String> listStopWords = new ArrayList<String>();
		List<String> listNoiseWords = new ArrayList<String>();
		FileHandler fh = new FileHandler();
		listStopWords = fh.file2Array(stopWordFile);
		//-Clean up
		text = text.toLowerCase(); 
		text = text.replaceAll("_x000d_or_x000d_", " ");
		text = replaceWords(text);
		text = removeWords(listStopWords, text);
		listNoiseWords = fh.file2Array(noiseWordFile);
		text = removeWords(listNoiseWords, text);
	   	text = text.replaceAll("-", " ").replaceAll("[.]","");;
	   	text = text.replaceAll("/", " ").replaceAll("[():';]"," "); //-remove parentheses
	   	text = text.replaceAll(",", " "); //-remove comma
	   	text = text.replaceAll("\\s+", " ").trim().toLowerCase(); //-normalize spaces
	   	//-More text processing here like stemming
		text = stemInput(text);
		return text;
	}
	
	public String sanitizeTextFr(String text) throws Exception{
		String stopWordFile = "./data/vocab/fr/stopwords-fr.txt";
		String noiseWordFile = "./data/vocab/fr/noise-fr.txt";
		
		List<String> listStopWords = new ArrayList<String>();
		List<String> listNoiseWords = new ArrayList<String>();
		FileHandler fh = new FileHandler();
		listStopWords = fh.file2Array(stopWordFile);
		//-Clean up
		text = text.toLowerCase(); 
		text = text.replaceAll("_x000d_ou_x000d_", " ");
		text = replaceWords(text);
		text = removeWords(listStopWords, text);
		listNoiseWords = fh.file2Array(noiseWordFile);
		text = removeWords(listNoiseWords, text);
	   	text = text.replaceAll("-", " ").replaceAll("[.]","");;
	   	text = text.replaceAll("/", " ").replaceAll("[():';«»]"," "); //-remove parentheses
	   	text = text.replaceAll(",", " "); //-remove comma
	   	text = text.replaceAll("\\s+", " ").trim().toLowerCase(); //-normalize spaces
	   	//-More text processing here like stemming
		text = stemFrenchInput(text);
		return text;
	}
	
	private String replaceWords(String text){
		
		text = text.replaceAll("co-ordinate", "coordinate");
		
		
		return text.trim();
	}
	
	private String removeWords(List<String> swDict, String text){
		for(String stopWord: swDict){
			text = text.replaceAll(" "+stopWord+" ", " ").replaceAll("\\s+", " ").trim();
		}//-end for
		return text;
	}
	
	
	private String stemInput(String strText){
		
		String stemResults="";
		strText = strText.toLowerCase();
		String strToken = "\\s+";
		strText = strText.replaceAll(strToken, " "); //replace extra spaces	
		String[] words = strText.split(strToken);
			
		PorterStemmer stem = new PorterStemmer();
		String sP="";
		for (String word : words){
		
		   stem.setCurrent(word);
	       stem.stem();
	       String stemWord= stem.getCurrent();
	       
	       if(word.length()>1){
				 if(stemResults.length()>0)sP=" ";
				 stemResults = stemResults+sP+stemWord.trim();
		   }//end if
		
		}//end for
	       return stemResults;
	       
	}
	
	
	public String stemFrenchInput(String strText){
		
		String stemResults="";
		strText = strText.toLowerCase();
		String strToken = "\\s+";
		strText = strText.replaceAll(strToken, " "); //replace extra spaces	
		String[] words = strText.split(strToken);
			
		FrenchStemmer stem = new FrenchStemmer();
		String sP="";
		for (String word : words){
		
		   stem.setCurrent(word);
	       stem.stem();
	       String stemWord= stem.getCurrent();
	       
	       if(word.length()>1){
				 if(stemResults.length()>0)sP=" ";
				 stemResults = stemResults+sP+stemWord.trim();
		   }//end if
		
		}//end for
	       return stemResults;
	       
	}
	
	

}
