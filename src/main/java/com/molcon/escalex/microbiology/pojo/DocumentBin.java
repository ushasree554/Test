package com.molcon.escalex.microbiology.pojo;

import java.util.List;

public class DocumentBin {
	
	private long totalItems;
	private List<List<Member>> members;
	public long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
	public List<List<Member>> getMembers() {
		return members;
	}
	public void setMembers(List<List<Member>> members) {
		this.members = members;
	}
	
	

}
