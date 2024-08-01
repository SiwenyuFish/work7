package com.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class HandlerExecutionChain {
    private final Object handler;
    private final List<HandlerInterceptor> interceptors;

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
        this.interceptors = new ArrayList<>();
    }

    public Object getHandler() {
        return handler;
    }

    public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerInterceptor interceptor : interceptors) {
            if (!interceptor.preHandle(request, response, handler)) {
                return false;
            }
        }
        return true;
    }

    public void applyPostHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        for (HandlerInterceptor interceptor : interceptors) {
            interceptor.postHandle(request, response, handler, mv);
        }
    }

    public void applyAfterCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerInterceptor interceptor : interceptors) {
            interceptor.afterCompletion(request, response, handler);
        }
    }
}

