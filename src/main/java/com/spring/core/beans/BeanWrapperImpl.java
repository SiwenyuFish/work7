package com.spring.core.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanWrapperImpl implements BeanWrapper {
    private final Object object;
    private final Map<Class<?>, PropertyEditor> customEditors = new HashMap<>();

    public BeanWrapperImpl(Object object) {
        this.object = object;
    }

    @Override
    public Object getPropertyValue(String propertyName) {
        try {
            PropertyDescriptor pd = getPropertyDescriptor(propertyName);
            Method readMethod = pd.getReadMethod();
            if (readMethod != null) {
                return readMethod.invoke(object);
            }
            throw new RuntimeException("No read method for property: " + propertyName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to get property value", e);
        }
    }

    @Override
    public void setPropertyValue(String propertyName, Object value) {
        try {
            PropertyDescriptor pd = getPropertyDescriptor(propertyName);
            Method writeMethod = pd.getWriteMethod();
            if (writeMethod != null) {
                Object convertedValue = convertIfNecessary(value, pd.getPropertyType());
                writeMethod.invoke(object, convertedValue);
            } else {
                throw new RuntimeException("No write method for property: " + propertyName);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to set property value", e);
        }
    }

    @Override
    public boolean isReadableProperty(String propertyName) {
        try {
            return getPropertyDescriptor(propertyName).getReadMethod() != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isWritableProperty(String propertyName) {
        try {
            return getPropertyDescriptor(propertyName).getWriteMethod() != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Class<?> getPropertyType(String propertyName) {
        try {
            return getPropertyDescriptor(propertyName).getPropertyType();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get property type", e);
        }
    }

    @Override
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        customEditors.put(requiredType, propertyEditor);
    }

    private PropertyDescriptor getPropertyDescriptor(String propertyName) {
        try {
            PropertyDescriptor[] propertyDescriptors = java.beans.Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                if (pd.getName().equals(propertyName)) {
                    return pd;
                }
            }
            throw new RuntimeException("No such property: " + propertyName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get property descriptor", e);
        }
    }

    private Object convertIfNecessary(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }
        PropertyEditor editor = customEditors.get(targetType);
        if (editor != null) {
            editor.setAsText(value.toString());
            return editor.getValue();
        }
        return convert(value.toString(), targetType);
    }

    private Object convert(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value);
        }
        // Add more type conversion logic as needed
        throw new IllegalArgumentException("Cannot convert value to type " + targetType);
    }
}
