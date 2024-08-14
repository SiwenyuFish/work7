package com.spring.web.servlet.mvc;

import com.spring.web.servlet.HandlerExceptionResolver;
import com.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String errorMessage = "{\"error\": \"" + ex.getMessage() + "\"}";
            response.getWriter().write(errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
