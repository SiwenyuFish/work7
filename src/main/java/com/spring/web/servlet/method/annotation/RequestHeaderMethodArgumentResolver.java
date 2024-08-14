package com.spring.web.servlet.method.annotation;

import com.spring.web.annotation.RequestHeader;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

public class RequestHeaderMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestHeader.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        RequestHeader annotation = parameter.getParameterAnnotation(RequestHeader.class);
        if (annotation != null) {
            String headerName = annotation.value();
            return request.getHeader(headerName);
        }
        return null;
    }
}
