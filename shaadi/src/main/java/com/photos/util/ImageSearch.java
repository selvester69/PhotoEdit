package com.photos.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImageSearch {
	public static final String SOURCE_FOLDER_PATH = "D:\\shaadi_tanu_edit";
	public static final String DEST_FOLDER_PATH = "D:\\shaadi_tanu_shortlisted";
	
	ExcelFileReader reader = new ExcelFileReader();
	
	public List<File> readAllFilesFromExcel() {
		Map<String,List<String>> filesList = reader.readFile();
		List<File> shortlisted = new ArrayList<File>();
		System.out.println(shortlisted.size());
		return shortlisted;
	}
	
	public void copyFile(List<File> files) {
		for(File file : files) {
		    try {
				Files.copy(file.toPath(),
				    (new File(DEST_FOLDER_PATH+"\\" + file.getName())).toPath(),
				    StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<File> readAllFilesFromExcelFinal() {
		Map<String,List<String>> filesList = reader.readFile();
		List<File> shortlisted = new ArrayList<File>();
		
		filesList.entrySet().forEach(entry->{
			String key = entry.getKey();
			if(key.isEmpty()) {
				return;
			}
			List<String> values = entry.getValue();
			System.out.println(values);
			System.out.println(values.size());
			File folder = new File(SOURCE_FOLDER_PATH+"\\"+key);//fetch folder on the basis of key
			List<File> listOfFiles = Arrays.asList(folder.listFiles()); //fetch list of files from that folder
			//for each name from excel check if present in folder
			List<File> filtered =  listOfFiles.stream().filter(val->
			values.stream().
				anyMatch(name -> name.toLowerCase().equalsIgnoreCase(val.getName().toLowerCase()))).collect(Collectors.toList());
			System.out.println("filtered"+filtered);
			System.out.println("filtered. size"+filtered.size());
			System.out.println("-------------");
			this.copyFile(filtered);
			
		});
		
		return shortlisted;
	}
	
	
	public static void main(String[] args) throws IOException {
		ImageSearch search = new ImageSearch();
		List<File> files = search.readAllFilesFromExcelFinal();
		System.out.println(files.size());
	}

}
