package pl.krakow.uek.invoiceservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ServiceConfig {
    @Bean
    @Scope("prototype")
    public HashMap getHashMap(){
        return new HashMap<>();
    }

    @Bean
    @Scope("prototype")
    public LinkedList getLinkedList(){return new LinkedList();}

    @Bean
    public AtomicInteger getAtomicInteger(){
        return new AtomicInteger(0);
    }
}
