package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.entity.Contact;
import com.example.demo.services.ContactService;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

	@Autowired
    private ContactService contactService;
	
	@GetMapping
	public ResponseEntity<?> helloWorld() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

    @PostMapping("/create")
    public void createContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
    }
}
