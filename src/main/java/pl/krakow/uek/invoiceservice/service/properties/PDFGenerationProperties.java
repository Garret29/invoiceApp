package pl.krakow.uek.invoiceservice.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services.pdfgeneration")
public class PDFGenerationProperties {
    private String cacheDirPath;
    private Boolean clearingCacheDir;


    public String getCacheDirPath() {
        return cacheDirPath;
    }

    public void setCacheDirPath(String cacheDirPath) {
        this.cacheDirPath = cacheDirPath;
    }

    public Boolean getClearingCacheDir() {
        return clearingCacheDir;
    }

    public void setClearingCacheDir(Boolean clearingCacheDir) {
        this.clearingCacheDir = clearingCacheDir;
    }
}
