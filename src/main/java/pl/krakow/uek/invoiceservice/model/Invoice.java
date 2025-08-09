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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        if (Double.compare(invoice.gross, gross) != 0) return false;
        if (Double.compare(invoice.vat, vat) != 0) return false;
        if (Double.compare(invoice.netto, netto) != 0) return false;
        if (Double.compare(invoice.vat_0, vat_0) != 0) return false;
        if (Double.compare(invoice.vat_5, vat_5) != 0) return false;
        if (Double.compare(invoice.vat_8, vat_8) != 0) return false;
        if (Double.compare(invoice.vat_23, vat_23) != 0) return false;
        if (Double.compare(invoice.net_0, net_0) != 0) return false;
        if (Double.compare(invoice.net_5, net_5) != 0) return false;
        if (Double.compare(invoice.net_8, net_8) != 0) return false;
        if (Double.compare(invoice.net_23, net_23) != 0) return false;
        if (Double.compare(invoice.gross_0, gross_0) != 0) return false;
        if (Double.compare(invoice.gross_5, gross_5) != 0) return false;
        if (Double.compare(invoice.gross_8, gross_8) != 0) return false;
        if (Double.compare(invoice.gross_23, gross_23) != 0) return false;
        if (invoiceCity != null ? !invoiceCity.equals(invoice.invoiceCity) : invoice.invoiceCity != null) return false;
        if (invoiceDate != null ? !invoiceDate.equals(invoice.invoiceDate) : invoice.invoiceDate != null) return false;
        if (invoiceShippingDate != null ? !invoiceShippingDate.equals(invoice.invoiceShippingDate) : invoice.invoiceShippingDate != null)
            return false;
        if (paymentDate != null ? !paymentDate.equals(invoice.paymentDate) : invoice.paymentDate != null) return false;
        if (paymentMethod != null ? !paymentMethod.equals(invoice.paymentMethod) : invoice.paymentMethod != null)
            return false;
        if (id != null ? !id.equals(invoice.id) : invoice.id != null)
            return false;
        if (provider != null ? !provider.equals(invoice.provider) : invoice.provider != null) return false;
        if (buyer != null ? !buyer.equals(invoice.buyer) : invoice.buyer != null) return false;
        if (goods != null ? !goods.equals(invoice.goods) : invoice.goods != null) return false;
        return bruttoWords != null ? bruttoWords.equals(invoice.bruttoWords) : invoice.bruttoWords == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = invoiceCity != null ? invoiceCity.hashCode() : 0;
        result = 31 * result + (invoiceDate != null ? invoiceDate.hashCode() : 0);
        result = 31 * result + (invoiceShippingDate != null ? invoiceShippingDate.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (buyer != null ? buyer.hashCode() : 0);
        result = 31 * result + (goods != null ? goods.hashCode() : 0);
        temp = Double.doubleToLongBits(gross);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(netto);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (bruttoWords != null ? bruttoWords.hashCode() : 0);
        temp = Double.doubleToLongBits(vat_0);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat_5);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat_8);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat_23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(net_0);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(net_5);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(net_8);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(net_23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gross_0);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gross_5);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gross_8);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gross_23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
