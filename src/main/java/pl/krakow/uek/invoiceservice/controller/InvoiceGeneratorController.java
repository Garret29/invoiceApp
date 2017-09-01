package pl.krakow.uek.invoiceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.service.PDFGenerationService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RequestMapping(value = "/")
@RestController
public class InvoiceGeneratorController {

    private final PDFGenerationService pdfGenerationService;

    @Autowired
    public InvoiceGeneratorController(PDFGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }


    @GetMapping(value = "api")
    public ResponseEntity<InputStreamResource> getPDFInvoice(@RequestBody Invoice invoice) throws FileNotFoundException {

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
