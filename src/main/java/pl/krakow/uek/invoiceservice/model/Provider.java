package pl.krakow.uek.invoiceservice.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

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

    public Provider() {
    }

    public Provider(String providerName, String providerStreet, String providerHouse, String providerApartment, String providerCity, String providerPostalCode, String providerNIP, String providerBankNumber, String providerPhoneNumber, String providerEmail, String providerBank) {
        this.providerName = providerName;
        this.providerStreet = providerStreet;
        this.providerHouse = providerHouse;
        this.providerApartment = providerApartment;
        this.providerCity = providerCity;
        this.providerPostalCode = providerPostalCode;
        this.providerNIP = providerNIP;
        this.providerBankNumber = providerBankNumber;
        this.providerPhoneNumber = providerPhoneNumber;
        this.providerEmail = providerEmail;
        this.providerBank = providerBank;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderStreet() {
        return providerStreet;
    }

    public void setProviderStreet(String providerStreet) {
        this.providerStreet = providerStreet;
    }

    public String getProviderHouse() {
        return providerHouse;
    }

    public void setProviderHouse(String providerHouse) {
        this.providerHouse = providerHouse;
    }

    public String getProviderApartment() {
        return providerApartment;
    }

    public void setProviderApartment(String providerApartment) {
        this.providerApartment = providerApartment;
    }

    public String getProviderCity() {
        return providerCity;
    }

    public void setProviderCity(String providerCity) {
        this.providerCity = providerCity;
    }

    public String getProviderPostalCode() {
        return providerPostalCode;
    }

    public void setProviderPostalCode(String providerPostalCode) {
        this.providerPostalCode = providerPostalCode;
    }

    public String getProviderNIP() {
        return providerNIP;
    }

    public void setProviderNIP(String providerNIP) {
        this.providerNIP = providerNIP;
    }

    public String getProviderBankNumber() {
        return providerBankNumber;
    }

    public void setProviderBankNumber(String providerBankNumber) {
        this.providerBankNumber = providerBankNumber;
    }

    public String getProviderPhoneNumber() {
        return providerPhoneNumber;
    }

    public void setProviderPhoneNumber(String providerPhoneNumber) {
        this.providerPhoneNumber = providerPhoneNumber;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderBank() {
        return providerBank;
    }

    public void setProviderBank(String providerBank) {
        this.providerBank = providerBank;
    }
}
