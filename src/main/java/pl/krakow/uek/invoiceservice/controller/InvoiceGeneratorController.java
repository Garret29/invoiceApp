package pl.krakow.uek.invoiceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krakow.uek.invoiceservice.model.Good;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.service.PDFGenerationService;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RequestMapping(value = "/")
@RestController
public class InvoiceGeneratorController {

    private final PDFGenerationService pdfGenerationService;
    private final PDFGenerationProperties pdfGenerationProperties;

    @Autowired
    public InvoiceGeneratorController(PDFGenerationService pdfGenerationService, PDFGenerationProperties pdfGenerationProperties) {
        this.pdfGenerationService = pdfGenerationService;
        this.pdfGenerationProperties = pdfGenerationProperties;
    }


    @PostMapping(value = "api")
    public ResponseEntity<InputStreamResource> getPDFInvoice(@RequestBody Invoice invoice) throws FileNotFoundException {

        invoice.getGoods().forEach(Good::doCalculations);
        invoice.doCalculations();
        File pdf = pdfGenerationService.createInvoicePDF(invoice);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new FileInputStream(pdf)));
    }
}
