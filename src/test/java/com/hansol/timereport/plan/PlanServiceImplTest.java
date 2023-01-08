package com.hansol.timereport.plan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hansol.timereport.domain.plan.service.PlanService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hansol.timereport.domain.member.service.MemberService;
import com.hansol.timereport.domain.plan.repository.PlanRepository;
import com.hansol.timereport.domain.task.service.TaskService;

@ActiveProfiles("local")
@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class PlanServiceImplTest {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PlanService planService;

	@Autowired
	private TaskService taskService;

	@PersistenceContext
	private EntityManager em;

	// @BeforeAll	// Instance PerClass 어노테이션 선언 시 static으로 하지 않아도 된다.
	// public void setup() {
	//
	// 	// 테스트 전용 계획 데이터 1000개 생성
	// 	for (int i = 0; i < 4000; i++) {
	// 		Plan plan = Plan.builder()
	// 			.seq(1)
	// 			.taskHour(8.0)
	// 			.planDay("20220704")
	// 			.dayHour(8.0)
	// 			.startedHour("0900")
	// 			.endedHour("1800")
	// 			.groupMainId("TR001")
	// 			.groupSubId("ZDUM1")
	// 			.codeId("Z001")
	// 			.codeMainNm("주업무")
	// 			.codeSubNm("R&D 및 내부 PJT (NonPJT코드) - 시장조사, 분석, 계획, 설계/개발/테스트/이행")
	// 			.workDetail("테스트 데이터" + i)
	// 			.wfhYn("0")
	// 			.enrollYn("0")
	// 			.member(memberService.findMember(14L))
	// 			.build();
	//
	// 		planRepository.save(plan);
	// 	}
	// }

	// @Test
	// @DisplayName("001. 계획 데이터 카운트 (10000개)")
	// @Order(1)
	// public void countPlans() throws Exception {
	//     //given
	//
	//     //when
	// 	int count = planRepository.countByPlanDay("20220704");
	//
	// 	//then
	// 	System.out.println("[+] 개수 => " + count);
	// }
	//
	//
	// @Test
	// @DisplayName("002. 계획 1000개 변경 테스트(List) - V1")
	// @Order(2)
	// void draftPlanV1() {
	//
	// 	em.flush();
	// 	em.clear();
	//
	// 	//given
	// 	List<Plan> plans = planRepository.findByPlanDayAndMemberMemberId("20220704", 14L);
	//
	// 	TaskDto defaultTaskDto = taskService.getDefaultTaskDto();
	//
	// 	List<PlanSaveReq> planSaveReqs = plans.stream()
	// 		.filter(p -> p.getPlanId() % 2 == 0)
	// 		.map(p -> PlanSaveReq.builder()
	// 			.planDay("20220704")
	// 			.planId(p.getPlanId())
	// 			.startedHour("0900")
	// 			.endedHour("1200")
	// 			.groupMainId(defaultTaskDto.getGroupMainId())
	// 			.groupSubId(defaultTaskDto.getGroupSubId())
	// 			.codeId(defaultTaskDto.getCodeId())
	// 			.codeMainNm(defaultTaskDto.getCodeMainNm())
	// 			.codeSubNm(defaultTaskDto.getCodeSubNm())
	// 			.workDetail("Time Report 프로젝트 개발 - 수정됨V1")
	// 			.seq(1)
	// 			.taskHour(3.0)
	// 			.wfhYn("0")
	// 			.build()
	// 		).collect(Collectors.toList());
	//
	// 	//when
	// 	Instant start = Instant.now();
	// 	int limit = 1000;
	// 	for (int i = 0; i < planSaveReqs.size(); i += limit) {
	// 		planService.draftPlanOld(planSaveReqs.subList(i, Math.min(i+limit, planSaveReqs.size())), 14L);
	// 	}
	//
	// 	Instant end = Instant.now();
	//
	// 	//then
	// 	System.out.println("V1 - 수정 요청까지 총 수행시간: " + Duration.between(start, end).toMillis() + " ms");
	// 	System.out.println("V1 - 수정된 개수 : " + planSaveReqs.size());
	//
	// 	em.flush();
	// 	em.clear();
	// }
	//
	// @Test
	// @DisplayName("003. 계획 1000개 변경 테스트(Map) - V2")
	// @Order(3)
	// void draftPlanV2() {
	//
	// 	em.flush();
	// 	em.clear();
	//
	// 	//given
	// 	List<Plan> plans = planRepository.findByPlanDayAndMemberMemberId("20220704", 14L);
	//
	// 	TaskDto defaultTaskDto = taskService.getDefaultTaskDto();
	//
	// 	List<PlanSaveReq> planSaveReqs = plans.stream()
	// 		.filter(p -> p.getPlanId() % 2 == 0)
	// 		.map(p -> PlanSaveReq.builder()
	// 			.planDay("20220704")
	// 			.planId(p.getPlanId())
	// 			.startedHour("0900")
	// 			.endedHour("1200")
	// 			.groupMainId(defaultTaskDto.getGroupMainId())
	// 			.groupSubId(defaultTaskDto.getGroupSubId())
	// 			.codeId(defaultTaskDto.getCodeId())
	// 			.codeMainNm(defaultTaskDto.getCodeMainNm())
	// 			.codeSubNm(defaultTaskDto.getCodeSubNm())
	// 			.workDetail("Time Report 프로젝트 개발 - 수정됨V2")
	// 			.seq(1)
	// 			.taskHour(3.0)
	// 			.wfhYn("0")
	// 			.build()
	// 		).collect(Collectors.toList());
	//
	// 	//when
	// 	Instant start = Instant.now();
	// 	int limit = 1000;
	// 	for (int i = 0; i < planSaveReqs.size(); i += limit) {
	// 		planService.draftPlan(planSaveReqs.subList(i, Math.min(i+limit, planSaveReqs.size())), 14L);
	// 	}
	//
	// 	Instant end = Instant.now();
	//
	// 	//then
	// 	System.out.println("V2 - 수정 요청까지 총 수행시간: " + Duration.between(start, end).toMillis() + " ms");
	// 	System.out.println("V2 - 수정된 개수 : " + planSaveReqs.size());
	// 	em.flush();
	// 	em.clear();
	//
	// }

	// @AfterAll
	// public void afterDelete() {
	// 	List<Plan> plans = planRepository.findByPlanDayAndMemberMemberId("20220704", 14L);
	// 	planRepository.deleteAll(plans);
	// }
}
