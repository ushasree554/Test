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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class FoodCategory {

	public static final String api = "http://localhost:6060/escalex-microbiology/food/indexData";

	public static void main(String[] args) {

		PrintStream printStream = null;
		try {
			String inputFile = "/home/rajendraprasad.yk/project/ifst/food_category.xlsx";
			FoodCategory parser = new FoodCategory();
			parser.readFromXlSheet(inputFile,printStream);
			System.err.println();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void readFromXlSheet(String file,PrintStream printStream) {

		try {
			Map<String, List<String>> map = new HashMap<>();

			FileInputStream excelFile = new FileInputStream(new File(file));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				if(currentRow.getRowNum()!=0) {
					Iterator<Cell> cellIterator = currentRow.iterator();
					String foodProduct = "";
					String foodProductExample= "";
					int subSectionId = 0;

					while (cellIterator.hasNext()) {
						
						Cell cell = cellIterator.next(); 	
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING: 

							//	System.out.println(cell.getColumnIndex());
							if(cell.getColumnIndex() ==0) {
							//	System.out.println(cell.getStringCellValue());
								foodProduct = cell.getStringCellValue().trim();
							}	
							if(cell.getColumnIndex() ==1) {
								foodProductExample = cell.getStringCellValue().trim();
								//System.out.println(cell.getStringCellValue());
							}
						



							break;
						case Cell.CELL_TYPE_NUMERIC: 
							//System.out.println(cell.getColumnIndex());
							if(cell.getColumnIndex() ==2) {
							//	System.out.println(cell.getNumericCellValue());
								subSectionId = (int) cell.getNumericCellValue();
							}
							break;

						default :
							break;
						}
					}
					if(map.containsKey(foodProduct+"_"+subSectionId)) {
						
						List<String> fcExmlList = map.get(foodProduct+"_"+subSectionId);
						fcExmlList.add(foodProductExample);
						map.put(foodProduct+"_"+subSectionId, fcExmlList);
					}else {
						
						List<String> fcExmlList = new ArrayList<String>();
						fcExmlList.add(foodProductExample);
						map.put(foodProduct+"_"+subSectionId, fcExmlList);
					}
					System.out.println(foodProduct+"\t"+foodProductExample+"\t"+subSectionId);
				}
			}
			System.out.println(map);
			
			int id=0;
			Set<String> key_set =  map.keySet();
			for(String key : key_set) {
				System.out.println("=======>"+map.get(key));
				id++;
				JSONObject object = new JSONObject();
				object.put("id", "record_"+id);
				object.put("food_product", key.split("_")[0]);
				object.put("food_product_examples", map.get(key));
				object.put("sub_section_id", key.split("_")[1]);
				System.out.println(object);
				
				
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
