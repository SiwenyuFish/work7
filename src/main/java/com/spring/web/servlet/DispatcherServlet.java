package com.spring.web.servlet;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.core.BeansException;
import com.spring.core.factory.config.InitializingBean;
import com.spring.web.servlet.method.annotation.RequestMappingHandlerMapping;
import com.spring.web.servlet.method.annotation.RequestMappingHandlerAdapter;
import com.spring.web.servlet.mvc.DefaultHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DispatcherServlet extends HttpServlet implements ApplicationContextAware , InitializingBean {


    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private HandlerExceptionResolver handlerExceptionResolver;
    private ViewResolver viewResolver;
    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void init() throws ServletException {
        // 初始化HandlerMapping、HandlerAdapter、ViewResolver等
        initHandlerMapping();
        initHandlerAdapter();
        initViewResolver();
        initHandlerExceptionResolver();
    }

    private void initHandlerMapping() {
        if(applicationContext != null) {
            try {
                this.handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            } catch (BeansException e) {
                this.handlerMapping = new RequestMappingHandlerMapping();
            }
        }else
            this.handlerMapping = new RequestMappingHandlerMapping();
    }

    private void initHandlerAdapter() {
        if(applicationContext != null) {
            try {
                this.handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
            } catch (BeansException e) {
                this.handlerAdapter = new RequestMappingHandlerAdapter();
            }
        }else
            this.handlerAdapter = new RequestMappingHandlerAdapter();
    }

    private void initViewResolver() {
        if(applicationContext != null) {
            try {
                this.viewResolver = applicationContext.getBean(ViewResolver.class);
            } catch (BeansException e) {
                this.viewResolver = new ViewResolver();
            }
        }else
            this.viewResolver = new ViewResolver();
    }

    private void initHandlerExceptionResolver() {
        if(applicationContext != null) {
            try {
                this.handlerExceptionResolver = applicationContext.getBean(DefaultHandlerExceptionResolver.class);
            } catch (BeansException e) {
                this.handlerExceptionResolver = new DefaultHandlerExceptionResolver();
            }
        }else
            this.handlerExceptionResolver = new DefaultHandlerExceptionResolver();
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
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
           handlerExceptionResolver.resolveException(req,resp,null,e);
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


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

