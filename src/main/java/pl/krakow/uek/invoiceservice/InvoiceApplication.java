package pl.krakow.uek.invoiceservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pl.krakow.uek.invoiceservice.model.Buyer;
import pl.krakow.uek.invoiceservice.model.Good;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.model.Provider;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;

import java.io.File;
import java.util.ArrayList;

@SpringBootApplication
@EnableConfigurationProperties(PDFGenerationProperties.class)
public class InvoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PDFGenerationProperties pdfGenerationProperties) {
        return args -> {
            File cacheDir = new File(pdfGenerationProperties.getCacheDirPath());
            cacheDir.mkdirs();

            Invoice invoice = new Invoice(
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    new Provider(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
                    new Buyer(" ", " ", " ", " ", " ", " "),
                    new ArrayList<>(),
                    0.0,
                    0.0,
                    0.0,
                    " ",
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

            invoice.addGood(new Good(" ", 0.0, " ", 0.0, 0.0, 0));

            ObjectMapper objectMapper = new ObjectMapper();
            File xml = new File(cacheDir, "xmlToExport.json");
            objectMapper.writeValue(xml, invoice);

        };
    }
}
