package com.spring.core.beans;

import java.beans.PropertyEditor;

public interface BeanWrapper {

    Object getPropertyValue(String propertyName);

    void setPropertyValue(String propertyName, Object value);

    boolean isReadableProperty(String propertyName);

    boolean isWritableProperty(String propertyName);

    Class<?> getPropertyType(String propertyName);

    void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor);
}
