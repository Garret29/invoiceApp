package pl.krakow.uek.invoiceservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;

@Configuration
public class StorageServiceConfig {
    @Bean
    @Scope("prototype")
    public HashMap getHashMap(){
        return new HashMap<>();
    }
}
