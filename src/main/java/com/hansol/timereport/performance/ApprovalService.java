package com.hansol.timereport.performance;

import java.util.List;
import java.util.Map;

import com.hansol.timereport.performance.dto.ApprovalDto;
import com.hansol.timereport.performance.dto.ApprovalReq;

public interface ApprovalService {

	List<ApprovalDto> findApprovals(String day, Long memberId);

	List<Long> approvalAny(ApprovalReq approvalReq, Long memberId);

	List<Long> cancelApprovalAny(ApprovalReq approvalReq, Long memberId);

	List<Long> resetApprovalAny(ApprovalReq approvalReq, Long memberId);
		
}
