package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.EmailVerify;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.service.IEmailVerifyService;
import com.catv.p2p.base.service.IMailService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.service.IVerifyCodeService;
import com.catv.p2p.base.util.BidConst;
import com.catv.p2p.base.util.DateUtil;
import com.catv.p2p.base.util.MD5;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.base.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * 发送验证码
 */
@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Value("${sms.username}")
    private String username;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.url}")
    private String url;

    @Value("${sms.apiKey}")
    private String apiKey;

    @Value("${mail.hostUrl}")
    private String hostUrl;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IEmailVerifyService emailVerifyService;

    /**
     * 发送验证码
     * @param phoneNumber 手机号
     */
    public void sendVerifyCode(String phoneNumber) {

        //判断手机号是否已经绑定其他用户
        int count = userInfoService.getByPhoneNumber(phoneNumber);
        if (count > 0){
            throw new RuntimeException("改手机号已经绑定其他用户");
        }

        //获取存放在session中的VerifyCodeVo
        VerifyCodeVo verifyCodeVoInSession = UserContext.getVerifyCodeVo();

        if (verifyCodeVoInSession == null || DateUtil.getBetweenDate(new Date(),verifyCodeVoInSession.getLastSendTime()) > 90){
            //生成验证码
            String code = UUID.randomUUID().toString().substring(0,4);
            //发送验证码
            try {
                //创建url对象
                URL url = new URL(this.url);
                //得到HttpURLConnection连接对象
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //拼接发送内容
                StringBuilder content = new StringBuilder(100)
                        .append("username=").append(username)
                        .append("&password=").append(password)
                        .append("&apiKey=").append(apiKey).append("&phoneNumber=")
                        .append(phoneNumber).append("&content=")
                        .append("验证码是:").append(code).append(",请在5分钟内使用");
                //设置发送请求的方式为post
                conn.setRequestMethod("POST");
                // 设置POST请求是有请求体的
                conn.setDoOutput(true);
                //将发送的内容写到输出流中
                conn.getOutputStream().write(content.toString().getBytes("utf-8"));
                //获得输入流中的响应
               String response = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("UTF-8"));
               System.out.println(response);
               //判断响应是否发送成功
               if (response.startsWith("success:")){
                   VerifyCodeVo verifyCodeVo = new VerifyCodeVo();
                   //将手机号、验证码存放到session中
                   verifyCodeVo.setCode(code);
                   verifyCodeVo.setPhoneNumber(phoneNumber);
                   verifyCodeVo.setLastSendTime(new Date());
                   UserContext.putVerifyCodeVo(verifyCodeVo);
               } else {
                   throw new RuntimeException();
               }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("短信发送失败");
            }
        } else {
            throw new RuntimeException("发送过于频繁");
        }

    }


    public void sendEmail(String email) {
        //判断邮箱是否已经绑定其他用户
        int count = userInfoService.getByEmail(email);
        if (count > 0){
            throw new RuntimeException("改邮箱已经绑定其他用户");
        }
        //获取当前登录用户信息
        UserInfo userInfo = userInfoService.getByPrimaryKey(UserContext.getCurrent().getId());
        //判断当前登录用户是否已经绑定邮箱
        if (!userInfo.getIsBindEmail()){
            //发送邮件
            String uuid = MD5.encode(UUID.randomUUID().toString());
            //拼接发送内容
            StringBuilder content = new StringBuilder("请点击连接确认绑定邮箱:")
                    .append("<a href='")
                    .append(hostUrl)
                    .append("?uuid=")
                    .append(uuid)
                    .append("' >点击跳转到激活页面</a>,邮件有效期为")
                    .append(BidConst.VERIFYEMAIL_VAILDATE_DAY)
                    .append("天");
            try {
                String title = "邮箱确认";
                //发送邮件
                mailService.sendMail(email,title,content.toString());
                //创建邮箱信息对象
                EmailVerify emailVerify = new EmailVerify();
                emailVerify.setEmail(email);
                emailVerify.setSendTime(new Date());
                emailVerify.setUserInfo_id(userInfo.getId());
                emailVerify.setUuid(uuid);
                emailVerifyService.save(emailVerify);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("该用户已经绑定邮箱");
        }
    }
}
