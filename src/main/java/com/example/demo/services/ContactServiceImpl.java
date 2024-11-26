package com.example.demo.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.models.entity.Contact;

@Service
public class ContactServiceImpl implements ContactService {

	public static final Logger LOGGER = LogManager.getLogger(ContactServiceImpl.class);
	
    @Autowired
    private JavaMailSender mailSender;

	@Override
	public void saveContact(Contact contact) {
        sendEmail(contact);
	}

	@Override
	public void sendEmail(Contact contact) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(contact.getEmail());
	        message.setSubject("Presupuesto Estimado");
	        
	        String total = contact.getMessages().get("total");
	        String metros = contact.getMessages().get("metros");
	        String wc = contact.getMessages().get("wc");
	        String habitacion = contact.getMessages().get("habitacion");
	        String cocina = contact.getMessages().get("cocina");
	        String salon = contact.getMessages().get("salon");
	        String vivienda = contact.getMessages().get("vivienda");
	        String calidad = contact.getMessages().get("calidad");
	        String reforma = contact.getMessages().get("reforma");
	        String mensajeCorreo = """
	        		Nombre solicitante: %s 
	        		Correo Electronico:  %s 
	        		Teléfono:  %s 
	        		Presupuesto Estimado es de %s euros
	        		Por haber seleccionado
	        			- m2 de la vivienda: %s
	        			- N° de baños: %s
	        			- N° de habitaciones: %s
	        			- Cocina: %s
	        			- Salón: %s
	        			- Calidad: %s
	        			- Tipo de vivienda: %s
	        			- Tipo de Reforma: %s
	        		""".formatted(contact.getFirstName()+" "+ contact.getLastName(), contact.getEmail(), contact.getPhoneNumber() , total, metros, wc, habitacion, cocina, salon, vivienda, calidad,reforma);
	        message.setText(mensajeCorreo);
	        mailSender.send(message);	
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		
		LOGGER.info("email sent");
	}
}
