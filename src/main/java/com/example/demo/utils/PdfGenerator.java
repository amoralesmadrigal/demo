package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import com.itextpdf.html2pdf.HtmlConverter;

public class PdfGenerator {

	public static String generatePdfFromHtml(String htmlContent, String outputPath) {
        try {
            // Crear el PDF a partir del HTML
            HtmlConverter.convertToPdf(htmlContent, new FileOutputStream(outputPath));
            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static byte[] generatePdfInMemory(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
            return outputStream.toByteArray(); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
