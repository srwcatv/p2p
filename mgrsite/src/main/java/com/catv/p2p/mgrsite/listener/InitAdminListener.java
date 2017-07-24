package com.catv.p2p.mgrsite.listener;

import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.service.ILoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化超级管理员的监听器
 *
 * 1,在Spring中,实现了ApplicationListener接口的类就可以作为Spring的监听器来监听spring中特殊的事件
 * 2,在spring中
 * ,ApplicationEvent这个类相当于所有的事件,如果我们的监听器继承ApplicationListener<ApplicationEvent
 * >;相当于我们这个监听器监听的是Spring里面所有的消息
 * 3,现在我只想监听Spring容器启动完成的事件,只需要监听ContextRefreshedEvent事件就可以了
 *
 *
 * @author Administrator
 *
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ILoginInfoService loginInfoService;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loginInfoService.initAdmin();
    }
}
