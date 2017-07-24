package com.catv.p2p.base.service;

/**
 * 邮件发送
 */
public interface IMailService {

    /**
     * 发送邮件
     * @param target 目标邮箱地址
     * @param title 邮件标题
     * @param content 邮件内容
     */
    void sendMail(String target,String title,String content);
}
