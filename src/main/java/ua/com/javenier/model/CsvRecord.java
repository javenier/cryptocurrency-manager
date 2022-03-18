package ua.com.javenier.model;

public class CsvRecord {

    private String cryptocurrencyName;
    private String minPrice;
    private String maxPrice;

    public CsvRecord(String cryptocurrencyName, String minPrice, String maxPrice) {
        this.cryptocurrencyName = cryptocurrencyName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getCryptocurrencyName() {
        return cryptocurrencyName;
    }

    public void setCryptocurrencyName(String cryptocurrencyName) {
        this.cryptocurrencyName = cryptocurrencyName;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
