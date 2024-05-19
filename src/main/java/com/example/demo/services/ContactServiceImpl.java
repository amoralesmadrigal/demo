package com.example.demo.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.models.entity.Contact;
import com.example.demo.models.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	public static final Logger LOGGER = LogManager.getLogger(ContactServiceImpl.class);
	
	@Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

	@Override
	public void saveContact(Contact contact) {
		contactRepository.save(contact);
        sendEmail(contact);
		
	}

	@Override
	public void sendEmail(Contact contact) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(contact.getEmail());
	        message.setSubject("Mensaje de Prueba");
	        message.setText("Name: " + contact.getFirstName() + " " + contact.getLastName() + "\nEmail: " + contact.getEmail() + "\nPhone: " + contact.getPhoneNumber());
	        mailSender.send(message);	
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		
		
	}
}
