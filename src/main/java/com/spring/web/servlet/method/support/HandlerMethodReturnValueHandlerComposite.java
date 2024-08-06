package com.spring.web.servlet.method.support;

import com.spring.web.servlet.method.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class HandlerMethodReturnValueHandlerComposite implements HandlerMethodReturnValueHandler {
    private final List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();

    public HandlerMethodReturnValueHandlerComposite addHandler(HandlerMethodReturnValueHandler handler) {
        this.handlers.add(handler);
        return this;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return handlers.stream().anyMatch(handler -> handler.supportsReturnType(returnType));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerMethodReturnValueHandler handler : this.handlers) {
            if (handler.supportsReturnType(returnType)) {
                handler.handleReturnValue(returnValue, returnType, mavContainer, request, response);
                return;
            }
        }
        throw new UnsupportedOperationException("方法" + returnType.getMethod()+"没有合适的返回值解析器");
    }
}
