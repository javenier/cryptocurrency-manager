package ua.com.javenier.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.com.javenier.model.CryptoPairRecord;

import java.util.List;

@Repository
public interface CryptoPairRecordRepository extends MongoRepository<CryptoPairRecord, String> {

    CryptoPairRecord findFirstByFirstMarketSymbolOrderByLastPriceAsc(String name);
    CryptoPairRecord findFirstByFirstMarketSymbolOrderByLastPriceDesc(String name);
    List<CryptoPairRecord> findByFirstMarketSymbolOrderByLastPriceAsc(String name, Pageable request);
}
