package spring.web;

import com.spring.context.support.AnnotationConfigApplicationContext;
import com.spring.web.servlet.DispatcherServlet;
import org.junit.Test;
import spring.web.bean.WebConfig;
import spring.web.controller.HomeController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebContextTest {

    @Test
    public void testWebContext() throws IOException, ServletException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class, HomeController.class );

//        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }

        DispatcherServlet dispatcherServlet = context.getBean(DispatcherServlet.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/hello");


        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        dispatcherServlet.service(request,response);
        System.out.println(responseWriter.toString().trim());
    }

}
