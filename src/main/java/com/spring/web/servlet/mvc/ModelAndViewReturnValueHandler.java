package com.spring.web.servlet.mvc;

import com.spring.web.servlet.ModelAndView;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodReturnValueHandler;
import com.spring.web.servlet.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelAndViewReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return ModelAndView.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (returnValue instanceof ModelAndView) {
            mavContainer.setView((ModelAndView) returnValue);
        }
    }
}
