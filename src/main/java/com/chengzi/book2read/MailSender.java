package com.chengzi.book2read;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

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

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("chengzi12130+spider@gmail.com", "cz book java spider"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("chengzi12130+spider@gmail.com", "dear cz"));
            msg.setSubject(subject);
            msg.setText(content);

            // [START multipart_example]

            String[] paragraphs = content.split("\r\n");
            StringBuilder sb = new StringBuilder();
            sb.append("<div style='font-size:1.5em'>");
            sb.append("<h4>" + subject + "</h4>");
            for (String p : paragraphs) {
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
