package com.realtime.Assignment1;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
	
	private static List<Data> data = new ArrayList();
	
	
	public static void getWikiData() {
		
		try {
			
			String url = "https://ms.wikipedia.org/wiki/Malaysia";
			Document doc = Jsoup.connect(url).get();
			Element table = doc.select("h2:contains(Trivia) + [class=\"wikitable\"]").first();
			Elements rows = table.select("tr");
			
			for (Element trs : rows) {               

                    Elements info1 = trs.select("th");
                    Elements info2 = trs.select("td");
                    String table1 = info1.text();
                    String table2 = info2.text();

                    data.add(new Data(table1, table2));
                    
                    System.out.println(table1  + " : " + table2);
                }


        } catch (IOException e){
            System.out.println("Disconnected!! Please try again!!");
        }
	}
	
	public static void writeToExcel() {

        try {

        	XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Trivia Excel");
            
            for (int a = 0; a < data.size(); a++) {
            	
                XSSFRow table = sheet.createRow(a);

                XSSFCell ce11 = table.createCell(0);
                ce11.setCellValue(data.get(a).getInfo1());

                XSSFCell ce112 = table.createCell(1);
                ce112.setCellValue(data.get(a).getInfo2());
            }
            
            FileOutputStream fileOutput = new FileOutputStream("C:\\Users\\setul\\Desktop\\Trivia.xlsx");
            workbook.write(fileOutput);
            workbook.close();
            
            System.out.println("\nExcel file created...");
            
        } 
        
        catch (IOException e) {
            System.out.println("Unsuccessful! Please try again!!");
        }
    }

	public static void main(String[] args) throws IOException{
		
		getWikiData();
		writeToExcel();
	}
}
