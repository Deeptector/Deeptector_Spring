package com.example.demo;

import java.util.List;

//DAO를 위한 interface
public interface ListDAO {
	public abstract int createList(ListClass list);
	
	public abstract List<ListClass> getList();
}
