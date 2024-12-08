package com.example.demo.config;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.services.ContactServiceImpl;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(2) // Ejecutar primero
public class OriginValidationFilter implements Filter {
	public static final Logger LOGGER = LogManager.getLogger(ContactServiceImpl.class);
	@Value("${allowed.origin}")
    private String allowedOrigin;

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String origin = httpRequest.getHeader("Origin");
        LOGGER.info("origin", origin);
        if (origin == null || origin != null && !origin.equals(allowedOrigin)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Forbidden: Invalid Origin");
            return;
        }

        chain.doFilter(request, response); // Continúa con la cadena
    }
}