package com.example.demo;

//DataBase의 list table을 위한 Model
public class ListClass {
	private String date;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ListClass() {
		
	}
	
	public ListClass(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "list date="+ date;
	}
}
