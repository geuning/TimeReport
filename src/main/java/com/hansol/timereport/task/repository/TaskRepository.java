package com.hansol.timereport.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hansol.timereport.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByGroupId(String groupId);
	
}
