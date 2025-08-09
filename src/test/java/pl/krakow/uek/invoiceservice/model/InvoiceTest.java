package pl.krakow.uek.invoiceservice.model;

import junit.framework.TestCase;

import java.util.Arrays;

public class InvoiceTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setGoods(Arrays.asList(new Good()));
    }

    public void testAddGood() {

    }

    public void testDoCalculations() {

    }
}