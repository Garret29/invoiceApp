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
import pl.krakow.uek.invoiceservice.service.StorageService;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;

import java.util.HashMap;

@RequestMapping(value = "/")
@RestController
public class InvoiceGeneratorController {

    private final PDFGenerationService pdfGenerationService;
    private final StorageService storageService;
    private final PDFGenerationProperties pdfGenerationProperties;

    @Autowired
    public InvoiceGeneratorController(PDFGenerationService pdfGenerationService, PDFGenerationProperties pdfGenerationProperties, HashMap<Integer, InputStreamResource> pdfs, StorageService storageService) {
        this.pdfGenerationService = pdfGenerationService;
        this.pdfGenerationProperties = pdfGenerationProperties;
        this.storageService = storageService;
    }


    @PostMapping(value = "api")
    public ResponseEntity<?> postPDFInvoice(@RequestBody Invoice invoice) {

        invoice.getGoods().forEach(Good::doCalculations);
        invoice.doCalculations();
        InputStreamResource pdf = pdfGenerationService.createInvoicePDFStream(invoice);

        storageService.saveFile(pdf.hashCode(), pdf);

        return ResponseEntity.ok().body(pdf.hashCode());
    }

    @GetMapping(value = "/files/{key}")
    public ResponseEntity<InputStreamResource> getPDFInvoice(@PathVariable int key) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename="+ key + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(storageService.getFile(key));
    }
}
