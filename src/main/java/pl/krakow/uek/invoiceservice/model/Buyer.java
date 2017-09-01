package pl.krakow.uek.invoiceservice.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "nabywca")
public class Buyer implements Serializable {
    @Element(name = "nazwa")
    private String buyerName;
    @Element(name = "ulica")
    private String buyerStreet;
    @Element(name = "dom")
    private String buyerHouse;
    @Element(name = "lokal")
    private String buyerAppartment;
    @Element(name = "miasto")
    private String buyerCity;
    @Element(name = "kod")
    private String buyerPostalCode;

    public Buyer(String buyerName, String buyerStreet, String buyerHouse, String buyerAppartment, String buyerCity, String buyerPostalCode) {
        this.buyerName = buyerName;
        this.buyerStreet = buyerStreet;
        this.buyerHouse = buyerHouse;
        this.buyerAppartment = buyerAppartment;
        this.buyerCity = buyerCity;
        this.buyerPostalCode = buyerPostalCode;
    }

    public Buyer() {
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerStreet() {
        return buyerStreet;
    }

    public void setBuyerStreet(String buyerStreet) {
        this.buyerStreet = buyerStreet;
    }

    public String getBuyerHouse() {
        return buyerHouse;
    }

    public void setBuyerHouse(String buyerHouse) {
        this.buyerHouse = buyerHouse;
    }

    public String getBuyerAppartment() {
        return buyerAppartment;
    }

    public void setBuyerAppartment(String buyerAppartment) {
        this.buyerAppartment = buyerAppartment;
    }

    public String getBuyerCity() {
        return buyerCity;
    }

    public void setBuyerCity(String buyerCity) {
        this.buyerCity = buyerCity;
    }

    public String getBuyerPostalCode() {
        return buyerPostalCode;
    }

    public void setBuyerPostalCode(String buyerPostalCode) {
        this.buyerPostalCode = buyerPostalCode;
    }
}
