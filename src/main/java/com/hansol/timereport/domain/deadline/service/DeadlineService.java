package com.hansol.timereport.domain.deadline.service;

import java.util.List;

import com.hansol.timereport.api.deadline.dto.DeadlineDto;
import com.hansol.timereport.api.deadline.dto.DeadlineRegisterDto;
import com.hansol.timereport.api.deadline.dto.DeadlineReq;
import com.hansol.timereport.domain.deadline.entity.Deadline;

public interface DeadlineService {
	
	List<DeadlineDto> findDeadline(String year, String month);

	DeadlineRegisterDto registerDeadline(DeadlineReq deadlineReq);
	
	Deadline findByDeadlineDay(String eachDay);
	
	List<Deadline> findByDeadlineDayBetween(String firstDay, String lastDay);
	
}
