package ua.com.javenier.service;

import ua.com.javenier.model.CsvRecord;

import java.util.List;

public interface CryptoPairRecordService<T> {

    T getRecordWithLowestPrice(String name);
    T getRecordWithHighestPrice(String name);
    List<T> findAllRecords(String name, int page, int size);
    List<CsvRecord> prepareFileInfo();
}
