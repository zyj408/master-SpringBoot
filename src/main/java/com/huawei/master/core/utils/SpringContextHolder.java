package com.huawei.master.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware
{
    /**
     * spring上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 获取spring上下文
     * @return
     */
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        SpringContextHolder.applicationContext = applicationContext;
    }

}