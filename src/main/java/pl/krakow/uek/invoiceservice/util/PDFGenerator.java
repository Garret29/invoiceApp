package pl.krakow.uek.invoiceservice.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
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

@Component
@Scope("prototype")
public class PDFGenerator {
    public File generatePDF(File xsl, File xml, File dir, String filename, File cacheDir) {

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
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, htmlIs, Charset.forName("UTF-8"));
            document.close();
            os.close();
            htmlIs.close();
        } catch (DocumentException | IOException | TransformerException e) {
            e.printStackTrace();
        }

        return pdf;
    }
}
