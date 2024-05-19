package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.entity.Contact;
import com.example.demo.services.ContactService;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

	@Autowired
    private ContactService contactService;
	
	@GetMapping
	public void helloWorld() {
		System.out.println("Hello World");
	}

    @PostMapping("/create")
    public void createContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
    }
}
