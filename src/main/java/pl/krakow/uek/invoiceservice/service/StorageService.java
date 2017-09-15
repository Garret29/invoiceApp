package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.Invoice;

import java.util.HashMap;

@Service
public class StorageService {
    private final HashMap<Integer, InputStreamResource> pdfFiles;
    private final HashMap<Integer, Invoice> invoices;

    @Autowired
    public StorageService(HashMap<Integer, InputStreamResource> pdfFiles, HashMap<Integer, Invoice> invoices) {
        this.pdfFiles = pdfFiles;
        this.invoices = invoices;
    }

    public void deleteFile(int key){
        pdfFiles.remove(key);
    }

    public void saveFile(int key, InputStreamResource file){
            pdfFiles.put(key,file);
    }

    public InputStreamResource getFile(int key){
        return pdfFiles.get(key);
    }

    public void addInvoice(int key, Invoice invoice){
        invoices.put(key, invoice);
    }

    public void deleteInvoice(int key){
        invoices.remove(key);
    }

    public Invoice getInvoice(int key){
        return invoices.get(key);
    }
}
