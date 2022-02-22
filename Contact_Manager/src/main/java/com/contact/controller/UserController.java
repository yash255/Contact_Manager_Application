package com.contact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.helper.Message;
import com.contact.repo.ContactRepo;
import com.contact.repo.UserRepo;

@Controller
@MultipartConfig
@RequestMapping("/user")
public class UserController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ContactRepo contactRepo;

	// method to add comman data to response
	@ModelAttribute
	public void addCommonData(Model m, Principal principal) {
		String username = principal.getName();

		// get the user using username

		User user = userRepo.getUserByName(username);
		m.addAttribute("user", user);

	}

	// open add from handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model m) {
		m.addAttribute("title", "Add Contact");
		m.addAttribute("contact", new Contact());

		return "normal/add_contact_form";

	}

	// home dashboard
	@RequestMapping("/index")
	public String dashboard(Model m, Principal principal) {

		m.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}

	// processing add contact form
	@PostMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult,
			@RequestParam("image") MultipartFile file, Principal principal, HttpSession session) {

		try {
			String name = principal.getName();
			User user = this.userRepo.getUserByName(name);

			// processing and uploading file
			if (file.isEmpty()) {
				// if the file is empty
				System.out.println("file is empty");
				contact.setImage("default.png");
			} else {
				// file the file to folder and update the name to contact
				contact.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("file uploaded");

			}
			contact.setUser(user);
			user.getContacts().add(contact);
			this.userRepo.save(user);
			System.out.println(contact);
			System.out.println("added");
			// message success
			session.setAttribute("message", new Message("Your contact is added !! Add more..", "success"));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong !! Please try again", "danger"));
		}

		return "normal/add_contact_form";
	}

	// show contacts handler
	// per page 5 contact
	// current page =0 [page]
	@GetMapping("/show-contacts/{page}")
	public String showContact(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "User Contacts");

		// send contact list to page
		String userName = principal.getName();
		User user = this.userRepo.getUserByName(userName);
		// current page
		// contact per page
		Pageable pageable = PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactRepo.findContactByUser(user.getId(), pageable);

		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);

		m.addAttribute("totalPages", contacts.getTotalPages());

		return "normal/show_contacts";
	}

	// showing particular contact details
	@GetMapping("/{cId}/contact")
	public String showContactDetails(@PathVariable("cId") Long cId, Model m, Principal principal) {
		Optional<Contact> contacts = this.contactRepo.findById(cId);
		Contact contact = contacts.get();

		//
		String userName = principal.getName();
		User user = this.userRepo.getUserByName(userName);
		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("contact", contact);
		}
		return "normal/contact_detail";
	}

	// delete contact handler
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Long cId, Principal principal, HttpSession httpSession) {

//	Optional<Contact> contacts =	this.contactRepo.findById(cId);

		Contact contact = this.contactRepo.findById(cId).get();
		String userName = principal.getName();
		User user = this.userRepo.getUserByName(userName);

		if (user.getId() == contact.getUser().getId()) {
			this.contactRepo.delete(contact);

			httpSession.setAttribute("message", new Message("Contact has been deleted", "danger"));

		}

		return "redirect:/user/show-contacts/0";

	}

	// update form handler
	@GetMapping("/update-contact/{cId}")
	public String updateForm(@PathVariable("cId") Long cId, Model m) {
		m.addAttribute("title", "Update Cpntact");
		Contact contact = this.contactRepo.findById(cId).get();
		m.addAttribute("contact", contact);
		return "normal/update_form";
	}

	// update contact handler
	@PostMapping("/process-update")
	public String updateHandler(@Valid @ModelAttribute Contact contact, BindingResult bindingResult,
			@RequestParam("image") MultipartFile file, Principal principal, HttpSession session) {

		try {
			// image

			// old contact details
			Contact oldContact = this.contactRepo.findById(contact.getcId()).get();
			if (!file.isEmpty()) {
				// file work
				// rewrite

				// delete old picture
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldContact.getImage());
				file1.delete();

				// update new picture
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());

			} else {
				contact.setImage(oldContact.getImage());
			}
			User user = this.userRepo.getUserByName(principal.getName());
			contact.setUser(user);
			this.contactRepo.save(contact);
			session.setAttribute("message", new Message("Your contact is updated", "success"));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "redirect:/user/" + contact.getcId() + "/contact/";
	}

	// your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model m) {

		m.addAttribute("title", "Profile");

		return "normal/profile";

	}

	// open setting
	@GetMapping("/settings")
	public String openSetting(Model m) {
		m.addAttribute("title","Setting");
		
		return "normal/settings";
	}
	
	//change password handler
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword")String newPassword,Principal principal,HttpSession session) {
		System.out.println(oldPassword);
		System.out.println(newPassword);
		
		
		String user=principal.getName();
	User currentUser =	this.userRepo.getUserByName(user);
	if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
		
		
			
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepo.save(currentUser);
			session.setAttribute("message", new Message("Your password changed successfully", "success"));
			
		
		
		
	} else {
		
		//error
		session.setAttribute("message", new Message("Current password is wrong", "error"));
		return "redirect:/user/settings";
	}
	
	
	
		return "redirect:/logout";
		
	}

}
