package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;
import pl.krakow.uek.invoiceservice.util.PDFGenerator;
import pl.krakow.uek.invoiceservice.util.XMLSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class PDFGenerationService {
    private final PDFGenerationProperties pdfGenerationProperties;
    private File cacheDir;
    @Value("classpath:faktury_style.xsl")
    Resource style;
    private final PDFGenerator pdfGenerator;
    private final XMLSerializer xmlSerializer;
    private final LinkedList<String> fonts;

    @Autowired
    public PDFGenerationService(PDFGenerator pdfGenerator, PDFGenerationProperties pdfGenerationProperties, XMLSerializer xmlSerializer, LinkedList<String> fonts) {
        this.pdfGenerator = pdfGenerator;
        this.pdfGenerationProperties = pdfGenerationProperties;
        this.xmlSerializer = xmlSerializer;
        this.fonts = fonts;

        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = null;
        try {
            resources = resolver.getResources("classpath*:/fonts/*.ttf");
            List<Resource> resourcesList = Arrays.asList(resources);
            resourcesList.forEach(resource -> {
                try {
                    fonts.add(resource.getURL().toString());
                    fonts.forEach(System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
    public File createInvoicePDFFile(Invoice invoice) {
        cacheDir = new File(pdfGenerationProperties.getCacheDirPath());

        File xml = new File(cacheDir, invoice.getId() + ".xml");
        xmlSerializer.serialize(xml, invoice);
        File pdf = null;
        try {
            pdf = pdfGenerator.generatePDF(style.getFile(), xml, cacheDir, invoice.getId(), cacheDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdf;
    }
    */

    public InputStream createInvoicePDFStream(Invoice invoice) {
        ByteArrayOutputStream xmlOs = new ByteArrayOutputStream();
        xmlSerializer.serialize(xmlOs, invoice);
        ByteArrayInputStream xmlIs;
        InputStream pdfStream = null;
        try {
            xmlIs = new ByteArrayInputStream(xmlOs.toByteArray());
            pdfStream = pdfGenerator.generatePDF(style.getInputStream(), xmlIs, fonts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdfStream;
    }
}
