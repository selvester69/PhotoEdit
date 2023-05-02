package com.photos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {
	
	public static String FILE_PATH = "C:\\Users\\abhij\\Desktop";
	public static String FILE_NAME = "Shortlisted_Tanu.xlsx";
	
	
	@SuppressWarnings("resource")
	public Map<String,List<String>> readFile() {
		FileInputStream file =null;
		Map<String,List<String>> map =null;
		try {
			file = new FileInputStream(new File(FILE_PATH+"\\"+FILE_NAME));
		
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(3); //sheet number -1 from excel
		Iterator<Row> rowIterator = sheet.iterator();
		map =new LinkedHashMap<String, List<String>>();
		while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int first = 0;
            String key = "";
            List<String> values = new ArrayList<String>();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
            	Cell cell = cellIterator.next();
            	if(first==0) {
            		key = cell.getStringCellValue();
            	}else {
            		if(cell.getStringCellValue()!=null&& !"".equalsIgnoreCase(cell.getStringCellValue())) {
            			values.add(cell.getStringCellValue()+".JPG");
            		}
            	}
            	first++;
            }
            map.put(key, values);
        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main(String[] args) {
		ExcelFileReader reader = new ExcelFileReader();
		Map<String,List<String>> map = reader.readFile();
		
		if(map!=null) {
			System.out.println("fileread");
			map.entrySet().forEach(value->{
				System.out.print(value.getKey());
				value.getValue().forEach(val-> { System.out.print(val+"\t");});
				System.out.println(value.getValue().size());
			});
		}
	}
	
	
	

}
