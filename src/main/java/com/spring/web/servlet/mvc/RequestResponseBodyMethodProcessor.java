package com.spring.web.servlet.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.web.annotation.RequestBody;
import com.spring.web.annotation.ResponseBody;
import com.spring.web.servlet.ModelAndView;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodArgumentResolver;
import com.spring.web.servlet.method.support.HandlerMethodReturnValueHandler;
import com.spring.web.servlet.method.support.ModelAndViewContainer;
import com.spring.web.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestResponseBodyMethodProcessor implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            // 读取请求体
            String requestBody = new String(request.getInputStream().toString());
            // 将请求体转换为方法参数类型
            return objectMapper.readValue(requestBody, parameter.getParameterType());
        }
        throw new IllegalArgumentException("Unsupported content type: " + contentType);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponseBody.class) || !ModelAndView.class.isAssignableFrom(returnType.getReturnType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (returnValue != null) {
            // 设置响应内容类型
            response.setContentType("application/json");

            // 将返回值转换为 Result 类型
            Result<?> result;
            if (returnValue instanceof Result) {
                result = (Result<?>) returnValue;
            } else {
                result = new Result<>(200, "Success", returnValue);
            }

            // 将返回值转换为 JSON
            String jsonResponse = objectMapper.writeValueAsString(result);
            // 写入响应体
            response.getWriter().write(jsonResponse);
        }
    }
}
