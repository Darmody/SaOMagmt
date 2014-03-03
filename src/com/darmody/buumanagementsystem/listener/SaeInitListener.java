package com.darmody.buumanagementsystem.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 2013.7.20
 * @author Caihuanyu
 * @content sae监听器
 * @deprecated
 */

public class SaeInitListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }

}
