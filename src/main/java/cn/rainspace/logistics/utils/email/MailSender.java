package cn.rainspace.logistics.utils.email;

import javax.mail.*;
import java.util.Properties;

public class MailSender {
    private Session session;
    private Transport transport;
    private String username = "foliet@rainspace.cn";
    private String password = "Fqh66545896";
    private String smtpServer = "smtp.exmail.qq.com";

    public void init()throws Exception
    {
        //设置属性
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPSSLTransport");
        props.put("mail.smtp.host", smtpServer); //设置发送邮件服务器
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true"); //SMTP服务器需要身份验证

        // 创建Session对象
        session = Session.getInstance(props,new Authenticator(){   //验账账户
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //session.setDebug(true); //输出跟踪日志

        // 创建Transport对象
        transport = session.getTransport();
    }
    public void sendMessage(Message msg)throws Exception{
        //创建一个邮件
        //Message msg = HtmlMessage.generate();
        //Message msg = AttachmentMessage.generate();
        //发送邮件
        transport.connect();
        transport.sendMessage(msg, msg.getAllRecipients());
        //打印结果
        System.out.println("邮件已经成功发送");
    }

    public void close()throws Exception
    {
        transport.close();
    }
}
