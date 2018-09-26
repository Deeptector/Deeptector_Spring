package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

//Database 관리를 위한 class
@Repository
public class DAO implements ListDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//DataBase에 insert함
	@Override
	public int createList(ListClass list) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection connection) -> {
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("INSERT INTO list(date) VALUES(?)",Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, list.getDate());
			return preparedStatement;
		}, keyHolder);
		
		return 1;
	}

	//DataBase에 접근하여 data 가져옴
	@Override
	public List<ListClass> getList() {
		
		List<ListClass> lists = new ArrayList<ListClass>();
		
		Collection<Map<String, Object>> rows = null;
		rows = jdbcTemplate.queryForList("SELECT * FROM list order by date desc");
		
		rows.stream().map((row) -> {
			ListClass list = new ListClass();
		list.setDate((String)row.get("date"));
		return list;
		}).forEach((ss) -> {
			lists.add(ss);
		});
		
		return lists;
	}

}
