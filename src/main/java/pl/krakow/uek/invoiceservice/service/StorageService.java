package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.InvoiceJsonData;
import pl.krakow.uek.invoiceservice.repository.InvoicesRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StorageService {
    private final HashMap<Integer, byte[]> pdfFiles;
    private final InvoicesRepository invoicesRepository;
    private AtomicInteger atomicInteger;

    @Autowired
    public StorageService(HashMap<Integer, byte[]> pdfFiles, InvoicesRepository invoicesRepository, AtomicInteger atomicInteger) {
        this.pdfFiles = pdfFiles;
        this.invoicesRepository = invoicesRepository;
        this.atomicInteger = atomicInteger;
    }

    public void deleteFile(int key){
        pdfFiles.remove(key);
    }

    public void saveFile(int key, byte[] file){
            pdfFiles.put(key,file);
    }

    public byte[] getFile(int key){
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

    public HashMap<Integer, byte[]> getPdfFiles() {
        return pdfFiles;
    }

    public InvoicesRepository getInvoicesRepository() {
        return invoicesRepository;
    }
}
