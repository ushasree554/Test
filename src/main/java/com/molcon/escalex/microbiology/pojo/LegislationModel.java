package com.molcon.escalex.microbiology.pojo;


public class LegislationModel {

	private String id;
	private int sub_section_id;
	private String legislation;
	
	private String food_category;
	private String microorganism;
	private String level;
	private String status;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLegislation() {
		return legislation;
	}
	public void setLegislation(String legislation) {
		this.legislation = legislation;
	}
	public String getMicroorganism() {
		return microorganism;
	}
	public void setMicroorganism(String microorganism) {
		this.microorganism = microorganism;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSub_section_id() {
		return sub_section_id;
	}
	public void setSub_section_id(int sub_section_id) {
		this.sub_section_id = sub_section_id;
	}
	
	
	public String getFood_category() {
		return food_category;
	}
	public void setFood_category(String food_category) {
		this.food_category = food_category;
	}
	
}
