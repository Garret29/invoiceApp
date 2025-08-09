package pl.krakow.uek.invoiceservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Root(name = "dostawca")
public class Provider implements Serializable {
    @Element(name = "nazwa")
    private String providerName;
    @Element(name = "ulica")
    private String providerStreet;
    @Element(name = "dom")
    private String providerHouse;
    @Element(name = "lokal")
    private String providerApartment;
    @Element(name = "miasto")
    private String providerCity;
    @Element(name = "kod")
    private String providerPostalCode;
    @Element(name = "NIP_PESEL")
    private String providerNIP;
    @Element(name = "rachunek")
    private String providerBankNumber;
    @Element(name = "telefon")
    private String providerPhoneNumber;
    @Element(name = "email")
    private String providerEmail;
    @Element(name = "bank")
    private String providerBank;
}
