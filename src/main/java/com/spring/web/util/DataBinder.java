package com.spring.web.util;


import com.spring.core.beans.BeanWrapper;
import com.spring.core.beans.BeanWrapperImpl;


import java.util.Map;

public class DataBinder {

    private final Object target;
    private final String objectName;


    public DataBinder(Object target, String objectName) {
        this.target = target;
        this.objectName = objectName;
    }


    public void bind(Map<String, String[]> parameters) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            if (paramValues.length > 0) {
                String paramValue = paramValues[0];
                // 进行类型转换
                Object convertedValue = convertIfNecessary(paramValue, beanWrapper.getPropertyType(paramName));
                // 设置属性值
                beanWrapper.setPropertyValue(paramName, convertedValue);
            }
        }
    }

    private Object convertIfNecessary(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        }
        // Add more type conversion logic as needed
        return null;
    }



    public Object getTarget() {
        return target;
    }

    public String getObjectName() {
        return objectName;
    }
}
