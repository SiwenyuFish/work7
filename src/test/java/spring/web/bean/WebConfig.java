package spring.web.bean;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;
import com.spring.web.servlet.DispatcherServlet;
import com.spring.web.servlet.HandlerExceptionResolver;
import com.spring.web.servlet.ViewResolver;
import com.spring.web.servlet.method.annotation.RequestMappingHandlerAdapter;
import com.spring.web.servlet.method.annotation.RequestMappingHandlerMapping;
import com.spring.web.servlet.mvc.DefaultHandlerExceptionResolver;

@Configuration
public class WebConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public RequestMappingHandlerAdapter handlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }

    @Bean
    public ViewResolver viewResolver() {
        return new ViewResolver();
    }

    @Bean
    public DefaultHandlerExceptionResolver handlerExceptionResolver() {
        return new DefaultHandlerExceptionResolver();
    }


}
