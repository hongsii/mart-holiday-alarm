package com.hongsi.martholidayalarm.api.interceptor;

import com.hongsi.martholidayalarm.api.model.ApiLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ApiLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiLog apiLog = ApiLog.builder()
                .remoteAddr(getRemoteIP(request))
                .httpMethod(request.getMethod())
                .urlPattern((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                .queryString(request.getQueryString())
                .build();
        log.info(apiLog.toString());
        return super.preHandle(request, response, handler);
    }

    private static String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
