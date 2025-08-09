package pl.krakow.uek.invoiceservice.model;

import lombok.*;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Root(name = "towar")
public class Good implements Serializable, Calculable {
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

    @Override
    public void calculate() {
        priceNetOfUnitAfterDiscount = priceNetOfUnit * ((100.0 - discount) / 100.0);
        priceNetto = priceNetOfUnitAfterDiscount * quantity;
        vat = priceNetto * (vatRate / 100.0);
        vat = Math.round(vat * 100.0) / 100.0;
        priceGross = priceNetto + vat;
        priceGross = Math.round(priceGross * 100.0) / 100.0;
        priceNetto = Math.round(priceNetto * 100.0) / 100.0;
        priceNetOfUnitAfterDiscount = Math.round(priceNetOfUnitAfterDiscount * 100.0) / 100.0;
    }

}
