package com.hansol.timereport.api.task.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hansol.timereport.global.response.BaseResponse;
import com.hansol.timereport.domain.task.service.TaskService;
import com.hansol.timereport.domain.task.entity.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
	public BaseResponse<List<Task>> readAllTasks() {
		return new BaseResponse<>(taskService.getTasks());
	}
}
