package com.spring.web.util;

public interface DataBinderFactory {
    public DataBinder createBinder(Object target, String objectName);
}
