package com.hansol.timereport.api.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hansol.timereport.global.auth.Authority;
import com.hansol.timereport.global.constant.Grade;
import com.hansol.timereport.global.response.BaseResponse;
import com.hansol.timereport.domain.member.service.MemberService;
import com.hansol.timereport.api.member.dto.MemberDto;
import com.hansol.timereport.api.member.dto.MemberLoginDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@Authority(target = Grade.ADMIN)
	@GetMapping
	public BaseResponse<List<MemberDto>> findMembers() {
		return new BaseResponse<>(memberService.findMembers());
	}

	@PostMapping("/login")
	public BaseResponse<MemberDto> memberLogin(@Valid @RequestBody MemberLoginDto memberLoginDto,
		HttpServletRequest request) {
		return new BaseResponse<>(memberService.login(memberLoginDto, request));
	}

	@PostMapping("/logout")
	public BaseResponse<String> memberLogout(HttpServletRequest request) {

		return new BaseResponse<>(memberService.logout(request));
	}
}
