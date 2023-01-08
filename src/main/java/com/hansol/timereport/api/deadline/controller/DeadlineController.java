package com.hansol.timereport.api.deadline.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hansol.timereport.domain.deadline.service.DeadlineService;
import com.hansol.timereport.api.deadline.dto.DeadlineDto;
import com.hansol.timereport.api.deadline.dto.DeadlineRegisterDto;
import com.hansol.timereport.api.deadline.dto.DeadlineReq;
import com.hansol.timereport.global.auth.Authority;
import com.hansol.timereport.global.constant.Grade;
import com.hansol.timereport.global.exception.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/deadline")
public class DeadlineController {

	private final DeadlineService deadlineService;

	public DeadlineController(DeadlineService deadlineService) {
		this.deadlineService = deadlineService;
	}

	// 마감 월조회
	@GetMapping
	@Authority(target = Grade.ADMIN)
	public BaseResponse<List<DeadlineDto>> findDeadline(@RequestParam(defaultValue = "") String year,
			@RequestParam(defaultValue = "") String month) {
		return new BaseResponse<>(deadlineService.findDeadline(year, month));
	}

	// 월 마감처리
	@PatchMapping
	@Authority(target = Grade.ADMIN)
	public BaseResponse<DeadlineRegisterDto> registerDeadline(@RequestBody DeadlineReq deadlineReq) {
		return new BaseResponse<>(deadlineService.registerDeadline(deadlineReq));
	}

}
