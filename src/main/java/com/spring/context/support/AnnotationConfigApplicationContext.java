package com.spring.context.support;

import com.spring.context.annotation.AnnotationConfigRegistry;
import com.spring.core.beans.factory.annotation.Component;
import com.spring.core.beans.factory.annotation.ComponentScan;
import com.spring.core.beans.factory.annotation.Configuration;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        this.register(componentClasses);
        this.refresh();
    }

    public AnnotationConfigApplicationContext() {
        this.refresh();
    }


    @Override
    public void register(Class<?>... componentClasses) {
        this.registerBean(BeanAnnotationBeanPostProcessor.class);
        for (Class<?> componentClass : componentClasses) {

            if(componentClass.isAnnotationPresent(ComponentScan.class)) {
                ComponentScan annotation = componentClass.getAnnotation(ComponentScan.class);
                String value = annotation.value();
                if(!value.isEmpty()) {
                    this.scan(value);
                }else {
                    this.scan(componentClass.getPackage().getName());
                }
            }

            this.registerBean(componentClass);
        }
    }

    public void scan(Object... basePackages) {
        for (Object basePackage : basePackages) {
            String packageName = basePackage.toString();
            try {
                //把包名转化为路径
                String path = packageName.replace('.', '/');
                URL url = Thread.currentThread().getContextClassLoader().getResource(path);
                if (url != null) {
                    File directory = new File(url.getFile());
                    if (directory.exists()) {
                        List<Class<?>> classes = findClasses(directory, packageName);
                        for (Class<?> clazz : classes) {
                            //注册bean到容器中
                            if (clazz.isAnnotationPresent(Component.class)||clazz.isAnnotationPresent(Configuration.class)) {
                                this.registerBean(clazz);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(packageName + '.' + className);
                if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                    classes.add(clazz);
                }
            } else if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
        }
        return classes;
    }


}
