package com.spring.web.servlet.method.annotation;

import com.spring.web.annotation.RequestParam;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodArgumentResolver;


import javax.servlet.http.HttpServletRequest;


public class RequestParamArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
        String paramName = requestParam.value();
        String paramValue = request.getParameter(paramName);
        if (paramValue == null && requestParam.required()) {
            throw new IllegalArgumentException("找不到需要的请求参数: " + paramName);
        }
        Class<?> paramType = parameter.getParameterType();
        if (paramType == String.class) {
            return paramValue;
        } else if (paramType == int.class || paramType == Integer.class) {
            return paramValue != null ? Integer.parseInt(paramValue) : null;
        }
        // 其他类型可以依次添加处理
        return null;

    }
}
