package com.spring.core.beans.factory.config;


public interface InitializingBean {

	void afterPropertiesSet() throws Exception;
}
