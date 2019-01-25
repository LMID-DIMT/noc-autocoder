package ca.servo.ai.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {
	
	public List<String> file2Array(String fileName) throws IOException{
		
		List<String> list = new ArrayList<String>();
		BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
			//br returns as stream and convert it into a List
			list = br.lines().collect(Collectors.toList());
			br.close();
		return list;
	}
	
	public void createFolder(String folderPath,String folderName)throws Exception{
		
		String dirPath = folderPath+folderName;
		//System.out.println(dirPath);
        File folder = new File(dirPath);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Directory "+folderName + " is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
		
	}
	
	
	public void writeFile(String folderPath,String fileName,String content ){
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String fn = folderPath+"/"+fileName;
			fw = new FileWriter(fn);
			bw = new BufferedWriter(fw);
			bw.write(content);

			//System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		
	}
	
	
	
	
}
