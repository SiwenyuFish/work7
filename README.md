# 项目结构
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─com
    │  │  │      └─spring
    │  │  │          ├─aop
    │  │  │          │  │  Advice.java
    │  │  │          │  │  AfterAdvice.java    //前置通知
    │  │  │          │  │  AopProxy.java    //getproxy接口
    │  │  │          │  │  AspectJExpressionPointcut.java    //切点
    │  │  │          │  │  BeforeAdvice.java    //后置通知
    │  │  │          │  │  CglibDynamicAopProxy.java    //cglib代理
    │  │  │          │  │  DefaultAdvisorAutoProxyCreator.java    //处理器，判断是否创建代理
    │  │  │          │  │  JdkDynamicAopProxy.java    //jdk代理
    │  │  │          │  │  MethodInterceptor.java
    │  │  │          │  │  MethodInvocation.java
    │  │  │          │  │  ProxyFactory.java    //代理工厂
    │  │  │          │  │
    │  │  │          │  └─annotation
    │  │  │          │          Advisor.java    //封装pointcut和advice
    │  │  │          │          After.java    //@After
    │  │  │          │          Aspect.java    //@Aspect
    │  │  │          │          Before.java    //@Before
    │  │  │          │
    │  │  │          ├─boot
    │  │  │          │      AopAutoConfiguration.java    //aop配置
    │  │  │          │      ConditionalOnMissingBean.java    //@ConditionalOnMissingBean
    │  │  │          │      SpringApplication.java    //启动类
    │  │  │          │      TransactionAutoConfiguration.java    //事务配置
    │  │  │          │      WebAutoConfiguration.java    //web配置
    │  │  │          │
    │  │  │          ├─context
    │  │  │          │  ├─annotation
    │  │  │          │  │      AnnotationConfigRegistry.java //注册配置类接口
    │  │  │          │  │
    │  │  │          │  ├─config
    │  │  │          │  │      ApplicationContext.java
    │  │  │          │  │      ApplicationContextAware.java    //感知ApplicationContext
    │  │  │          │  │      ConfigurableApplicationContext.java
    │  │  │          │  │
    │  │  │          │  └─support
    │  │  │          │          AbstractApplicationContext.java
    │  │  │          │          AnnotationConfigApplicationContext.java    //基于配置类的容器
    │  │  │          │          ApplicationContextAwareProcessor.java    //aware处理器
    │  │  │          │          BeanAnnotationBeanPostProcessor.java    //处理注册bean相关注解
    │  │  │          │          GenericApplicationContext.java    //“干净的容器”
    │  │  │          │          WebAnnotationConfigApplicationContext.java //添加tomcat的容器
    │  │  │          │
    │  │  │          ├─core
    │  │  │          │  │  BeansException.java    
    │  │  │          │  │  PropertyValue.java    //属性
    │  │  │          │  │  PropertyValues.java
    │  │  │          │  │
    │  │  │          │  └─factory
    │  │  │          │      │  AutowiredCapableBeanFactory.java    
    │  │  │          │      │  Aware.java
    │  │  │          │      │  BeanFactory.java
    │  │  │          │      │  BeanFactoryAware.java
    │  │  │          │      │  ConfigurableListableBeanFactory.java
    │  │  │          │      │  HierarchicalBeanFactory.java    //获得父容器的接口
    │  │  │          │      │  ListableBeanFactory.java    //获得多个bean的接口
    │  │  │          │      │
    │  │  │          │      ├─annotation    //都是注解
    │  │  │          │      │      Autowired.java    
    │  │  │          │      │      Bean.java    
    │  │  │          │      │      Component.java    
    │  │  │          │      │      ComponentScan.java    
    │  │  │          │      │      Configuration.java
    │  │  │          │      │      Value.java
    │  │  │          │      │
    │  │  │          │      ├─config
    │  │  │          │      │      BeanDefinition.java
    │  │  │          │      │      BeanDefinitionRegistry.java    //definition注册
    │  │  │          │      │      BeanFactoryPostProcessor.java   
    │  │  │          │      │      BeanPostProcessor.java
    │  │  │          │      │      BeanReference.java    //依赖bean
    │  │  │          │      │      DisposableBean.java    //销毁接口
    │  │  │          │      │      InitializingBean.java    //初始化接口
    │  │  │          │      │      InstantiationStrategy.java    //实例化策略
    │  │  │          │      │      SingletonBeanRegistry.java    //单例注册
    │  │  │          │      │
    │  │  │          │      ├─support
    │  │  │          │      │      AbstractAutowireCapableBeanFactory.java    //createBean方法所在
    │  │  │          │      │      AbstractBeanFactory.java
    │  │  │          │      │      BeanNameAware.java
    │  │  │          │      │      DefaultListableBeanFactory.java    //默认BeanFactory
    │  │  │          │      │      DefaultSingletonBeanRegistry.java   
    │  │  │          │      │      DisposableBeanAdapter.java
    │  │  │          │      │      SimpleInstantiationStrategy.java    //无参构造
    │  │  │          │      │
    │  │  │          │      └─util
    │  │  │          │              BeanFactoryUtil.java    //获取所有父容器的beanDefiniton
    │  │  │          │
    │  │  │          ├─jdbc
    │  │  │          │      DataSourceConfig.java   //读取db.properties
    │  │  │          │      JdbcTemplate.java    
    │  │  │          │      RowMapper.java
    │  │  │          │      Transactional.java    //@Transactional
    │  │  │          │      TransactionInterceptor.java    //代理逻辑
    │  │  │          │      TransactionManager.java    //事务管理
    │  │  │          │
    │  │  │          └─web
    │  │  │              ├─annotation    //都是注解
    │  │  │              │      Controller.java
    │  │  │              │      CookieValue.java
    │  │  │              │      DeleteMapping.java
    │  │  │              │      GetMapping.java
    │  │  │              │      PathVariable.java
    │  │  │              │      PostMapping.java
    │  │  │              │      PutMapping.java
    │  │  │              │      RequestBody.java
    │  │  │              │      RequestHeader.java
    │  │  │              │      RequestMapping.java
    │  │  │              │      RequestParam.java
    │  │  │              │      ResponseBody.java
    │  │  │              │
    │  │  │              ├─controller
    │  │  │              │      HelloController.java
    │  │  │              │      HomeController.java
    │  │  │              │
    │  │  │              ├─servlet
    │  │  │              │  │  DispatcherServlet.java    //mvc入口
    │  │  │              │  │  HandlerAdapter.java    //执行控制器方法
    │  │  │              │  │  HandlerExceptionResolver.java    //异常处理
    │  │  │              │  │  HandlerExecutionChain.java    //getHandler返回的执行链
    │  │  │              │  │  HandlerInterceptor.java    //拦截器
    │  │  │              │  │  HandlerMapping.java    //映射
    │  │  │              │  │  ModelAndView.java //视图处理没实现
    │  │  │              │  │  ViewResolver.java
    │  │  │              │  │
    │  │  │              │  ├─handler
    │  │  │              │  │      AbstractHandlerMethodMapping.java
    │  │  │              │  │
    │  │  │              │  ├─method
    │  │  │              │  │  │  HandlerMethod.java
    │  │  │              │  │  │  MethodParameter.java    //封装方法和参数
    │  │  │              │  │  │
    │  │  │              │  │  ├─annotation
    │  │  │              │  │  │      HandlerMethodArgumentResolverComposite.java    //多个参数解析器组合
    │  │  │              │  │  │      PathVariableArgumentResolver.java    
    │  │  │              │  │  │      RequestHeaderMethodArgumentResolver.java    //处理@RequestHeader
    │  │  │              │  │  │      RequestMappingHandlerAdapter.java    
    │  │  │              │  │  │      RequestMappingHandlerMapping.java
    │  │  │              │  │  │      RequestParamArgumentResolver.java    //处理@RequestParam
    │  │  │              │  │  │      ServletCookieValueMethodArgumentResolver.java    //处理Cookie
    │  │  │              │  │  │
    │  │  │              │  │  └─support
    │  │  │              │  │          HandlerMethodArgumentResolver.java    //参数解析
    │  │  │              │  │          HandlerMethodReturnValueHandler.java    //返回值解析
    │  │  │              │  │          HandlerMethodReturnValueHandlerComposite.java    //多个返回值解析器组合
    │  │  │              │  │          ModelAndViewContainer.java
    │  │  │              │  │
    │  │  │              │  └─mvc
    │  │  │              │          DefaultHandlerExceptionResolver.java    //异常处理
    │  │  │              │          ModelAndViewReturnValueHandler.java
    │  │  │              │          RequestResponseBodyMethodProcessor.java    //处理@RequestBody和@ResponseBody
    │  │  │              │
    │  │  │              ├─tomcat
    │  │  │              │      TomcatServer.java    //启动tomcat
    │  │  │              │
    │  │  │              └─util
    │  │  │                      Result.java    //封装返回数据
