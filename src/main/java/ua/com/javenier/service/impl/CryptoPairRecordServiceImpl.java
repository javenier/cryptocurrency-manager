package ua.com.javenier.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.javenier.exception.InvalidCryptocurrencyNameException;
import ua.com.javenier.model.CryptoPairRecord;
import ua.com.javenier.model.CsvRecord;
import ua.com.javenier.repository.CryptoPairRecordRepository;
import ua.com.javenier.service.CryptoPairRecordService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoPairRecordServiceImpl implements CryptoPairRecordService<CryptoPairRecord> {

    private final CryptoPairRecordRepository cryptoPairRecordRepository;

    public CryptoPairRecordServiceImpl(CryptoPairRecordRepository cryptoPairRecordRepository) {
        this.cryptoPairRecordRepository = cryptoPairRecordRepository;
    }

    @Override
    public CryptoPairRecord getRecordWithLowestPrice(String name) {
        if(name.equals("BTC") || name.equals("XRP") || name.equals("ETH"))
            return cryptoPairRecordRepository.findFirstByFirstMarketSymbolOrderByLastPriceAsc(name);
        throw new InvalidCryptocurrencyNameException("Invalid cryptocurrency name! Possible variants: BTC, XRP, ETH");
    }

    @Override
    public CryptoPairRecord getRecordWithHighestPrice(String name) {
        if(name.equals("BTC") || name.equals("XRP") || name.equals("ETH"))
            return cryptoPairRecordRepository.findFirstByFirstMarketSymbolOrderByLastPriceDesc(name);
        throw new InvalidCryptocurrencyNameException("Invalid cryptocurrency name! Possible variants: BTC, XRP, ETH");
    }

    @Override
    public List<CryptoPairRecord> findAllRecords(String name, int page, int size) {
        if(name.equals("BTC") || name.equals("XRP") || name.equals("ETH")) {
            Pageable request = PageRequest.of(page, size);
            List<CryptoPairRecord> records = cryptoPairRecordRepository.findByFirstMarketSymbolOrderByLastPriceAsc(name, request);
            return records;
        }
        throw new InvalidCryptocurrencyNameException("Invalid cryptocurrency name! Possible variants: BTC, XRP, ETH");
    }

    @Override
    public List<CsvRecord> prepareFileInfo() {
        List<CsvRecord> source = new ArrayList<>();

        String btcMinPrice = cryptoPairRecordRepository.
                findFirstByFirstMarketSymbolOrderByLastPriceAsc("BTC").
                getLastPrice().
                toPlainString();
        String btcMaxPrice = cryptoPairRecordRepository.
                findFirstByFirstMarketSymbolOrderByLastPriceDesc("BTC").
                getLastPrice().
                toPlainString();
        String ethMinPrice = cryptoPairRecordRepository.
                findFirstByFirstMarketSymbolOrderByLastPriceAsc("ETH").
                getLastPrice().
                toPlainString();
        String ethMaxPrice = cryptoPairRecordRepository.
                findFirstByFirstMarketSymbolOrderByLastPriceDesc("ETH").
                getLastPrice().
                toPlainString();
        String xrpMinPrice = cryptoPairRecordRepository.
                findFirstByFirstMarketSymbolOrderByLastPriceAsc("XRP").
                getLastPrice().
                toPlainString();
        String xrpMaxPrice = cryptoPairRecordRepository.
                findFirstByFirstMarketSymbolOrderByLastPriceDesc("XRP").
                getLastPrice().
                toPlainString();

        CsvRecord btc = new CsvRecord("BTC", btcMinPrice, btcMaxPrice);
        CsvRecord eth = new CsvRecord("ETH", ethMinPrice, ethMaxPrice);
        CsvRecord xrp = new CsvRecord("XRP", xrpMinPrice, xrpMaxPrice);

        source.add(btc);
        source.add(eth);
        source.add(xrp);
        return source;
    }
}