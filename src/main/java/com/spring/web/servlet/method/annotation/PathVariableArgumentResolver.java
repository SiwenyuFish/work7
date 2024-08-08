package com.spring.web.servlet.method.annotation;

import com.spring.web.annotation.PathVariable;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodArgumentResolver;
import javax.servlet.http.HttpServletRequest;



public class PathVariableArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PathVariable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
        String variableName = pathVariable.value();
        // 假设请求路径已经解析并存储在 request 的属性中
        String variableValue = (String) request.getAttribute(variableName);


        Class<?> paramType = parameter.getParameterType();
        if (paramType == String.class) {
            return variableValue;
        } else if (paramType == int.class || paramType == Integer.class) {
            return variableValue != null ? Integer.parseInt(variableValue) : null;
        }
        // 其他类型可以依次添加处理
        return null;
    }

}