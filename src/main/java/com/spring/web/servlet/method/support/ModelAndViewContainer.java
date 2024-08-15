package com.spring.web.servlet.method.support;

import com.spring.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ModelAndViewContainer {

    private Map<String, Object> model = new HashMap<>();
    private Object view;
    private boolean isViewResolved = false;


    public void setView(Object view) {
        this.view = view;
    }


    // 生成 ModelAndView 对象
    public ModelAndView getModelAndView() {
        if (isViewResolved) {
            return new ModelAndView((String) view, model);
        } else {
            return null; // 或者抛出异常，表示没有视图解析
        }
    }
}
