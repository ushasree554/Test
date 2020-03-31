package com.molcon.escalex.microbiology.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ExcelParser {

	public static final String api = "http://localhost:6060/escalex-microbiology/elastic/indexData";

	public static void main(String[] args) {

		PrintStream printStream = null;
		try {
			printStream = new PrintStream("/home/rajendraprasad.yk/Desktop/HtmlFileMissing.txt");
			//String inputFile = "/home/rajendraprasad.yk/Desktop/Filename-section-mapping.xlsx";
			String inputFile = "/home/rajendraprasad.yk/Documents/Test4.xlsx";
			ExcelParser excelParser = new ExcelParser();
			excelParser.readFromXlSheet(inputFile,printStream);
			System.err.println();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void readFromXlSheet(String file,PrintStream printStream) {

		try {
			Map<String, JSONObject> subSectionMap = new LinkedHashMap<>();
			JSONArray jsonArray  = new JSONArray();

			FileInputStream excelFile = new FileInputStream(new File(file));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				JSONObject object = new JSONObject();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next(); 	
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING: 

					//	System.out.println(cell.getColumnIndex());
						if(cell.getColumnIndex() ==0) {
							List<String> fileInfo = getTextForSubSection(cell.getStringCellValue(),printStream);
							if(fileInfo!=null && fileInfo.size()==2) {
								object.put("sub_section_text",fileInfo.get(0));
								object.put("sub_section_display",fileInfo.get(1));
							}else {
								System.err.println("error"+cell.getStringCellValue());
							}
						}						
						if(cell.getColumnIndex() ==3) {
							object.put("section_title", cell.getStringCellValue());
						}
						
						if(cell.getColumnIndex() ==4) {
							System.out.println(cell.getStringCellValue());			
							
							
							object.put("sub_section_display_id", cell.getStringCellValue());
						}
						if(cell.getColumnIndex() ==5) {
							subSectionMap.put(cell.getStringCellValue(), object);
							object.put("sub_section_title", cell.getStringCellValue());
							jsonArray.add(object);
						}
						break;
					case Cell.CELL_TYPE_NUMERIC: 
						//System.out.println(cell.getColumnIndex());
						if(cell.getColumnIndex() ==1) {
							object.put("sub_section_id", cell.getNumericCellValue());
						}
						if(cell.getColumnIndex() ==2) {
							object.put("section_display_id", cell.getNumericCellValue());
						}
						if(cell.getColumnIndex() ==4) {
							System.out.println(cell.getNumericCellValue());
							
							object.put("sub_section_display_id", cell.getNumericCellValue());
						}
						break;
						
					case Cell.CELL_TYPE_FORMULA:
						if(cell.getColumnIndex() ==4) {
							System.out.println(cell.getNumericCellValue());
							object.put("sub_section_display_id", cell.getNumericCellValue());
						}
						break;
				
					default :
						break;
					}

				}
				
			}
		//System.out.println(jsonArray.toString());
			for(Object object : jsonArray) {
				JSONObject js = (JSONObject) object;
				System.out.println("=======>"+js.get("sub_section_display_id"));
				postCall(api,object.toString());
				//String output = postCall(api,object.toString());
				//System.out.println(object.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<String> getTextForSubSection(String fileName,PrintStream printStream) {	

		List<String> list = new ArrayList<>();		
		Document htmlFile = null; 	
		try {			
			htmlFile = Jsoup.parse(new File("/home/rajendraprasad.yk/Desktop/ifst/2k191119/MicroBook/"+fileName), "ISO-8859-1"); 
			String text = htmlFile.body().text();
			String html = htmlFile.body().html();
			
			if(html.contains("src=\"img")) {
				
				html = html.replaceAll("src=\"img", "src=\"../../../assets/images");
			}
			
			list.add(text);
			list.add(html);
		} catch (IOException e) {
			printStream.println(e.getMessage());
		} 		
		return list;
	}

	public String postCall(String api,String input) {

		String outputRes = null;
		try {

			URL url = new URL(api);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(1000);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				outputRes = output;
			}
			conn.disconnect();
		}
		catch (Exception e) {			
			e.printStackTrace();
		}
		return outputRes;
	}



}
