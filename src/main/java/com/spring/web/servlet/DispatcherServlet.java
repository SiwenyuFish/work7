package com.spring.web.servlet;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.core.beans.BeansException;
import com.spring.web.servlet.method.annotation.RequestMappingHandlerMapping;
import com.spring.web.servlet.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet implements ApplicationContextAware {


    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;


    @Override
    public void init() throws ServletException {

        // 初始化HandlerMapping、HandlerAdapter、ViewResolver等
        this.handlerMapping = new RequestMappingHandlerMapping();
        this.handlerAdapter = new RequestMappingHandlerAdapter();
        this.viewResolver = new ViewResolver();



    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HandlerExecutionChain handlerChain = null;
        try {
            // 1. 查找Handler
            handlerChain = handlerMapping.getHandler(req);

            if (handlerChain == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                System.out.println(req.getRequestURI());
                System.out.println("404 not found");
                return;
            }

            // 2. 处理前拦截器
            if (!handlerChain.applyPreHandle(req, resp)) {
                return;
            }

            // 3. 处理请求
            ModelAndView mv = handlerAdapter.invokeHandlerMethod(req, resp, handlerChain.getHandler());

            // 4. 视图解析
            if (mv != null) {
                viewResolver.resolveView(mv, req, resp);
            }

            // 5. 处理后拦截器
            handlerChain.applyPostHandle(req, resp, mv);
        } catch (Exception e) {
            // 异常处理
            processException(req, resp, e);
        } finally {
            // 清理资源或做最终的处理
            try {
                if (handlerChain != null) {
                    handlerChain.applyAfterCompletion(req, resp);
                }
            } catch (Exception e) {
                System.out.println("..");
            }
        }
    }

    private void processException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        // 简单异常处理
        e.printStackTrace();
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "laoda");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}

