package com.hansol.timereport.domain.deadline.repository;

import java.util.List;

import com.hansol.timereport.domain.deadline.entity.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadlineRepository extends JpaRepository<Deadline, String> {
	
	List<Deadline> findByDeadlineDayBetween(String firstDay, String lastDay);
	
	Deadline findByDeadlineDay(String eachDay);
	
}