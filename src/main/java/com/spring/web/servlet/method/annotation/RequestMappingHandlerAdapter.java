package com.spring.web.servlet.method.annotation;

import com.spring.web.servlet.ModelAndView;
import com.spring.web.servlet.HandlerAdapter;
import com.spring.web.servlet.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 调用控制器方法
        Object result = handlerMethod.getMethod().invoke(handlerMethod.getController());
        return new ModelAndView(result.toString()); // 简化处理为返回字符串视图名
    }
}
