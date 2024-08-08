package com.spring.web.servlet.method.annotation;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.BeanFactory;
import com.spring.core.beans.factory.BeanFactoryAware;
import com.spring.core.beans.factory.ConfigurableListableBeanFactory;
import com.spring.core.beans.factory.config.InitializingBean;
import com.spring.web.servlet.ModelAndView;
import com.spring.web.servlet.HandlerAdapter;
import com.spring.web.servlet.method.HandlerMethod;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodReturnValueHandlerComposite;
import com.spring.web.servlet.method.support.ModelAndViewContainer;
import com.spring.web.servlet.mvc.ModelAndViewReturnValueHandler;
import com.spring.web.servlet.mvc.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    private final HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();
    private final HandlerMethodReturnValueHandlerComposite returnValueHandlers = new HandlerMethodReturnValueHandlerComposite();


    public RequestMappingHandlerAdapter() {
        // 添加参数解析器
        argumentResolvers.addResolver(new RequestParamArgumentResolver());
        argumentResolvers.addResolver(new PathVariableArgumentResolver());
        // 添加返回值解析器
        returnValueHandlers.addHandler(new ModelAndViewReturnValueHandler());
        returnValueHandlers.addHandler(new RequestResponseBodyMethodProcessor());
    }



    @Override
    public ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Object[] args = resolveArguments(request, response, method);
        Object result = method.invoke(handlerMethod.getController(), args);
        ModelAndViewContainer mav = new ModelAndViewContainer();
        returnValueHandlers.handleReturnValue(result, new MethodParameter(handlerMethod.getMethod()), mav, request, response);
        return mav.getModelAndView();

    }

    private Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response, Method method) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter methodParameter = new MethodParameter(method, i);
            args[i] = argumentResolvers.resolveArgument(methodParameter, request);
        }
        return args;
    }

}
