package com.hansol.timereport.api.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hansol.timereport.global.auth.Authority;
import com.hansol.timereport.global.constant.Grade;
import com.hansol.timereport.global.constant.SessionConst;
import com.hansol.timereport.global.response.BaseResponse;
import com.hansol.timereport.domain.project.service.ProjectService;
import com.hansol.timereport.api.project.dto.ProjectDto;
import com.hansol.timereport.api.project.dto.ProjectEditReq;
import com.hansol.timereport.api.project.dto.ProjectRegisterReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	public BaseResponse<List<ProjectDto>> findProjects(
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			) {
		return new BaseResponse<>(projectService.findAll(memberId));
	}
	
	@PostMapping("/{projectId}")
	public BaseResponse<Long> addMyProject(@PathVariable Long projectId,
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			) {
		return new BaseResponse<>(projectService.addMyProject(projectId, memberId));
	}


	@GetMapping("/me")
	public BaseResponse<List<ProjectDto>> findMyProject(
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			) {
		return new BaseResponse<>(projectService.findMyProject(memberId));
	}
	
	
	@PostMapping("delete/{projectId}")
	public BaseResponse<Long> deleteMyProject(@PathVariable Long projectId,
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			) {
		return new BaseResponse<>(projectService.deleteMyProject(projectId, memberId));
	}
	
	// 관리자 프로젝트 등록
	@PostMapping("/admin/register")
	@Authority(target = Grade.MANAGER)
	public BaseResponse<Long> addProject(@RequestBody ProjectRegisterReq projectRegisterReq,
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			){
		return new BaseResponse<>(projectService.addProject(projectRegisterReq, memberId));
	}
	
	// 관리자 프로젝트 수정
	@PatchMapping("/admin/edit")
	@Authority(target = Grade.MANAGER)
	public BaseResponse<Long> eidtProject(@RequestBody ProjectEditReq projectEditReq,
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			) {
		return new BaseResponse<>(projectService.editProject(projectEditReq, memberId));
	}
	
	// 관리자 프로젝트 삭제
	@PostMapping("/admin/delete/{projectId}")
	@Authority(target = Grade.MANAGER)
	public BaseResponse<Long> deleteProject(@PathVariable Long projectId,
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long memberId
			) {
		return new BaseResponse<>(projectService.deleteProject(projectId, memberId));
	}

}
