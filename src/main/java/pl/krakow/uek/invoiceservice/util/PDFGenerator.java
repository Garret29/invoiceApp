package pl.krakow.uek.invoiceservice.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.tidy.Tidy;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

@Component
@Scope("prototype")
public class PDFGenerator {
    public InputStream generatePDF(InputStream xsl, InputStream xml, List<String> fonts) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        ByteArrayOutputStream pdfOs=null;

        try {
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsl));
            ByteArrayOutputStream htmlOs = new ByteArrayOutputStream();
            ByteArrayOutputStream html2Os = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult(htmlOs);

            transformer.transform(new StreamSource(xml), streamResult);
            htmlOs.close();

            InputStream htmlIs = new ByteArrayInputStream(htmlOs.toByteArray());

            Tidy tidy = new Tidy();
            tidy.setInputEncoding("UTF-8");
            tidy.setXHTML(true);
            tidy.parseDOM(htmlIs, html2Os);
            htmlIs.close();
            htmlIs = new ByteArrayInputStream(html2Os.toByteArray());
            pdfOs = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfOs);
            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fonts.forEach(fontProvider::register);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, htmlIs, Charset.forName("UTF-8"), fontProvider);
            document.close();
            pdfOs.close();
            htmlIs.close();

        } catch (TransformerException | IOException | DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(pdfOs.toByteArray());
    }
}
