package com.krrunchy.scheduling.service.endpoints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;

import com.krrunchy.scheduling.service.model.DateUtil;
import com.krrunchy.scheduling.service.model.Schedule;
import com.krrunchy.scheduling.service.repository.ScheduleRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/schedules")
@CrossOrigin
@Tag(name = "schedules", description = "The Scheduling API")
public class ScheduleController {
	
	@Autowired
	private ScheduleRepository repo;
	
	@Autowired
	public ScheduleController(ScheduleRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping(value="/",produces = "application/json")
	@ResponseBody
	
	public List<Schedule> findAllMenus(){
		List<Schedule> findAll = repo.findAll();
		return findAll;	
	}

	@GetMapping(value="/{date}",produces = "application/json")
	@ResponseBody
	private List<Schedule> getMenusFor(@PathVariable  String date) {
		Optional<Date> dateObj = DateUtil.parseDate(date);
		if(!dateObj.isPresent()) {
			return Collections.emptyList();
		}
		
		//Expected date example -"21/7/2018"
		Schedule menuDate = repo.findForDate(dateObj.get());
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		list.add(menuDate);
		return list;
	}

	@PostMapping(value="/",produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void addSchedule(){
	}
	

	@DeleteMapping(value="/{date}",produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Schedule deleteScheduleByDate(@PathVariable String date){
		//TODO: implement this
		return new Schedule();
	}
}
