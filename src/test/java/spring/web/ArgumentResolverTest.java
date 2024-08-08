package spring.web;

import com.spring.web.servlet.DispatcherServlet;
import com.spring.web.servlet.method.MethodParameter;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ArgumentResolverTest {

    @Test
    public void testArgumentResolver3() throws ServletException, IOException {

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/hello");


        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        dispatcherServlet.init();
        dispatcherServlet.service(request,response);
        System.out.println(responseWriter.toString().trim());

    }



    @Test
    public void testArgumentResolver() throws ServletException, IOException {

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/kobe");
        when(request.getParameter("name")).thenReturn("laoda");
        when(request.getParameter("number")).thenReturn("24");

        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        dispatcherServlet.init();
        dispatcherServlet.service(request,response);
        System.out.println(responseWriter.toString().trim());

    }

    @Test
    public void testArgumentResolver2() throws ServletException, IOException {

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users/{id}");
        when(request.getAttribute("id")).thenReturn("1");


        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        dispatcherServlet.init();
        dispatcherServlet.service(request,response);
        System.out.println(responseWriter.toString().trim());


    }


}
