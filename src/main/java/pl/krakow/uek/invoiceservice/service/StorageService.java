package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.InvoiceJsonData;
import pl.krakow.uek.invoiceservice.repository.InvoicesRepository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StorageService {
    private final HashMap<Integer, InputStreamResource> pdfFiles;
    private final InvoicesRepository invoicesRepository;
    private AtomicInteger atomicInteger;

    @Autowired
    public StorageService(HashMap<Integer, InputStreamResource> pdfFiles, InvoicesRepository invoicesRepository, AtomicInteger atomicInteger) {
        this.pdfFiles = pdfFiles;
        this.invoicesRepository = invoicesRepository;
        this.atomicInteger = atomicInteger;
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

    public void saveInvoice(InvoiceJsonData invoice){
        invoicesRepository.save(invoice);
    }

    public void deleteInvoice(int key){
        invoicesRepository.delete(key);
    }

    public InvoiceJsonData getInvoice(int key){
        return invoicesRepository.findById(key);
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public HashMap<Integer, InputStreamResource> getPdfFiles() {
        return pdfFiles;
    }

    public InvoicesRepository getInvoicesRepository() {
        return invoicesRepository;
    }
}
