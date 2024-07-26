package com.spring.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut {
    private PointcutParser pointcutParser;
    private PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        Set<PointcutPrimitive> supportedPrimitives = new HashSet<>();
        supportedPrimitives.add(PointcutPrimitive.EXECUTION);
        pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }
}

