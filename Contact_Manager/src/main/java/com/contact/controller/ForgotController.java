package com.contact.controller;

import java.util.Random;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.entities.User;
import com.contact.repo.UserRepo;
import com.contact.service.EmailService;
@Controller
public class ForgotController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;
	Random random = new Random(1000);
	@RequestMapping("/forgot")
	public String openEmailForm() {
		return "forgot_email";
	}
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam(name = "email")String email,HttpSession session) {
		System.out.println(email);
		
		//genrating otp of 4 digit
		
		
int otp =		random.nextInt(9999999);
System.out.println(otp);
String subject="OTP from Contact Manager";
String message="OTP = "+otp+"";
String to=email;

boolean flag=   this.emailService.sendEmail(subject, message, to);
if (flag) {
	session.setAttribute("myotp", otp);
	session.setAttribute("email", email);
	return "verify_otp";
}
else {
	session.setAttribute("message", "Please check your email id and try again");
	
	
	return "forgot_email";
	
}
		
	}
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") int otp,HttpSession session) {
		
		int myotp = (int) session.getAttribute("myotp");
		String email=(String) session.getAttribute("email");
		
		if (myotp==otp) {
			
	User user =		this.userRepo.getUserByName(email);
	
	if (user==null) {
		
		//send error
		session.setAttribute("message", "User does not exists");
		return "forgot_email";
		
	}else {
		//send change password
	}
			
			
			
			return "password_change";
			
		} else {
			session.setAttribute("message", "You have entered wrong otp");
			return "verify_otp";

		}
		
	}
	
	//change password
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newPassword")String newPassword,HttpSession session){
		String email = (String) session.getAttribute("email");
	User user =	this.userRepo.getUserByName(email);
	user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
	this.userRepo.save(user);
	
	return "redirect:/signin?change=password changed successfully";
		
		
	}
	
	 
  }
	
	

