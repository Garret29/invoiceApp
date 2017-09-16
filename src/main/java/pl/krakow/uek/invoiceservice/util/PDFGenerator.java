package pl.krakow.uek.invoiceservice.util;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
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
    public File generatePDF(File xsl, File xml, File dir, String filename, File cacheDir, List<String> fonts) {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        File html = new File(cacheDir, "invoice.html");
        File pdf = new File(dir, filename + ".pdf");

        try {
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsl));
            OutputStream htmlOs = new FileOutputStream(html);
            OutputStream html2Os = new FileOutputStream(new File(cacheDir, "invoice2.html"));
            InputStream htmlIs = new FileInputStream(html);

            transformer.transform(new StreamSource(xml), new StreamResult(htmlOs));
            htmlOs.close();

            Tidy tidy = new Tidy();
            tidy.setInputEncoding("UTF-8");
            tidy.setXHTML(true);
            tidy.parseDOM(htmlIs, html2Os);
            html2Os.close();

            htmlIs.close();
            html = new File(cacheDir, "invoice2.html");
            htmlIs = new FileInputStream(html);
            OutputStream os = new FileOutputStream(pdf);
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
            if (!fonts.isEmpty()) {
                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
                fonts.forEach(fontProvider::register);
                document.open();
                XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, htmlIs, Charset.forName("UTF-8"), fontProvider);
                document.close();
                os.close();
                htmlIs.close();
            } else {
                document.open();
                XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, htmlIs, Charset.forName("UTF-8"));
                document.close();
                os.close();
                htmlIs.close();
            }
        } catch (DocumentException | IOException | TransformerException e) {
            e.printStackTrace();
        }

        return pdf;
    }

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
