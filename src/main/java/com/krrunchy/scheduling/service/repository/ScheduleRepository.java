package com.krrunchy.scheduling.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.krrunchy.scheduling.service.model.DateUtil;
import com.krrunchy.scheduling.service.model.Schedule;


@Repository
public class ScheduleRepository {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ScheduleRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Schedule> findAll() {
		String query = "select  delivery_date from menu_schedule";
		List<Date> dates = jdbcTemplate.query(query,new DateMapper());
		
		//TODO: write stream
		//TODO: move this to stored procedures in the DB
		List<Schedule> menuSchedules = new LinkedList<Schedule>();
		for (Date date2 : dates) {
			menuSchedules.add(findForDate(date2));
		}
		
		return menuSchedules ;
	}
	
	public Schedule findForDate(Date date) {
		Date sqlDate = new Date(date.getTime());
		String query = "select menu_item_name from menu_item where menu_item_id in (\r\n" + 
				"	select menu_item_id from menu_details where menu_id in(\r\n" + 
				"		select menu_id from menu_schedule where delivery_date=?\r\n" + 
				"	)\r\n" + 
				")";
		List<String> items = jdbcTemplate.query(query,new Object[]{sqlDate},new MenuRowMapper());
		Schedule schedule = new Schedule();
		schedule.setDate(DateUtil.getStringFrom(date));
		
		return schedule ;
	}
	
	class DateMapper implements RowMapper<Date>{
		@Override
		public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dateString = rs.getString("delivery_date");
			//TODO: handle empty optional
			Date date = DateUtil.parseDate(dateString).get();
			return date;
		}
	}
	
	class MenuRowMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("menu_item_name");
		}
	}
}
