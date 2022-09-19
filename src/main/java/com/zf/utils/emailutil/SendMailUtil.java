
package com.zf.utils.emailutil;


import com.zf.domain.vo.MailInfo;
import lombok.NonNull;

import java.io.File;


public class SendMailUtil {

    public static void send(final File file, String toAdd,String id ,String code) {
        final MailInfo mailInfo = creatMail(toAdd,id ,code);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo, file);
            }
        }).start();
    }

    public static void send(String toAdd ,String id,String code) {
        final MailInfo mailInfo = creatMail(toAdd,id,code);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }
    @NonNull
    private static MailInfo creatMail(String toAdd,String id,String code) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost("smtp.qq.com");//发送方邮箱服务器
        mailInfo.setMailServerPort("587");//发送方邮箱端口号
        mailInfo.setValidate(true);
        mailInfo.setUserName("2293667568@qq.com"); // 发送者邮箱地址
        mailInfo.setPassword("cacruzxolthydjhc");// 发送者邮箱授权码
        mailInfo.setFromAddress("2293667568@qq.com"); // 发送者邮箱
        mailInfo.setToAddress(toAdd); // 接收者邮箱
        mailInfo.setSubject("尊敬的用户您的验证码为"); // 邮件主题
        mailInfo.setContent(code); // 邮件文本
        return mailInfo;
    }

}