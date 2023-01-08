package com.hansol.timereport.api.plan.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlanDaysReq {

	private List<String> planDays;

	public PlanDaysReq() {
	}


	@Builder
	public PlanDaysReq(List<String> planDays) {
		this.planDays = planDays;
	}
}
