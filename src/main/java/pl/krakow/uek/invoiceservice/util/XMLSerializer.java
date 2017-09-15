package pl.krakow.uek.invoiceservice.util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

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

    public void serialize(ByteArrayOutputStream xmlOutputStream, Object o) {
        Serializer serializer = new Persister();

        try {
            serializer.write(o, xmlOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
