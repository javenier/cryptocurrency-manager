package ua.com.javenier;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.com.javenier.model.CryptoPairRecord;

import static org.junit.Assert.*;

public class CryptoControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getListOfCryptocurrencies() throws Exception {
        String uri = "/cryptocurrencies?name=BTC";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CryptoPairRecord[] records = super.mapFromJson(content, CryptoPairRecord[].class);
        assertTrue(records.length > 0);
    }

    @Test
    public void getRecordWithMinPrice() throws Exception {
        String uri = "/cryptocurrencies/minprice?name=BTC";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CryptoPairRecord record = super.mapFromJson(content, CryptoPairRecord.class);
        assertNotNull(record);
    }

    @Test
    public void getRecordWithMaxPrice() throws Exception {
        String uri = "/cryptocurrencies/maxprice?name=BTC";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CryptoPairRecord record = super.mapFromJson(content, CryptoPairRecord.class);
        assertNotNull(record);
    }
}
