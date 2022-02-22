package com.contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)  
public class ContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactManagerApplication.class, args);
	}

}
