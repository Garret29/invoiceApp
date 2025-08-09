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
@Root(name = "nabywca")
public class Buyer implements Serializable {
    @Element(name = "nazwa")
    private String buyerName;
    @Element(name = "NIP_PESEL")
    private String buyerNIP;
    @Element(name = "ulica")
    private String buyerStreet;
    @Element(name = "dom")
    private String buyerHouse;
    @Element(name = "lokal")
    private String buyerApartment;
    @Element(name = "miasto")
    private String buyerCity;
    @Element(name = "kod")
    private String buyerPostalCode;
}
