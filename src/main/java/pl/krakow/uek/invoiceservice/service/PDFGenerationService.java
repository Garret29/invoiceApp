package pl.krakow.uek.invoiceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.util.PDFGenerator;
import pl.krakow.uek.invoiceservice.util.XMLSerializer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PDFGenerationService {
    @Value("classpath:faktura_0_transform.xsl")
    Resource noVatStyle;
    @Value("classpath:faktura_1_transform.xsl")
    Resource vatStyle;
    private final PDFGenerator pdfGenerator;
    private final XMLSerializer xmlSerializer;
    private List<String> fonts;

    @Autowired
    public PDFGenerationService(PDFGenerator pdfGenerator, XMLSerializer xmlSerializer) {
        this.pdfGenerator = pdfGenerator;
        this.xmlSerializer = xmlSerializer;
        List<String> fontURLs = tryGetFonts();
        this.fonts = new ArrayList<>(fontURLs);
    }

    private List<String> tryGetFonts() {
        List<String> fontURLs;
        try {
            fontURLs = getFonts();
        } catch (IOException e) {
            fontURLs = Collections.emptyList();
        }
        return fontURLs;
    }

    private List<String> getFonts() throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("classpath*:/fonts/*.ttf");
        return Arrays.asList(resources).stream().map(resource -> {
                    try {
                        return resource.getURL().toString();
                    } catch (IOException ignored) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public InputStream createInvoicePDFStream(Invoice invoice) {
        ByteArrayOutputStream xmlOs = new ByteArrayOutputStream();
        xmlSerializer.serialize(xmlOs, invoice);
        ByteArrayInputStream xmlIs;
        InputStream pdfStream = null;
        try {
            xmlIs = new ByteArrayInputStream(xmlOs.toByteArray());

            InputStream inputStream;
            if (invoice.isVatInvoice()) {
                inputStream = vatStyle.getInputStream();
            } else {
                inputStream = noVatStyle.getInputStream();
            }

            pdfStream = pdfGenerator.generatePDF(inputStream, xmlIs, fonts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdfStream;
    }
}
