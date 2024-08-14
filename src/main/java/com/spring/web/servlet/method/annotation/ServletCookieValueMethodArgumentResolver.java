package com.spring.web.servlet.method.annotation;

import com.spring.web.annotation.CookieValue;
import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ServletCookieValueMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CookieValue.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        CookieValue annotation = parameter.getParameterAnnotation(CookieValue.class);
        if (annotation != null) {
            String cookieName =annotation.value();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookieName.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }
}
