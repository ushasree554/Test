package com.molcon.escalex.microbiology.pojo;

public class SearchBin {

	private int start;
	private int offset;
	private String term;
	
	private String foodCategory;
	private String microorganism;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public String getMicroorganism() {
		return microorganism;
	}

	public void setMicroorganism(String microorganism) {
		this.microorganism = microorganism;
	}

	
	
	
	
}
