package com.molcon.escalex.microbiology.pojo;

import java.util.List;

public class RequestQuery {

	private int totalItems;
	private List<SubObject> query;
	private List<SubObject> filter;
	private int offset;
	private SubObject sort;
	
	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<SubObject> getQuery() {
		return query;
	}

	public void setQuery(List<SubObject> query) {
		this.query = query;
	}

	public List<SubObject> getFilter() {
		return filter;
	}

	public void setFilter(List<SubObject> filter) {
		this.filter = filter;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public SubObject getSort() {
		return sort;
	}

	public void setSort(SubObject sort) {
		this.sort = sort;
	}

}
