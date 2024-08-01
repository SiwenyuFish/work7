package com.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewResolver {
    public void resolveView(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 简单输出视图名作为响应
        response.getWriter().write(mv.getViewName());
    }
}

