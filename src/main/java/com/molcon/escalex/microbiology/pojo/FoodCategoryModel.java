package com.molcon.escalex.microbiology.pojo;

import java.util.List;

public class FoodCategoryModel {

	private String id;
	private String food_product;
	private List<String> food_product_examples;
	private int sub_section_id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFood_product() {
		return food_product;
	}
	public void setFood_product(String food_product) {
		this.food_product = food_product;
	}
	public List<String> getFood_product_examples() {
		return food_product_examples;
	}
	public void setFood_product_examples(List<String> food_product_examples) {
		this.food_product_examples = food_product_examples;
	}
	public int getSub_section_id() {
		return sub_section_id;
	}
	public void setSub_section_id(int sub_section_id) {
		this.sub_section_id = sub_section_id;
	}
	
	
	
}
