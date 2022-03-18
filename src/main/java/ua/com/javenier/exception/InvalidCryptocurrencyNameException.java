package ua.com.javenier.exception;

public class InvalidCryptocurrencyNameException extends RuntimeException {

    public InvalidCryptocurrencyNameException(String message) {
        super(message);
    }
}
