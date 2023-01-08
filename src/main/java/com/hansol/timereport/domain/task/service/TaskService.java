package com.hansol.timereport.domain.task.service;

import java.util.List;

import com.hansol.timereport.api.task.dto.TaskDto;
import com.hansol.timereport.domain.task.entity.Task;

public interface TaskService {
	List<Task> getTasks();
	
	TaskDto getDefaultTaskDto();
}
