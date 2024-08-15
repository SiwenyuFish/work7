package com.spring.core;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public void addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue value = this.propertyValueList.get(i);
            if (value.getName().equals(pv.getName())) {
                this.propertyValueList.set(i, pv);
                return;
            }
        }
        this.propertyValueList.add(pv);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue propertyValue = this.propertyValueList.get(i);
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }
}
