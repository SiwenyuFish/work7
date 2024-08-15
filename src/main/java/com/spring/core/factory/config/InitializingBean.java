package com.spring.core.factory.config;


public interface InitializingBean {

	void afterPropertiesSet() throws Exception;
}
