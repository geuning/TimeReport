package com.hansol.timereport.task;

import java.util.List;

import com.hansol.timereport.task.dto.TaskDto;
import com.hansol.timereport.task.entity.Task;

public interface TaskService {
	List<Task> getTasks();
	
	TaskDto getDefaultTaskDto();
}
