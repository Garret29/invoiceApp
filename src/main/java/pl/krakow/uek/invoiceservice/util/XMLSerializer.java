package pl.krakow.uek.invoiceservice.util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class XMLSerializer {

    public void serialize(File xml, Object o) {
        Serializer serializer = new Persister();
        try {
            serializer.write(o, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
