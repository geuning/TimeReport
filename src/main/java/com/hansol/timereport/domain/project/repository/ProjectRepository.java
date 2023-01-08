package com.hansol.timereport.domain.project.repository;

import com.hansol.timereport.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
