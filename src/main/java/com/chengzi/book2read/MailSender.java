package com.chengzi.book2read;

// [START simple_includes]
import com.google.apphosting.api.ApiProxy;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
// [END simple_includes]

// [START multipart_includes]
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
// [END multipart_includes]

public class MailSender {
    private static final Logger log = Logger.getLogger(MailSender.class.getName());

    public void sendSimpleMail(String subject, String content) {
        // [START simple_example]
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("chengzi12130+spider@gmail.com", "book2read java"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("chengzi12130+spider@gmail.com", "chengzi"));
            msg.setSubject(subject);
            msg.setText(content);
            Transport.send(msg);
        } catch (AddressException e) {
            log.warning(e.toString());
        } catch (MessagingException e) {
            log.warning(e.toString());
        } catch (UnsupportedEncodingException e) {
            log.warning(e.toString());
        }
        // [END simple_example]
    }

    public void sendMultipartMail(String subject, String content) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = content;

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("chengzi12130+spider@gmail.com", "book2read java"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("chengzi12130+spider@gmail.com", "chengzi"));
            msg.setSubject(subject);
            msg.setText(content);

            // [START multipart_example]

            String[] paragraphs = content.split("\r\n");
            StringBuilder sb = new StringBuilder();
            sb.append("<div>");
            sb.append("<h4>"+subject+"</h4>");
            for(String p: paragraphs){
                sb.append("<p>");
                sb.append(p);
                sb.append("</p>");
            }
            sb.append("</div>");
            String htmlBody = sb.toString();// ...
            byte[] attachmentData = null;  // ...
            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            mp.addBodyPart(htmlPart);

//            MimeBodyPart attachment = new MimeBodyPart();
//            InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
//            attachment.setFileName("manual.pdf");
//            attachment.setContent(attachmentDataStream, "application/pdf");
//            mp.addBodyPart(attachment);

            msg.setContent(mp);
            // [END multipart_example]

            Transport.send(msg);

        } catch (AddressException e) {
            log.warning(e.toString());
        } catch (MessagingException e) {
            log.warning(e.toString());
        } catch (UnsupportedEncodingException e) {
            log.warning(e.toString());
        }
    }
}
