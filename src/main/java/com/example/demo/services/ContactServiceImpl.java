package com.example.demo.services;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.models.entity.Contact;
import com.example.demo.utils.PdfGenerator;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class ContactServiceImpl implements ContactService {

	public static final Logger LOGGER = LogManager.getLogger(ContactServiceImpl.class);
	
    @Autowired
    private JavaMailSender mailSender;

	@Override
	public void sendEmail(Contact contact) {
		senderEmail(contact);
	}

	@Async
	private void senderEmail(Contact contact) {
		try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       
	        String total = contact.getMessages().get("total");
	        String metros = contact.getMessages().get("metros");
	        String wc = contact.getMessages().get("wc");
	        String habitacion = contact.getMessages().get("habitacion");
	        String cocina = contact.getMessages().get("cocina");
	        String salon = contact.getMessages().get("salon");
	        String vivienda = contact.getMessages().get("vivienda");
	        String calidad = contact.getMessages().get("calidad");
	        String reforma = contact.getMessages().get("reforma");
	        String mensajeCorreoHtml = """
	        		<!DOCTYPE html>
	        		<html lang="es">
	        		<head>
	        		    <meta charset="UTF-8">
	        		    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	        		    <style>
	        		        body {
	        		            font-family: Arial, sans-serif;
	        		            line-height: 1.6;
	        		            color: #333;
	        		        }
	        		        h2 {
	        		            color: #2c3e50;
	        		        }
	        		        ul {
	        		            list-style-type: none;
	        		            padding: 0;
	        		        }
	        		        li {
	        		            background: #f8f9fa;
	        		            margin: 5px 0;
	        		            padding: 10px;
	        		            border-radius: 5px;
	        		            border: 1px solid #ddd;
	        		        }
	        		        strong {
	        		            color: #2c3e50;
	        		        }
	        		    </style>
	        		</head>
	        		<body>
	        		    <h2>Detalles de la Solicitud</h2>
	        		    <p><strong>Nombre solicitante:</strong> %s</p>
	        		    <p><strong>Correo Electr&oacute;nico:</strong> %s</p>
	        		    <p><strong>Tel&eacute;fono:</strong> %s</p>
	        		    <p><strong>Presupuesto Estimado:</strong> %s euros</p>
	        		    <h2>Selecciones</h2>
	        		    <ul>
	        		        <li><strong>m&sup2; de la vivienda:</strong> %s</li>
	        		        <li><strong>N&deg; de ba&ntilde;os:</strong> %s</li>
	        		        <li><strong>N&deg; de habitaciones:</strong> %s</li>
	        		        <li><strong>Cocina:</strong> %s</li>
	        		        <li><strong>Sal&oacute;n:</strong> %s</li>
	        		        <li><strong>C&aacute;lidad:</strong> %s</li>
	        		        <li><strong>Tipo de vivienda:</strong> %s</li>
	        		        <li><strong>Tipo de Reforma:</strong> %s</li>
	        		    </ul>
	        		</body>
	        		</html>
	        		""".formatted(
	        		    StringEscapeUtils.escapeHtml4(contact.getFirstName()) + " " + StringEscapeUtils.escapeHtml4(contact.getLastName()),
	        		    contact.getEmail(),
	        		    contact.getPhoneNumber(),
	        		    total,
	        		    metros,
	        		    wc,
	        		    StringEscapeUtils.escapeHtml4(habitacion),
	        		    StringEscapeUtils.escapeHtml4(cocina),
	        		    StringEscapeUtils.escapeHtml4(salon),
	        		    StringEscapeUtils.escapeHtml4(calidad),
	        		    vivienda,
	        		    reforma
	        		);
	        helper.setText(mensajeCorreoHtml, true);
	        helper.setTo(contact.getEmail());
	        helper.setSubject("Presupuesto Estimado");
	        helper.setFrom("parapininos@gmail.com", "MAR TODO EN UNA REFORMA S.L.");
	        byte[] pdfBytes = PdfGenerator.generatePdfInMemory(mensajeCorreoHtml);
	        helper.addAttachment("presupuesto.pdf", new ByteArrayDataSource(pdfBytes, "application/pdf"));

	        mailSender.send(message);
	        message = null;
	        helper = null;
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		
		LOGGER.info("email sent");
	}
}
