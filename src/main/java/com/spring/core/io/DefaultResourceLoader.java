package com.spring.core.io;

public class DefaultResourceLoader implements ResourceLoader {

    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    @Override
    public Resource getResource(String location) {

        if(location.startsWith(CLASSPATH_URL_PREFIX)) {
            location = location.substring(CLASSPATH_URL_PREFIX.length());
            return new ClassPathResource(location);
        }else {
            return new FileSystemResource(location);
        }

    }
}
