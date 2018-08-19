package pl.krakow.uek.invoiceservice.model;

import javax.persistence.*;

@Entity
public class InvoiceJsonData {
    @Id
    private Integer id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String data;

    public InvoiceJsonData(String data, Integer id) {
        this.data = data;
        this.id = id;
    }

    //JPA only
    public InvoiceJsonData() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }
}
