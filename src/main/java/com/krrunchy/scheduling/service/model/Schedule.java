package com.krrunchy.scheduling.service.model;

import java.text.MessageFormat;
import java.util.List;

public class Schedule {
	private String date;
	private List<Long> items;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public List<Long> getMenuIds() {
		return items;
	}
	public void setItems(List<Long> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return MessageFormat.format("{0},{1}", date , items);
	}
}
