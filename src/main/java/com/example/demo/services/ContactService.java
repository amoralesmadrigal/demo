package com.example.demo.services;

import com.example.demo.models.entity.Contact;

public interface ContactService {

	public void saveContact(Contact contact);
	
	public void sendEmail(Contact contact);
}
