package com.hansol.timereport.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hansol.timereport.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
