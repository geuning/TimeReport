package com.hansol.timereport.domain.task.repository;

import java.util.List;

import com.hansol.timereport.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByGroupId(String groupId);
	
}
