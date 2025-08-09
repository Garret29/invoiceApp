package pl.krakow.uek.invoiceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.model.InvoiceJsonData;
import pl.krakow.uek.invoiceservice.service.InvoiceService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class InvoiceGeneratorController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceGeneratorController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @PostMapping(value = "/api")
    public ResponseEntity<?> postPDFInvoice(@RequestBody Invoice invoice, @RequestParam(defaultValue = "false") boolean save) {
        int key;
        try {
            key = invoiceService.saveInvoice(invoice);
            if (save) {
                invoiceService.saveData(key, invoice);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(key);
    }

    @GetMapping(value = "/api/keys")
    public ResponseEntity<?> getInvoice(@RequestParam int key) {


        InvoiceJsonData invoiceJsonData = invoiceService.getInvoice(key);
        ObjectMapper mapper = new ObjectMapper();
        Invoice invoice = null;
        try {
            invoice = mapper.readValue(invoiceJsonData.getData(), Invoice.class);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity
                .ok()
                .body(invoice);
    }

    @GetMapping(value = "/files/{key}")
    public ResponseEntity<?> getPDFInvoice(@PathVariable int key) {

        byte[] file = invoiceService.getFile(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        InputStreamResource pdf = new InputStreamResource(new ByteArrayInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + key + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
