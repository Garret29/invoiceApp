package pl.krakow.uek.invoiceservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pl.krakow.uek.invoiceservice.service.properties.PDFGenerationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PDFGenerationProperties.class)
public class InvoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PDFGenerationProperties pdfGenerationProperties) {
        return args -> {

        };
    }
}
