package com.spring.web.servlet.method.support;


import com.spring.web.servlet.method.MethodParameter;

import javax.servlet.http.HttpServletRequest;


public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);
    Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception;
}
