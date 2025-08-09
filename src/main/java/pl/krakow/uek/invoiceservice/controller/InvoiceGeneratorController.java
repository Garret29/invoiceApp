package pl.krakow.uek.invoiceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.service.InvoiceService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceGeneratorController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceGeneratorController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> postPDFInvoice(@RequestBody Invoice invoice) {
        try {
            byte[] file = invoiceService.convertToPdf(invoice);
            if (file == null) {
                return ResponseEntity.notFound().build();
            }
            InputStreamResource pdf = new InputStreamResource(new ByteArrayInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Disposition", "attachment; filename=" + invoice.getId() + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
