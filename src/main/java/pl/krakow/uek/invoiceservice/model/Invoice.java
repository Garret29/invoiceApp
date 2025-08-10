package pl.krakow.uek.invoiceservice.model;

import lombok.*;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Root(name = "faktura")
public class Invoice implements Serializable, Calculable {
    @Element(name = "miejscowosc_wystawienia")
    private String invoiceCity;
    @Element(name = "data_wystawienia")
    private String invoiceDate;
    @Element(name = "data_dostawy")
    private String invoiceShippingDate;
    @Element(name = "termin_platnosci")
    private String paymentDate;
    @Element(name = "sposob_platnosci")
    private String paymentMethod;

    @Element(name = "id")
    private String id;

    @Element(name = "dostawca")
    private Provider provider;

    @Element(name = "nabywca")
    private Buyer buyer;

    @ElementList(name = "towary")
    private List<Good> goods;

    @Element(name = "gross")
    private double gross;
    @Element(name = "VAT")
    private double vat;
    @Element(name = "netto")
    private double netto;
    @Element(name = "brutto_slownie")
    private String bruttoWords;
    @Element(name = "vat_0")
    private double vat_0;
    @Element(name = "vat_5")
    private double vat_5;
    @Element(name = "vat_8")
    private double vat_8;
    @Element(name = "vat_23")
    private double vat_23;

    @Element(name = "netto_0")
    private double net_0;
    @Element(name = "netto_5")
    private double net_5;
    @Element(name = "netto_8")
    private double net_8;
    @Element(name = "netto_23")
    private double net_23;

    @Element(name = "gross_0")
    private double gross_0;
    @Element(name = "gross_5")
    private double gross_5;
    @Element(name = "gross_8")
    private double gross_8;
    @Element(name = "gross_23")
    private double gross_23;

    @Override
    public void calculate() {
        goods.forEach(good -> {
            good.calculate();
            gross += good.getPriceGross();
            netto += good.getPriceNetto();

            switch (good.getVatRate()) {
                case 0: {
                    vat_0 += good.getVat();
                    net_0 += good.getPriceNetto();
                    break;
                }
                case 5: {
                    vat_5 += good.getVat();
                    net_5 += good.getPriceNetto();
                    break;
                }
                case 8: {
                    vat_8 += good.getVat();
                    net_8 += good.getPriceNetto();
                    break;
                }
                case 23: {
                    vat_23 += good.getVat();
                    net_23 += good.getPriceNetto();
                    break;
                }
            }
        });

        vat = vat_0 + vat_5 + vat_8 + vat_23;
        gross_0 = net_0 + vat_0;
        gross_5 = net_5 + vat_5;
        gross_8 = net_8 + vat_8;
        gross_23 = net_23 + vat_23;

        gross = Math.round(gross * 100.0) / 100.0;
        gross_0 = Math.round(gross_0 * 100.0) / 100.0;
        gross_5 = Math.round(gross_5 * 100.0) / 100.0;
        gross_8 = Math.round(gross_8 * 100.0) / 100.0;
        gross_23 = Math.round(gross_23 * 100.0) / 100.0;
    }
}
