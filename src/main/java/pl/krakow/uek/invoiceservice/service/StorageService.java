package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StorageService {
    private final HashMap<Integer, InputStreamResource> pdfFiles;

    @Autowired
    public StorageService(HashMap<Integer, InputStreamResource> pdfFiles) {
        this.pdfFiles = pdfFiles;
    }

    public void deleteFile(int key){
        pdfFiles.remove(key);
    }

    public void saveFile(int key, InputStreamResource file){
        if (!pdfFiles.containsKey(key)){
            pdfFiles.put(key,file);
        }
    }

    public InputStreamResource getFile(int key){
        return pdfFiles.get(key);
    }
}
