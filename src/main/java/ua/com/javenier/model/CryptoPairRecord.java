package ua.com.javenier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoPairRecord {

    @Id
    private String id;
    @JsonProperty("symbol1")
    private String firstMarketSymbol;
    @JsonProperty("symbol2")
    private String secondMarketSymbol;
    @JsonProperty("lprice")
    private BigDecimal lastPrice;
    private Date createdAt;

    public CryptoPairRecord() {
        this.createdAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstMarketSymbol() {
        return firstMarketSymbol;
    }

    public void setFirstMarketSymbol(String firstMarketSymbol) {
        this.firstMarketSymbol = firstMarketSymbol;
    }

    public String getSecondMarketSymbol() {
        return secondMarketSymbol;
    }

    public void setSecondMarketSymbol(String secondMarketSymbol) {
        this.secondMarketSymbol = secondMarketSymbol;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CryptoPairRecord{" +
                "id='" + id + '\'' +
                ", firstMarketSymbol='" + firstMarketSymbol + '\'' +
                ", secondMarketSymbol='" + secondMarketSymbol + '\'' +
                ", lastPrice=" + lastPrice +
                ", createdAt=" + createdAt +
                '}';
    }
}
