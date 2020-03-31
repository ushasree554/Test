package com.molcon.escalex.microbiology.util;

import java.util.ArrayList;
import java.util.List;

public class FoodCategoryUtil {

	
	static  List<String> foodCategoryList = new ArrayList<String>();
	static {
		foodCategoryList.add("Raw Poultry");
		foodCategoryList.add("Raw Meat");
		foodCategoryList.add("Raw Fish & Shellfish");
		foodCategoryList.add("Doughs, Pasta & Batters");
		foodCategoryList.add("Fruit And Fruit Juices");
		foodCategoryList.add("Raw And Prepared Vegetables (Including Salad Vegetables)");
		foodCategoryList.add("Pasteurised Milk & Milk Products");
		foodCategoryList.add("Non Pasteurised Milk & Milk Products");
		foodCategoryList.add("Uht Milk, Cream & Dairy Products");
		foodCategoryList.add("Part Cooked Foods");
		foodCategoryList.add("Chilled & Frozen Processed Foods");
		foodCategoryList.add("Bakery Products");
		foodCategoryList.add("Dried Foods, To Be Cooked");
		foodCategoryList.add("Dried Foods, Ready-To-Eat");
		foodCategoryList.add("Dried Heat Processed Foods, Ready-To-Eat After Rehydration");
		foodCategoryList.add("Dried Baby Foods");
		foodCategoryList.add("Canned, Pouched Or Bottled Foods (>F03 Process)");
		foodCategoryList.add("Preserved Foods - Heat Treated (<F03 Process), Intermediate Moisture Food (Imf) Or Low Ph");
		foodCategoryList.add("Non-Dairy Fats, Oils & Spreads");
		foodCategoryList.add("Soft Drinks And Alcoholic Beverages");
	}
	
	public static List<String> getList() {		
		
		return foodCategoryList;
	}
}
