package com.hansol.timereport.deadline;

import java.util.List;

import com.hansol.timereport.deadline.dto.DeadlineDto;
import com.hansol.timereport.deadline.dto.DeadlineRegisterDto;
import com.hansol.timereport.deadline.dto.DeadlineReq;
import com.hansol.timereport.deadline.entity.Deadline;

public interface DeadlineService {
	
	List<DeadlineDto> findDeadline(String year, String month);

	DeadlineRegisterDto registerDeadline(DeadlineReq deadlineReq);
	
	Deadline findByDeadlineDay(String eachDay);
	
	List<Deadline> findByDeadlineDayBetween(String firstDay, String lastDay);
	
}
