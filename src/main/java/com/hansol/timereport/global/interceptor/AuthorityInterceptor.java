package com.hansol.timereport.global.interceptor;

import static com.hansol.timereport.global.response.BaseResponseStatus.INVALID_USER;
import static com.hansol.timereport.global.response.BaseResponseStatus.NOT_EXIST_SESSION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hansol.timereport.global.auth.Authority;
import com.hansol.timereport.global.constant.Grade;
import com.hansol.timereport.global.constant.SessionConst;
import com.hansol.timereport.global.exception.BaseException;
import com.hansol.timereport.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorityInterceptor implements HandlerInterceptor {

	private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)) {
        	return true;
        }

        Authority auth = ((HandlerMethod) handler).getMethodAnnotation(Authority.class);
        if(auth == null) {
        	return true;
        }

    	HttpSession session = request.getSession(false);

		if (session == null) {
			throw new BaseException(NOT_EXIST_SESSION);
		}


		if(auth.target().compareTo(Grade.MANAGER) == 0) {
        	Long memberId = Long.parseLong(session.getAttribute(SessionConst.LOGIN_MEMBER_ID).toString());
        	String memberGrade = memberService.findMember(memberId).getGrade();

        	if(memberGrade.equals(Grade.MANAGER.name()) || memberGrade.equals(Grade.ADMIN.name())) {
        		return true;
        	}

        	throw new BaseException(INVALID_USER);
        }



        if(auth.target().compareTo(Grade.ADMIN) == 0) {
        	Long memberId = Long.parseLong(session.getAttribute(SessionConst.LOGIN_MEMBER_ID).toString());
        	String memberGrade = memberService.findMember(memberId).getGrade();

        	if(memberGrade.equals(Grade.ADMIN.name())) {
        		return true;
        	}


        	throw new BaseException(INVALID_USER); 

    	}

        return false;
    }


}
