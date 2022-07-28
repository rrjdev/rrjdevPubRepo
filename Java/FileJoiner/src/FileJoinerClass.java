import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileJoinerClass {
	
	public void joinMethod(String dirName,String delimeter) throws IOException {
		
		File oldFile = new File(dirName.concat("\\MergedFile.txt")); 
		
	    if (oldFile.delete()) 
	    { 
	      System.out.println("Deleted the file: " + oldFile.getName());
	    } 
	    else 
	    {
	      System.out.println("Info: Failed to delete the file.");
	    } 
	    
	    System.out.println();
	    
	    File f = new File(dirName);
		String[] fileNames;
		
		String line = "";
		String seq;
		
		int index = 1;
		
		fileNames = f.list();
		int fileCnt = fileNames.length;
		
		List<String> seqList = new ArrayList<String>();
		
		for (String fname : fileNames) 
        {
			 seqList.add(fname);	 
			
        }
		
		Collections.sort(seqList);
		
		PrintWriter pw;
		pw = new PrintWriter(dirName.concat("\\MergedFile.txt"));
		
		BufferedReader br = null;
		
		String fileName= "";
		
		boolean flag = false;
		
		for (String seqNum : seqList) 
		{
			fileName = seqNum;
			
			System.out.println("Merging contents for file : " + fileName); 
			
            br = new BufferedReader(new FileReader(dirName.concat("\\" + fileName)));
        	
        	line = br.readLine();
        	
        	if(line.indexOf(delimeter)!=-1) 
	         {
	            flag = true;
	         }
        	
        	if(flag)
        	{
        		break;
        	}
            
            while (line != null && index != fileCnt)
            {
            	pw.print(line.concat(delimeter));
                line = br.readLine();
            }
            
            if (line != null && index == fileCnt)
            {
            	
            	pw.print(line);
            }
            
            index ++;	
		}
		
		pw.flush();
        
        br.close();
        pw.close();
        
        System.out.println(".........................................................................");
        
        if(flag)
		{
			oldFile.delete();
			System.out.println("Rerun the java program with some other delimeter as file: " + fileName + " contains delimeter: " + delimeter);
		}
		
		else
		{
			System.out.println("Merged all : " + fileCnt + " files into : " + dirName + "\\MergedFile.txt");
		}
		
	}
	
	
	public static void main(String args[]) throws IOException
	{
		FileJoinerClass fjo = new FileJoinerClass();
		String dir = "D:\\TestDir\\IIBDir\\FileJoiner";
		String delimeter = "###";
		fjo.joinMethod(dir,delimeter);
	}

}
