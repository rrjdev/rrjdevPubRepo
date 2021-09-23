package writeExcelPkg;

import java.io.IOException;

import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class WriteExcel 
{
    
    public static final String xlsWriteFilePath = "D:\\Error_Recon Report_2021-05-05-07.17.04.149102.XLS";
    
    public static void main(String[] args) 
    {
        WriteExcel exwObj = new WriteExcel();
        try 
        {
            exwObj.excelWriterMethod(xlsWriteFilePath);
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        } 
        catch (InvalidFormatException e) 
        {
            System.out.println(e.getMessage());
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static void excelWriterMethod(String xlsWriteFilePath) throws IOException,
                                                                    InvalidFormatException,
                                                                    SQLException 
    {
        
    }
}
