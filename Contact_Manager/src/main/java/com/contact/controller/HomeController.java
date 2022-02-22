package com.contact.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.entities.User;
import com.contact.helper.Message;
import com.contact.repo.UserRepo;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepo userRepo;

	@RequestMapping("/")
	public String home(Model m) {
		m.addAttribute("title", "Home - Contact Manager");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model m) {
		m.addAttribute("title", "About - Contact Manager");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("title", "Register - Contact Manager");
		m.addAttribute("user", new User());
		return "signup";
	}

	// handeler for registering user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,
			@RequestParam(value = "tc", defaultValue = "false") boolean tc, Model m, HttpSession session) {

		try {
			if (!tc) {
				System.out.println("not agreed t&c");
				throw new Exception("You have not agreed terms and conditions");

			}

			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.toString());
				m.addAttribute("user", user);

				return "signup";

			}
			System.out.println(tc);
			user.setRole("ROLE_USER");
			user.setActive(true);
			user.setImageUrl("default.png");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			User result = this.userRepo.save(user);
			System.out.println(result.getId());
			m.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered ", "alert-success"));
			return "signup";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong !!", "alert-danger"));
			return "signup";
		}

	}

	
	//handeler for custom login
	@GetMapping("/signin")
	public String customLogin(Model m) {
		m.addAttribute("title","Login - Contact Manager");
		return "signin";
	}
	
}
