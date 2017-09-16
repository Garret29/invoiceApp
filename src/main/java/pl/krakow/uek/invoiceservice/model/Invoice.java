package pl.krakow.uek.invoiceservice.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
@Root(name = "faktura")
public class Invoice implements Serializable {

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

    //Razem:
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

    public Invoice() {
    }

    public Invoice(String invoiceCity, String invoiceDate, String invoiceShippingDate, String paymentDate, String paymentMethod, String id, Provider provider, Buyer buyer, List<Good> goods, double gross, double vat, double netto, String bruttoWords, double vat_0, double vat_5, double vat_8, double vat_23, double net_0, double net_5, double net_8, double net_23, double gross_0, double gross_5, double gross_8, double gross_23) {

        this.invoiceCity = invoiceCity;
        this.invoiceDate = invoiceDate;
        this.invoiceShippingDate = invoiceShippingDate;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.id = id;
        this.provider = provider;
        this.buyer = buyer;

        this.bruttoWords = bruttoWords;

        this.goods = goods;

        this.gross = gross;
        this.netto = netto;
        this.vat = vat;

        this.vat_0 = vat_0;
        this.vat_5 = vat_5;
        this.vat_8 = vat_8;
        this.vat_23 = vat_23;
        this.net_0 = net_0;
        this.net_5 = net_5;
        this.net_8 = net_8;
        this.net_23 = net_23;
        this.gross_0 = gross_0;
        this.gross_5 = gross_5;
        this.gross_8 = gross_8;
        this.gross_23 = gross_23;
    }

    public void addGood(Good good) {
        goods.add(good);
    }

    public void doCalculations() {
        goods.forEach(good -> {
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

    public double getNet_0() {
        return net_0;
    }

    public void setNet_0(double net_0) {
        this.net_0 = net_0;
    }

    public double getNet_5() {
        return net_5;
    }

    public void setNet_5(double net_5) {
        this.net_5 = net_5;
    }

    public double getNet_8() {
        return net_8;
    }

    public void setNet_8(double net_8) {
        this.net_8 = net_8;
    }

    public double getNet_23() {
        return net_23;
    }

    public void setNet_23(double net_23) {
        this.net_23 = net_23;
    }

    public double getGross_0() {
        return gross_0;
    }

    public void setGross_0(double gross_0) {
        this.gross_0 = gross_0;
    }

    public double getGross_5() {
        return gross_5;
    }

    public void setGross_5(double gross_5) {
        this.gross_5 = gross_5;
    }

    public double getGross_8() {
        return gross_8;
    }

    public void setGross_8(double gross_8) {
        this.gross_8 = gross_8;
    }

    public double getGross_23() {
        return gross_23;
    }

    public void setGross_23(double gross_23) {
        this.gross_23 = gross_23;
    }

    public double getVat_0() {
        return vat_0;
    }

    public void setVat_0(double vat_0) {
        this.vat_0 = vat_0;
    }

    public double getVat_5() {
        return vat_5;
    }

    public void setVat_5(double vat_5) {
        this.vat_5 = vat_5;
    }

    public double getVat_8() {
        return vat_8;
    }

    public void setVat_8(double vat_8) {
        this.vat_8 = vat_8;
    }

    public double getVat_23() {
        return vat_23;
    }

    public void setVat_23(double vat_23) {
        this.vat_23 = vat_23;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceShippingDate() {
        return invoiceShippingDate;
    }

    public void setInvoiceShippingDate(String invoiceShippingDate) {
        this.invoiceShippingDate = invoiceShippingDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public String getBruttoWords() {
        return bruttoWords;
    }

    public void setBruttoWords(String bruttoWords) {
        this.bruttoWords = bruttoWords;
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
        if (id != null ? !id.equals(invoice.id) : invoice.id != null) return false;
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
