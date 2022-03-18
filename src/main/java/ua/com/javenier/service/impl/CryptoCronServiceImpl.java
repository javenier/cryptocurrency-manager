package ua.com.javenier.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.javenier.model.CryptoPairRecord;
import ua.com.javenier.repository.CryptoPairRecordRepository;
import ua.com.javenier.service.CryptoCronService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoCronServiceImpl implements CryptoCronService {

    private final CryptoPairRecordRepository cryptoPairRecordRepository;
    private final RestTemplate restTemplate;

    private static final String REQUEST_API_URL = "https://cex.io/api/last_prices/USD";

    public CryptoCronServiceImpl(CryptoPairRecordRepository cryptoPairRecordRepository, RestTemplate restTemplate) {
        this.cryptoPairRecordRepository = cryptoPairRecordRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Scheduled(fixedDelay = 30000)
    public void fetchLastPrices() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(REQUEST_API_URL, String.class);
        List<CryptoPairRecord> allRecords = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(responseEntity.getBody()).get("data");
            ObjectReader reader = mapper.readerFor(new TypeReference<List<CryptoPairRecord>>() {
            });
            allRecords = reader.readValue(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CryptoPairRecord> filteredRecords = allRecords.stream().
                filter(r -> r.getFirstMarketSymbol().equals("BTC") ||
                        r.getFirstMarketSymbol().equals("XRP") ||
                        r.getFirstMarketSymbol().equals("ETH")).
                collect(Collectors.toList());
        cryptoPairRecordRepository.saveAll(filteredRecords);
    }
}
