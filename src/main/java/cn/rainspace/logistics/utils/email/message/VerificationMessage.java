package cn.rainspace.logistics.utils.email.message;

import org.apache.ibatis.io.Resources;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.util.Date;
import java.util.Properties;

public class VerificationMessage {
    public static MimeMessage generate(String to, String code) throws Exception {
        String from = "foliet@rainspace.cn"; // 发件人地址
        String subject = "验证码";
        String body = Files.readString(Resources.getResourceAsFile("EmailMessages/verification.html").toPath());
        body = body.replace("{userName}", to);
        body = body.replace("{code}", code);
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(new Properties());
        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(from));
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        // 设置发送日期
        message.setSentDate(new Date());
        // 设置邮件主题
        message.setSubject(subject);
        // 设置HTML格式的邮件正文
        message.setContent(body, "text/html;charset=gb2312");
        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 把MimeMessage对象中的内容写入到文件中
        //msg.writeTo(new FileOutputStream("e:/HtmlMessage.eml"));
        return message;
    }
}