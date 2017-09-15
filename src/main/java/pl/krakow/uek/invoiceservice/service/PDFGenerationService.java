package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;
import pl.krakow.uek.invoiceservice.util.PDFGenerator;
import pl.krakow.uek.invoiceservice.util.XMLSerializer;

import java.io.*;

@Service
public class PDFGenerationService{
    private final PDFGenerationProperties pdfGenerationProperties;
    private File cacheDir;
    @Value("classpath:faktury_style.xsl")
    Resource style;
    private final PDFGenerator pdfGenerator;
    private final XMLSerializer xmlSerializer;

    @Autowired
    public PDFGenerationService(PDFGenerator pdfGenerator, PDFGenerationProperties pdfGenerationProperties, XMLSerializer xmlSerializer) {
        this.pdfGenerator = pdfGenerator;
        this.pdfGenerationProperties = pdfGenerationProperties;
        this.xmlSerializer = xmlSerializer;
    }


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

    public InputStreamResource createInvoicePDFStream(Invoice invoice){
        ByteArrayOutputStream xmlOs = new ByteArrayOutputStream();
        xmlSerializer.serialize(xmlOs, invoice);
        ByteArrayInputStream xmlIs;
        InputStreamResource pdfStream=null;
        try {
            xmlIs = new ByteArrayInputStream(xmlOs.toByteArray());
            pdfStream = pdfGenerator.generatePDF(style.getInputStream(), xmlIs, invoice.getId()+".pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdfStream;
    }
}
