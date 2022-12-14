package com.hansol.timereport.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hansol.timereport.controller.member.entity.Member;
import com.hansol.timereport.project.entity.MemberProject;
import com.hansol.timereport.project.entity.Project;

public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
	
	List<MemberProject> findByMember(Member member);
	
	List<MemberProject> findByProject(Project project);
	
	Optional<MemberProject> findByMemberAndProject(Member member, Project project);

	Optional<MemberProject> findByMemberMemberIdAndProjectProjectId(Long memberId, Long projectId);
}
