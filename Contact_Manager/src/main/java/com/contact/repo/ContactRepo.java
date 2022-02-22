package com.contact.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.entities.Contact;
import com.contact.entities.User;

public interface ContactRepo extends JpaRepository<Contact, Long>{
	
	//pagination
	@Query("from Contact as c where c.user.id =:userId")
	//current page
	//contact per page
	public Page<Contact> findContactByUser(@Param("userId")long userId,Pageable pageable);
	
	//search
	public List<Contact> findByNameContainingAndUser(String name,User user);

}
