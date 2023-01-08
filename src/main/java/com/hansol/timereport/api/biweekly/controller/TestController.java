package com.hansol.timereport.api.biweekly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hansol.timereport.domain.biweekly.service.BiWeeklyService;
import com.hansol.timereport.global.exception.response.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

	private final BiWeeklyService biWeeklyService;

	@GetMapping("/save-test")
	public BaseResponse<Boolean> saveTwoWeeks() {
		return new BaseResponse<>(biWeeklyService.saveTwoWeeks());
	}
}
