package pl.krakow.uek.invoiceservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krakow.uek.invoiceservice.model.Invoice;
import pl.krakow.uek.invoiceservice.model.InvoiceJsonData;

@Repository
public interface InvoicesRepository extends CrudRepository<InvoiceJsonData, Integer>{
    InvoiceJsonData findById(Integer id);
}
