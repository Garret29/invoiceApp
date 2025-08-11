package pl.krakow.uek.invoiceservice;


import org.junit.jupiter.api.Test;
import pl.krakow.uek.invoiceservice.model.Good;
import pl.krakow.uek.invoiceservice.model.Invoice;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceTest {

    private final double DELTA = 0.001;

    @Test
    public void shouldCorrectlyCalculateInvoiceTotals() {
        Good good1 = Good.builder()
                .name("Testowy Produkt 1")
                .quantity(1)
                .unit("sztuk")
                .priceNetOfUnit(100.00)
                .discount(0)
                .vatRate(23)
                .build();

        Good good2 = Good.builder()
                .name("Testowy Produkt 2")
                .quantity(2)
                .unit("sztuk")
                .priceNetOfUnit(50.00)
                .discount(0)
                .vatRate(8)
                .build();

        Invoice invoice = Invoice.builder()
                .id("FV-1/25")
                .invoiceCity("Kraków")
                .invoiceDate("2025-08-10")
                .goods(Arrays.asList(good1, good2))
                .build();

        invoice.calculate();

        assertEquals(200.00, invoice.getNetto(), DELTA);
        assertEquals(8.00, invoice.getVat_8(), DELTA);
        assertEquals(23.00, invoice.getVat_23(), DELTA);
        assertEquals(31.00, invoice.getVat(), DELTA);
        assertEquals(123.00, invoice.getGross_23(), DELTA);
        assertEquals(108.00, invoice.getGross_8(), DELTA);
        assertEquals(231.00, invoice.getGross(), DELTA);
    }

    @Test
    public void shouldCorrectlyCalculateValuesForVat23() {
        Good good = Good.builder()
                .name("Testowy produkt")
                .quantity(2)
                .unit("sztuk")
                .priceNetOfUnit(10.00)
                .discount(10.0)
                .vatRate(23)
                .build();

        good.calculate();

        assertEquals(9.00, good.getPriceNetOfUnitAfterDiscount(), DELTA);
        assertEquals(18.00, good.getPriceNetto(), DELTA);
        assertEquals(4.14, good.getVat(), DELTA);
        assertEquals(22.14, good.getPriceGross(), DELTA);
    }

    @Test
    public void shouldCorrectlyCalculateValuesForVat8() {
        Good good = Good.builder()
                .name("Testowy produkt")
                .quantity(2)
                .unit("sztuk")
                .priceNetOfUnit(10.00)
                .discount(10.0)
                .vatRate(8)
                .build();

        good.calculate();

        assertEquals(9.00, good.getPriceNetOfUnitAfterDiscount(), DELTA);
        assertEquals(18.00, good.getPriceNetto(), DELTA);
        assertEquals(1.44, good.getVat(), DELTA);
        assertEquals(19.44, good.getPriceGross(), DELTA);
    }
}
