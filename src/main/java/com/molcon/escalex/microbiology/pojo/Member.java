package com.molcon.escalex.microbiology.pojo;

import org.springframework.data.mongodb.core.mapping.Field;

public class Member {

	@Field("id")
	private String field;
	private String name;
	private String value;
	private MetaDataBin metaData;
	
	public MetaDataBin getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaDataBin metaData) {
		this.metaData = metaData;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
