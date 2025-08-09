package pl.krakow.uek.invoiceservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.Invoice;

import java.io.IOException;
import java.io.InputStream;

@Service
public class InvoiceService {
    private final PDFGenerationService pdfGenerationService;

    @Autowired
    public InvoiceService(PDFGenerationService pdfGenerationService, ObjectMapper objectMapper) {
        this.pdfGenerationService = pdfGenerationService;
    }

    public byte[] convertToPdf(Invoice invoice) throws IOException {
        invoice.calculate();
        InputStream pdf = pdfGenerationService.createInvoicePDFStream(invoice);
        return IOUtils.toByteArray(pdf);
    }
}
