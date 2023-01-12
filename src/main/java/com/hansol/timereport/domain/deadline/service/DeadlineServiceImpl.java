package com.hansol.timereport.domain.deadline.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hansol.timereport.domain.biweekly.service.BiWeeklyService;
import com.hansol.timereport.domain.deadline.constant.DeadlineStatus;
import com.hansol.timereport.api.deadline.dto.DeadlineDto;
import com.hansol.timereport.api.deadline.dto.DeadlineRegisterDto;
import com.hansol.timereport.api.deadline.dto.DeadlineReq;
import com.hansol.timereport.domain.deadline.entity.Deadline;
import com.hansol.timereport.domain.deadline.repository.DeadlineRepository;
import com.hansol.timereport.global.exception.BaseException;
import com.hansol.timereport.global.response.BaseResponseStatus;
import com.hansol.timereport.domain.performance.service.PerformanceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
public class DeadlineServiceImpl implements DeadlineService {
	
	private final BiWeeklyService biWeeklyService;
	private final DeadlineRepository deadlineRepository;
	private final PerformanceService performanceService;
	
	public DeadlineServiceImpl(
			BiWeeklyService biWeeklyService, 
			DeadlineRepository deadlineRepository, 
			PerformanceService performanceService
			) {
		this.biWeeklyService = biWeeklyService;
		this.deadlineRepository = deadlineRepository;
		this.performanceService = performanceService;
	}
	
	//월 마감 조회
	@Override
	public List<DeadlineDto> findDeadline(String year, String month){
		
		// 1. 오늘 or 선택 월 기준으로 1일과 마지막일 구하기 
		LocalDate today = LocalDate.now();
		if(year != null && month != null && !year.isBlank() && !month.isBlank()) {
			biWeeklyService.isValidYearAndMonth(year, month);
			today = LocalDate.parse(year+month+"01", DateTimeFormatter.ofPattern("yyyyMMdd"));
		}
		
		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());
				
		List<Deadline> deadLineDayList = findByDeadlineDayBetween(
				firstDay.format(DateTimeFormatter.ofPattern("yyyyMMdd")), 
				lastDay.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		List<DeadlineDto> resultList = new ArrayList<>();
		
		for(Deadline eachDay : deadLineDayList) {
			
			String deadlineDay = eachDay.getDeadlineDay();
			
			LocalDate deadlineDate = LocalDate.parse(deadlineDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
	        DayOfWeek dayOfWeek = deadlineDate.getDayOfWeek();
	        int deadlineDayNum = dayOfWeek.getValue();
	        
			DeadlineDto deadlineDto = DeadlineDto.of(eachDay, deadlineDayNum);
			
			resultList.add(deadlineDto);
		}
		
		return resultList;
	}
	
	//월 마감 처리
	@Transactional
	@Override
	public DeadlineRegisterDto registerDeadline(DeadlineReq deadlineReq) {
		List<String> days = deadlineReq.getDays();
		List<String> deadlineDays = new ArrayList<>();
		
		for(String eachDay : days) {

			Boolean isExsists = performanceService.findPerformanceSignStatusExists(eachDay);
			
			if(isExsists) {
				throw new BaseException(BaseResponseStatus.EXIST_NO_APPROVAL_OF_PERF);
			}
			
			Deadline eachDeadline = findByDeadlineDay(eachDay);	

			if(DeadlineStatus.FALSE.getValue().equals(eachDeadline.getIsDeadline())) {
				eachDeadline.updateIsDeadline(DeadlineStatus.TRUE.getValue());
			} else {
				eachDeadline.updateIsDeadline(DeadlineStatus.FALSE.getValue());
			}
			deadlineDays.add(eachDeadline.getDeadlineDay());
			
		}
		
		DeadlineRegisterDto result = DeadlineRegisterDto.builder()
				.days(deadlineDays)
				.build();
		
		return result;
	}
	
	@Override
	public Deadline findByDeadlineDay(String eachDay) {
		return deadlineRepository.findByDeadlineDay(eachDay);
	}
	
	@Override
	public List<Deadline> findByDeadlineDayBetween(String firstDay, String lastDay) {
		return deadlineRepository.findByDeadlineDayBetween(firstDay, lastDay);
	}
	
}
