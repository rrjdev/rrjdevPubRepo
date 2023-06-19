package poi_excels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Excel2CSV {

    public static void printAsCSV(Workbook wbk,Sheet sheet, String targetFile) {
        
        Row row = null;
        
        String rowContent;
        String cellContent;
        
        //FormulaEvaluator evaluator = wbk.getCreationHelper().createFormulaEvaluator();
        
        try
        {
            System.out.println(targetFile);
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
            
            for (int i = 0; i <= sheet.getLastRowNum(); i++) 
            {
                rowContent = "";
                
                
                row = sheet.getRow(i);
                
                for (int j = 0; j < row.getLastCellNum(); j++) 
                {
                   cellContent = "";
                    
                   row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                   
                   cellContent =  row.getCell(j).toString();
                    
                   if(j < row.getLastCellNum() - 1) 
                   {
                       cellContent = cellContent + ",";
                   }
                    
                    rowContent = rowContent + cellContent;
                    cellContent  = "";
                }
                
                writer.write(rowContent);
                writer.newLine();
                
            }
            
            writer.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        InputStream inp = null;
        String targetFileName = null;
        
        try {
            inp = new FileInputStream(new File("D:\\TestExcel_2003.xls"));
                
            Workbook wb = WorkbookFactory.create(inp);

            for(int i=0;i<wb.getNumberOfSheets();i++) {
                targetFileName = "D:\\TestCSV_" + wb.getSheetAt(i).getSheetName() + ".csv";
                System.out.println();
                printAsCSV(wb,wb.getSheetAt(i),targetFileName);
            }
        } 
        
        catch (InvalidFormatException ex) {
            Logger.getLogger(Excel2CSV.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Excel2CSV.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(Excel2CSV.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(Excel2CSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}