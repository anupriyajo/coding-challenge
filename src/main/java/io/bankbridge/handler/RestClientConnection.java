package io.bankbridge.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import io.bankbridge.parser.ParserJson;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class RestClientConnection {

    private RestTemplate restTemplate;

    public RestClientConnection(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getBankDetails(String bic, String bankUrl) throws IOException {
        String result ;
        try {
            String bankData = restTemplate.getForObject(bankUrl, String.class);
            BankModel bankModel = new ObjectMapper().readValue(bankData, BankModel.class);
            result = ParserJson.convertBankTypeToString(bic, bankModel);
        }catch(HttpStatusCodeException e){
            result = convertNullValueToText(bic);
        }catch (RuntimeException e){
            result = convertNullValueToText(bic);
        }
        return result;
    }

    private String convertNullValueToText(String bic) {
        return ParserJson.convertNullValueToText(bic);
    }
}
