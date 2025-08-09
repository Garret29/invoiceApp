package pl.krakow.uek.invoiceservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.Good;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.model.InvoiceJsonData;
import pl.krakow.uek.invoiceservice.repository.InvoicesRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceService {
    private final Map<Integer, byte[]> pdfFilesCache;
    private final InvoicesRepository invoicesRepository;
    private final ObjectMapper objectMapper;
    private final AtomicInteger atomicInteger;
    private final PDFGenerationService pdfGenerationService;

    @Autowired
    public InvoiceService(InvoicesRepository invoicesRepository, PDFGenerationService pdfGenerationService, ObjectMapper objectMapper) {
        this.pdfGenerationService = pdfGenerationService;
        this.objectMapper = objectMapper;
        this.pdfFilesCache = new HashMap<>();
        this.invoicesRepository = invoicesRepository;
        this.atomicInteger = new AtomicInteger(0);
    }

    public void deleteFile(int key) {
        pdfFilesCache.remove(key);
    }

    public void saveFile(int key, byte[] file) {
        pdfFilesCache.put(key, file);
    }

    public byte[] getFile(int key) {
        return pdfFilesCache.get(key);
    }

    public int saveInvoice(Invoice invoice) throws IOException {
        invoice.getGoods().forEach(Good::doCalculations);
        invoice.doCalculations();
        InputStream pdf = pdfGenerationService.createInvoicePDFStream(invoice);
        int key = atomicInteger.incrementAndGet();
        byte[] pdfBytes = IOUtils.toByteArray(pdf);
        saveFile(key, pdfBytes);

        return key;
    }

    public void saveData(int key, Invoice invoice) throws JsonProcessingException {
        InvoiceJsonData invoiceJsonData = new InvoiceJsonData(objectMapper.writeValueAsString(invoice), key);
        invoicesRepository.save(invoiceJsonData);
    }

    public InvoiceJsonData getInvoice(int key) {
        return invoicesRepository.findById(key);
    }
}
