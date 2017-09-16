package pl.krakow.uek.invoiceservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krakow.uek.invoiceservice.model.Good;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.model.InvoiceJsonData;
import pl.krakow.uek.invoiceservice.service.PDFGenerationService;
import pl.krakow.uek.invoiceservice.service.StorageService;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public ResponseEntity<?> postPDFInvoice(@RequestBody Invoice invoice, @RequestParam(defaultValue = "false") boolean save) {

        invoice.getGoods().forEach(Good::doCalculations);
        invoice.doCalculations();
        InputStream pdf = pdfGenerationService.createInvoicePDFStream(invoice);

        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceJsonData invoiceJsonData = null;
        int counter = storageService.getAtomicInteger().incrementAndGet();
        if (save) {
            try {
                invoiceJsonData = new InvoiceJsonData(objectMapper.writeValueAsString(invoice), counter);
                storageService.saveInvoice(invoiceJsonData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        byte[] pdfBytes = new byte[0];
        try {
            pdfBytes = IOUtils.toByteArray(pdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageService.saveFile(counter, pdfBytes);

        return ResponseEntity.ok().body(counter);
    }

    @GetMapping(value = "api/keys")
    public ResponseEntity<?> getInvoice(@RequestParam int key) {

        if (!storageService.getInvoicesRepository().exists(key)){
            return ResponseEntity.notFound().build();
        }


        InvoiceJsonData invoiceJsonData = storageService.getInvoice(key);
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

    @GetMapping(value = "files/{key}")
    public ResponseEntity<?> getPDFInvoice(@PathVariable int key) {

        if (!storageService.getPdfFiles().containsKey(key)) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource pdf = new InputStreamResource(new ByteArrayInputStream(storageService.getFile(key)));

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
