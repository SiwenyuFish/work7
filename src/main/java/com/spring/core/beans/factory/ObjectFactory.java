package com.spring.core.beans.factory;

import com.spring.core.beans.BeansException;

public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
