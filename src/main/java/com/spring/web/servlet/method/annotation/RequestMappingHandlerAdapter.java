package com.spring.web.servlet.method.annotation;

import com.spring.web.servlet.ModelAndView;
import com.spring.web.servlet.HandlerAdapter;
import com.spring.web.servlet.method.HandlerMethod;
import com.spring.web.servlet.method.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    private final HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();

    public RequestMappingHandlerAdapter() {
        // 添加参数解析器
        argumentResolvers.addResolver(new RequestParamArgumentResolver());
        argumentResolvers.addResolver(new PathVariableArgumentResolver());
        // 添加其他参数解析器
    }

    @Override
    public ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Object[] args = resolveArguments(request, response, method);
        Object result = method.invoke(handlerMethod.getController(), args);
        return new ModelAndView((String) result);

    }

    private Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response, Method method) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter methodParameter = new MethodParameter(method, i);
            args[i] = argumentResolvers.resolveArgument(methodParameter, request, response);
        }
        return args;
    }

}
