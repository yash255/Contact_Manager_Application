package com.contact.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.servlet.annotation.MultipartConfig;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contact")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cId;
	@NotBlank(message = "Name field is Required !!")
	private String name;
	private String nickName;
	private String work;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	@NotBlank
	@Pattern(regexp = "^[0-1010-0]")
	private Long phone;
	
	private String image;
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	
	
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Contact(long cId, String name, String nickName, String work, String email, Long phone, String image,
			String description) {
		super();
		this.cId = cId;
		this.name = name;
		this.nickName = nickName;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.description = description;
	}


	public long getcId() {
		return cId;
	}
	public void setcId(long cId) {
		this.cId = cId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}


	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


	
	
	
	
	
	

}
