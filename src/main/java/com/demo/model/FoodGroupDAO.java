package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("foodGroupDAO")
public class FoodGroupDAO {
	private NamedParameterJdbcTemplate myJdbcTemplate;
	
	public List<FoodGroup> getFoodGroups(){
		
		MapSqlParameterSource myMap = new MapSqlParameterSource();
		myMap.addValue("groupName", "fruits");
		
		return myJdbcTemplate.query("select * from foodgroups where name=:groupName",myMap ,new RowMapper<FoodGroup>() {

			public FoodGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
				FoodGroup fg = new FoodGroup();
				fg.setId(rs.getInt("id"));
				fg.setName(rs.getString("name"));
				fg.setDescription(rs.getString("description"));
				
				return fg;
			}
			
		});
	}
	
	public FoodGroup getFoodGroup(int id) {
		MapSqlParameterSource myMap = new MapSqlParameterSource();
		myMap.addValue("id", id);
		
		return myJdbcTemplate.queryForObject("select * from foodgroups where id=:id", myMap, new RowMapper<FoodGroup>() {
			public FoodGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
				FoodGroup fg = new FoodGroup();
				fg.setId(rs.getInt("id"));
				fg.setName(rs.getString("name"));
				fg.setDescription(rs.getString("description"));
				
				return fg;
			}
		});
	}
	
	public Boolean addFoodGroup(String name, String description) {
		Boolean res = false;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", name);
		params.addValue("description", description);
		
		int numOfRowsAffected = myJdbcTemplate.update("insert into foodgroups (name,description) values (:name,:description)",params);
		
		if (numOfRowsAffected == 1) {
			System.out.println("One row added!!");
			res = true;
		} else {
			System.out.println("Problem during adding!!");
		}
		
		return res;
	}
	
	public Boolean addFoodGroup(FoodGroup fg) {
		Boolean res = false;
		
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(fg);
		
		int numOfRowsAffected = myJdbcTemplate.update("insert into foodgroups (name,description) values (:name,:description)",params);
		
		if (numOfRowsAffected == 1) {
			System.out.println("One row added!!");
			res = true;
		} else {
			System.out.println("Problem during adding!!");
		}
		
		return res;
	}
	
	public Boolean updFoodGroup(FoodGroup fg) {
		Boolean res = false;
		
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(fg);
		
		int numOfRowsAffected = myJdbcTemplate.update("update foodgroups set name = :name, description = :description where id = 1",params);
		
		if (numOfRowsAffected == 1) {
			System.out.println("Row updated");
			res = true;
		} else {
			System.out.println("Problem during adding!!");
		}
		
		return res;
	}
	
	public Boolean delFoodGroup(int id) {
		Boolean res = false;
		
		MapSqlParameterSource myMap = new MapSqlParameterSource();
		myMap.addValue("id", id);
		
		int numOfRowsAffected = myJdbcTemplate.update("delete from foodgroups where id=:id", myMap);
		
		if (numOfRowsAffected == 1) {
			System.out.println("One row deleted!!");
			res = true;
		} else {
			System.out.println("Problem during deleting!!");
		}
		return res;
	}
	
	@Transactional("myTransactionManager")
	public int[] createFoodGroups(List<FoodGroup> groups) {
//		ArrayList<MapSqlParameterSource> paramsArrayList = new ArrayList<MapSqlParameterSource>();
//		
//		for(FoodGroup group: groups) {
//			MapSqlParameterSource tempParam = new MapSqlParameterSource();
//			
//			tempParam.addValue("name", group.getName());
//			tempParam.addValue("description", group.getDescription());
//			
//			paramsArrayList.add(tempParam);
//		}
//		SqlParameterSource[] batchParams = new MapSqlParameterSource[paramsArrayList.size()];
		
		SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(groups.toArray());
		
		int[] numOfRowsAffectedArray = myJdbcTemplate.batchUpdate("insert into foodgroups (name,description) values (:name,:description)", batchParams);

		return numOfRowsAffectedArray;
	}
	
	public NamedParameterJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	
	@Autowired
	public void setMyJdbcTemplate(DataSource ds) {
		this.myJdbcTemplate = new NamedParameterJdbcTemplate(ds);
	}
}
