package spring.web;

import com.spring.web.servlet.DispatcherServlet;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReturnValueHandlerTest {

    @Test
    public void testReturnValueHandler() throws IOException, ServletException {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/uu/{id}");
        when(request.getAttribute("id")).thenReturn("1");


        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        dispatcherServlet.init();
        dispatcherServlet.service(request,response);
        System.out.println(responseWriter.toString().trim());
    }


}
