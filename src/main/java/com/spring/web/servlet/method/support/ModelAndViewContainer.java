package com.spring.web.servlet.method.support;

import com.spring.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ModelAndViewContainer {

    private Map<String, Object> model = new HashMap<>();
    private Object view;
    private boolean isViewResolved = false;

    // 存储模型数据
    public void addObject(String name, Object value) {
        this.model.put(name, value);
    }

    // 获取模型数据
    public Object getObject(String name) {
        return this.model.get(name);
    }

    // 设置视图名
    public void setViewName(String viewName) {
        this.view = viewName;
        this.isViewResolved = true;
    }

    public void setView(Object view) {
        this.view = view;
    }

    // 获取视图名
    public String getViewName() {
        return this.view instanceof String ? (String)this.view : null;
    }

    // 检查视图是否已经解析
    public boolean isViewResolved() {
        return this.isViewResolved;
    }

    // 获取模型数据
    public Map<String, Object> getModel() {
        return this.model;
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
