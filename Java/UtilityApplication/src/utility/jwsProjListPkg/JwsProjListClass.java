package utility.jwsProjListPkg;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JwsProjListClass {

	public List<String> JwsProjListMethod(String parDirPath) {
		
		File directory = new File(parDirPath);
		
	    FileFilter directoryFileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isDirectory();
	        }
	    };
			
	    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
	    List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
	    
	    for (File directoryAsFile : directoryListAsFile) {
	        foldersInDirectory.add(directoryAsFile.getName());
	    }

	    return foldersInDirectory;
	}
	
	
	//creating a generic function that converts the Array into List  
	public static <T> List<T> ArrayToListConversion(T arr[])   
	{   
		//invoking the asList() method and passing the array to be converted  
		List<T> list = Arrays.asList(arr);   
		//returns the list  
		return list;   
	}   
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("---------Jws Entry---------\n");
		
		String parDir = "C:\\DATA\\Workspace\\OSB12c\\NGSSP_OSB_Appl\\";
		
		String dirArr[] = {".data"};
		List<String> invalidDirList = ArrayToListConversion(dirArr);   
		
		JwsProjListClass jplo = new JwsProjListClass();
		
		List<String> dirList = jplo.JwsProjListMethod(parDir);
		
		for (int i = 0; i < dirList.size(); i++) {
			 
            if(!invalidDirList.contains(dirList.get(i).toString())) {
            	System.out.println("<hash><url n=\"URL\" path=\"" + dirList.get(i) + "/" + dirList.get(i) + ".jpr\"/></hash>");
            }
        }
	}
}
