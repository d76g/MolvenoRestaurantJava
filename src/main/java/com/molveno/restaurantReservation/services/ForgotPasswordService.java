package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.ForgetPasswordToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    JavaMailSender javaMailSender;

    private final int MINUTES = 3;

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime expireTimeRange() {
        return LocalDateTime.now().plusMinutes(MINUTES);
    }

    public void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Password Reset</title>"
                + "    <style>"
                + "        .container {"
                + "            max-width: 600px;"
                + "            margin: 0 auto;"
                + "            padding: 20px;"
                + "            background-color: #f9fafb;"
                + "            border: 1px solid #e5e7eb;"
                + "            border-radius: 8px;"
                + "            font-family: 'Arial', sans-serif;"
                + "        }"
                + "        .header {"
                + "            text-align: center;"
                + "            padding-bottom: 20px;"
                + "            border-bottom: 1px solid #e5e7eb;"
                + "            margin-bottom: 20px;"
                + "        }"
                + "        .content {"
                + "            font-size: 16px;"
                + "            color: #1f2937;"
                + "        }"
                + "        .button {"
                + "            display: inline-block;"
                + "            padding: 10px 20px;"
                + "            margin: 20px 0;"
                + "            background-color: #3b82f6;"
                + "            color: #ffffff;"
                + "            text-decoration: none;"
                + "            border-radius: 4px;"
                + "        }"
                + "        .footer {"
                + "            font-size: 12px;"
                + "            color: #6b7280;"
                + "            text-align: center;"
                + "            padding-top: 20px;"
                + "            border-top: 1px solid #e5e7eb;"
                + "            margin-top: 20px;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <div class=\"header\">"
                + "            <h1>Password Reset</h1>"
                + "        </div>"
                + "        <div class=\"content\">"
                + "            <p>Hello,</p>"
                + "            <p>You recently requested to reset your password for your account. Click the button below to reset it.</p>"
                + "            <p><a href=\"" + emailLink + "\" class=\"button\">Change My Password</a></p>"
                + "            <p>If you did not request a password reset, please ignore this email or contact support if you have questions.</p>"
                + "            <p>Thanks,</p>"
                + "            <p>Molveno Resort</p>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>If you’re having trouble with the button above, copy and paste the URL below into your web browser:</p>"
                + "            <p><a href=\"" + emailLink + "\">" + emailLink + "</a></p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";


        helper.setText(emailContent, true);
        helper.setFrom("codingtechniques17@gmail.com", "Coding Techniques Support");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
    }

    public boolean isExpired(ForgetPasswordToken forgetPasswordToken) {
        return LocalDateTime.now().isAfter(forgetPasswordToken.getExpireTime());
    }

    public String checkValidity(ForgetPasswordToken forgetPasswordToken, Model model) {

        if (forgetPasswordToken == null) {
            model.addAttribute("error", "Invalid Token");
            return "error-page";
        } else if (forgetPasswordToken.isUsed()) {
            model.addAttribute("error", "the token is already used");
            return "error-page";
        } else if (isExpired(forgetPasswordToken)) {
            model.addAttribute("error", "the token is expired");
            return "error-page";
        } else {
            return "reset-password";
        }
    }
}
