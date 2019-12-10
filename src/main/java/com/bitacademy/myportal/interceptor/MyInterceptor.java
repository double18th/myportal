package com.bitacademy.myportal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * Interceptor : 컨트롤러 앞/뒤에서 요청과 응답을 가로채 처리 
 * 필터와의 차이점 : 
 * 필터 - DispatcherController 이전에서 요청/응답 처리
 * 인터셉터 - DispatcherController 이후에 작동. 
 * 		   Spring Container 내부 생성된 Bean 객체 활용 가능
 */
public class MyInterceptor implements HandlerInterceptor {
	// 이전에 만든 로그 달아주기
	private static final Logger logger = 
			LoggerFactory.getLogger(MyInterceptor.class);
	// preHandle : 컨트롤러 실행 이전
	// 만약 return false라면 연결되어 있는 handler로 요청이 전달되지 않는다
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("MyInterceptor.preHandle call");		
		return true;
	}
	// postHandle : 컨트롤러 수행 이후 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("MyInterceptor.postHandle call");

	}
	// afterCompletion : 뷰 작업까지 완료된 시점에서 시행
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("MyInterceptor.afterCompletion call");

	}

}
