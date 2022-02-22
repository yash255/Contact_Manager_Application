package com.contact.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.repo.ContactRepo;
import com.contact.repo.UserRepo;

@RestController
public class SearchController {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ContactRepo contactRepo;
	
	//search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal){
		
		System.out.println(query);
User user	=	this.userRepo.getUserByName(principal.getName());
List<Contact> contacts = this.contactRepo.findByNameContainingAndUser(query, user);
return ResponseEntity.ok(contacts);
	}

}
