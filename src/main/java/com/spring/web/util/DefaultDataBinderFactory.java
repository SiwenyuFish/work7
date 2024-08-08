package com.spring.web.util;

public class DefaultDataBinderFactory implements DataBinderFactory {
    @Override
    public DataBinder createBinder(Object target, String objectName) {
        // 创建一个 DataBinder 实例
        DataBinder binder = new DataBinder(target, objectName);
        // 可以配置更多的绑定选项，比如添加自定义编辑器等
        return binder;
    }
}
