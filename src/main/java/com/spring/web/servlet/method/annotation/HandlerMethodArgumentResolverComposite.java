package com.spring.web.servlet.method.annotation;

import com.spring.web.servlet.method.MethodParameter;
import com.spring.web.servlet.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

    public void addResolver(HandlerMethodArgumentResolver resolver) {
        resolvers.add(resolver);
    }

    public void addResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        this.resolvers.addAll(resolvers);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getResolver(parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        HandlerMethodArgumentResolver resolver = getResolver(parameter);
        if (resolver != null) {
            return resolver.resolveArgument(parameter, request);
        }
        throw new IllegalArgumentException("No suitable resolver for parameter: " + parameter.getParameter().getName());
    }

    private HandlerMethodArgumentResolver getResolver(MethodParameter parameter) {
        for (HandlerMethodArgumentResolver resolver : resolvers) {
            if (resolver.supportsParameter(parameter)) {
                return resolver;
            }
        }
        return null;
    }
}
