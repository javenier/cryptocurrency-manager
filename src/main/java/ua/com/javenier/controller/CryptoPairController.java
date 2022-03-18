package ua.com.javenier.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import ua.com.javenier.model.CryptoPairRecord;
import ua.com.javenier.model.CsvRecord;
import ua.com.javenier.service.CryptoPairRecordService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptoPairController {

    private final CryptoPairRecordService<CryptoPairRecord> cryptoPairRecordService;

    public CryptoPairController(CryptoPairRecordService cryptoPairRecordService) {
        this.cryptoPairRecordService = cryptoPairRecordService;
    }

    @GetMapping("/minprice")
    public ResponseEntity<CryptoPairRecord> getRecordWithLowestPrice(@RequestParam String name) {
        CryptoPairRecord record = cryptoPairRecordService.getRecordWithLowestPrice(name);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping("/maxprice")
    public ResponseEntity<CryptoPairRecord> getRecordWithHighestPrice(@RequestParam String name) {
        CryptoPairRecord record = cryptoPairRecordService.getRecordWithHighestPrice(name);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CryptoPairRecord>> getRecords(@RequestParam String name,
                                                             @RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size) {
        if (page == null)
            page = 0;
        if (size == null)
            size = 10;
        List<CryptoPairRecord> records = cryptoPairRecordService.findAllRecords(name, page, size);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/csv")
    public ResponseEntity<?> exportToCsv(HttpServletResponse response) {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=crypto_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
        String[] headers = {"Cryptocurrency", "Min price", "Max price"};
        String[] nameMapping = {"cryptocurrencyName", "minPrice", "maxPrice"};

        List<CsvRecord> source = cryptoPairRecordService.prepareFileInfo();
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            csvWriter.writeHeader(headers);
            for (CsvRecord record : source) {
                csvWriter.write(record, nameMapping);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}