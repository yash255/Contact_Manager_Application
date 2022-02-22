package com.contact.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank(message = "Name field is Required !!")
	@Size(min = 1,max = 30,message = "minimum 1 and maximum 30 characters are allowed !!")
	
	private String name;
	@NotBlank(message = "Email field is Required")
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	@Column(unique = true)
	private String email;
	@NotBlank
	@Pattern(regexp = "^[a-A0-9!@#$%^&*()_+=-][{}|")
	@Size(min = 8,max = 20,message = "minimum 8 and maximum 20 characters are required")
	private String password;
	private String role;
	private boolean active;
	private String imageUrl;
	@Column(length = 1000)
	private String about;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user",orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(int id, String name, String email, String password, String role, boolean active, String imageUrl,
			String about) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.active = active;
		this.imageUrl = imageUrl;
		this.about = about;
	}


	


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}


	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}


	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", active=" + active + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts + "]";
	}
	
	
	
	
	
	
	

}
