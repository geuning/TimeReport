package com.hansol.timereport.domain.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hansol.timereport.api.member.dto.MemberDto;
import com.hansol.timereport.api.member.dto.MemberLoginDto;
import com.hansol.timereport.domain.member.entity.Member;

public interface MemberService {

	List<MemberDto> findMembers();

	MemberDto login(MemberLoginDto memberLoginDto, HttpServletRequest request);

	String logout(HttpServletRequest request);

	MemberDto findMemberDto(Long memberId);
	
	Member findMember(Long memberId);
	
	List<Member> findAllByDeptCode(String deptCode);
}
