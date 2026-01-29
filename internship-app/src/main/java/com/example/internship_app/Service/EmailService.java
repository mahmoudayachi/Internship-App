package com.example.internship_app.Service;


import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    private final JavaMailSender mailSender ;
    private final TemplateEngine templateEngine;
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendmail(String to ,String subject ,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("ayachimahmoud175@gmail.com");
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


    public void sendAcceptedApplicationEmail(
            String to,
            String studentName,
            String internshipTitle,
            String companyName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("studentName", studentName);
            context.setVariable("internshipTitle", internshipTitle);
            context.setVariable("companyName", companyName);

            String html = templateEngine.process("application-accetped", context);

            helper.setTo(to);
            helper.setSubject("Internship Application Accepted 🎉");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
    public void sendRejectedApplicationEmail(
            String to,
            String studentName,
            String internshipTitle,
            String companyName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("studentName", studentName);
            context.setVariable("internshipTitle", internshipTitle);
            context.setVariable("companyName", companyName);

            String html = templateEngine.process(
                    "application-rejected", context);

            helper.setTo(to);
            helper.setSubject("Update on Your Internship Application");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send rejection email", e);
        }
    }

    public void sendStudentAccountActivatedEmail(
            String to,
            String studentName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("studentName", studentName);
            context.setVariable("loginUrl", "http://localhost:4200/login");

            String html = templateEngine.process(
                    "student-account-activated", context);

            helper.setTo(to);
            helper.setSubject("Your Account Has Been Activated ");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send activation email", e);
        }
    }
    public void sendStudentAccountDeactivatedEmail(
            String to,
            String studentName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("studentName", studentName);

            String html = templateEngine.process(
                    "student-account-desactivated", context);

            helper.setTo(to);
            helper.setSubject("Your Account Has Been Desactivated");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send deactivation email", e);
        }
    }

    public void sendCompanyAccountActivatedEmail(
            String to,
            String companyName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("companyName", companyName);
            context.setVariable("loginUrl", "http://localhost:4200/login");

            String html = templateEngine.process(
                    "company-account-activated", context);

            helper.setTo(to);
            helper.setSubject("Your Company Account Has Been Activated");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send company activation email", e);
        }
    }

    public void sendCompanyAccountDeactivatedEmail(
            String to,
            String companyName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("companyName", companyName);

            String html = templateEngine.process(
                    "company-account-desactivated", context);

            helper.setTo(to);
            helper.setSubject("Your Company Account Has Been Desactivated");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send company deactivation email", e);
        }
    }


}
