package pl.krakow.uek.invoiceservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class StorageServiceConfig {
    @Bean
    public HashMap getHashMap(){
        return new HashMap<>();
    }
}
