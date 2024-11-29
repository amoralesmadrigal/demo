package com.example.demo.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

	@Value("${email.username}")
	private String email;

	@Value("${email.password}")
	private String password;

	private Properties getConfigPropertes() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 587);
		props.put("mail.protocol", "smtp");
		props.put("mail.properties.mail.smtp.auth", true);
		props.put("mail.properties.mail.smtp.starttls.enable", true);
		props.put("mail.properties.mail.smtp.connectiontimeout", 5000);
		props.put("mail.properties.mail.smtp.timeout", 3000);
		props.put("mail.properties.mail.smtp.writetimeout", 3000);
		props.put("mail.properties.mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.properties.mail.smtp.pool", true);
		props.put("mail.properties.mail.smtp.pool.size", 5);
		return props;
	}

	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setJavaMailProperties(getConfigPropertes());
		sender.setUsername(email);
		sender.setPassword(password);
		return sender;
	}
}
