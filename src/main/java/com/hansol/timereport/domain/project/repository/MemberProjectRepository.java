package com.hansol.timereport.domain.project.repository;

import java.util.List;
import java.util.Optional;

import com.hansol.timereport.domain.project.entity.MemberProject;
import com.hansol.timereport.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hansol.timereport.domain.member.entity.Member;

public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
	
	List<MemberProject> findByMember(Member member);
	
	List<MemberProject> findByProject(Project project);
	
	Optional<MemberProject> findByMemberAndProject(Member member, Project project);

	Optional<MemberProject> findByMemberMemberIdAndProjectProjectId(Long memberId, Long projectId);
}
