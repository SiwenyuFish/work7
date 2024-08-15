package spring.demo;

import cn.hutool.core.util.StrUtil;
import com.spring.core.factory.annotation.Component;
import com.spring.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        return StrUtil.isNotBlank(token);
    }

}
