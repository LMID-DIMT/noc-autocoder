package ca.servo.ai.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	
	//-Data Tools
			public List<String> arrayFileData(String filePath)throws IOException {
				  //--Read file data and store in Array	
				  List<String> dataArray = new ArrayList<String>();
				  BufferedReader br = new BufferedReader(new FileReader(filePath));
				  String line;
				  while ((line = br.readLine()) != null) {
					  if(line.length()>1)dataArray.add(line);
				  }
				  br.close();
					return dataArray;	
			}
			
			public void writeArrayData(String filePath, List<String> dataArray)throws IOException {
				Writer fbw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(filePath), "UTF-8"));
				
				  for (String arrayItem:dataArray) {
					fbw.write(arrayItem+"\r\n");
				  }//end for
				fbw.close();
			}
			
			public List<String> file2Array(String fileName) throws IOException{
				
				List<String> list = new ArrayList<String>();
				BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
					//br returns as stream and convert it into a List
					list = br.lines().collect(Collectors.toList());
					br.close();
				return list;
			}
	

}
