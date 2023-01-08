package com.hansol.timereport.domain.approval.service;

import java.util.List;

import com.hansol.timereport.api.approval.dto.ApprovalDto;
import com.hansol.timereport.api.approval.dto.ApprovalReq;

public interface ApprovalService {

	List<ApprovalDto> findApprovals(String day, Long memberId);

	List<Long> approvalAny(ApprovalReq approvalReq, Long memberId);

	List<Long> cancelApprovalAny(ApprovalReq approvalReq, Long memberId);

	List<Long> resetApprovalAny(ApprovalReq approvalReq, Long memberId);
		
}
