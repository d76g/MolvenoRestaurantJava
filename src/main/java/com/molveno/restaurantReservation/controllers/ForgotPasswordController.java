package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.repos.UserRepo;
import org.springframework.ui.Model;
import com.molveno.restaurantReservation.models.ForgetPasswordToken;
import com.molveno.restaurantReservation.models.User;
import com.molveno.restaurantReservation.repos.ForgotPasswordRepository;
import com.molveno.restaurantReservation.services.ForgotPasswordService;
import com.molveno.restaurantReservation.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;


@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/password-request")
    public String passwordRequest(){
        return "password-request";
    }

    @PostMapping("/password-request")
    public String savePasswordRequest(@RequestParam("email")String email, Model model){
        User user = userRepo.findByEmail(email);
        System.out.println(user);
        if (user == null){
           model.addAttribute("error","This Email is not registered");
            return "password-request";
        }

        ForgetPasswordToken  forgotPasswordToken = new ForgetPasswordToken();
        forgotPasswordToken.setExpireTime(forgotPasswordService.expireTimeRange());
        forgotPasswordToken.setToken(forgotPasswordService.generateToken());
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setUsed(false);

        forgotPasswordRepository.save(forgotPasswordToken);

        String emailLink = "http://localhost:9090/reset-password?token=" + forgotPasswordToken.getToken();
        try {
            forgotPasswordService.sendEmail(user.getEmail(),"Password Reset Link",emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error","Error while sending email");
            return "password-request";

        }

        return "redirect:/password-request?success";

    }

    @GetMapping("/reset-password")
    public String resetPassword(@Param(value = "token") String token, Model model, HttpSession session) {
        session.setAttribute("token", token);
        ForgetPasswordToken forgetPasswordToken = forgotPasswordRepository.findByToken(token);
        return forgotPasswordService.checkValidity(forgetPasswordToken, model);
    }

    @PostMapping("/reset-password")
    public String saveResetPassword(HttpServletRequest request, HttpSession session,Model model){
      String password = request.getParameter("password");
      String token = (String)session.getAttribute("token");

      ForgetPasswordToken forgetPasswordToken = forgotPasswordRepository.findByToken(token);
      User user = forgetPasswordToken.getUser();
      user.setPassword(passwordEncoder.encode(password));
      forgetPasswordToken.setUsed(true);
      userService.save(user);
      forgotPasswordRepository.save(forgetPasswordToken);

      model.addAttribute("message", "You have successfuly reset your password");

        return "reset-password";
        
    }


}
