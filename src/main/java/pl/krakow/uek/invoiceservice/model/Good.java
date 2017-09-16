package pl.krakow.uek.invoiceservice.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "towar")
public class Good implements Serializable {
    @Element(name = "nazwa")
    private String name;
    @Element(name = "ilosc")
    private double quantity;
    @Element(name = "jednostka")
    private String unit;
    @Element(name = "cena_netto_jednost")
    private double priceNetOfUnit;
    @Element(name = "rabat")
    private double discount;
    @Element(name = "cena_netto_jednost_po_rabacie")
    private double priceNetOfUnitAfterDiscount;
    @Element(name = "netto")
    private double priceNetto;
    @Element(name = "brutto")
    private double priceGross;
    @Element(name = "stawka_VAT")
    private int vatRate;
    @Element(name = "VAT")
    private double vat;

    public Good(String name, double quantity, String unit, double priceNetOfUnit, double discount, int vatRate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.priceNetOfUnit = priceNetOfUnit;
        this.discount = discount;
        this.vatRate = vatRate;
    }

    public Good() {
    }

    public void doCalculations(){
        priceNetOfUnitAfterDiscount = priceNetOfUnit * ((100.0 - discount)/100.0);
        priceNetto = priceNetOfUnitAfterDiscount * quantity;
        vat = priceNetto * (vatRate/100.0);
        vat = Math.round(vat*100.0)/100.0;
        priceGross = priceNetto + vat;
        priceGross = Math.round(priceGross*100.0)/100.0;
        priceNetto = Math.round(priceNetto*100.0)/100.0;
        priceNetOfUnitAfterDiscount = Math.round(priceNetOfUnitAfterDiscount*100.0)/100.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPriceNetOfUnit() {
        return priceNetOfUnit;
    }

    public void setPriceNetOfUnit(double priceNetOfUnit) {
        this.priceNetOfUnit = priceNetOfUnit;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPriceNetOfUnitAfterDiscount() {
        return priceNetOfUnitAfterDiscount;
    }

    public void setPriceNetOfUnitAfterDiscount(double priceNetOfUnitAfterDiscount) {
        this.priceNetOfUnitAfterDiscount = priceNetOfUnitAfterDiscount;
    }

    public double getPriceNetto() {
        return priceNetto;
    }

    public void setPriceNetto(double priceNetto) {
        this.priceNetto = priceNetto;
    }

    public double getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(double priceGross) {
        this.priceGross = priceGross;
    }

    public int getVatRate() {
        return vatRate;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }
}
