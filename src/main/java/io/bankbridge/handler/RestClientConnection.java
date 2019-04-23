package io.bankbridge.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import io.bankbridge.parser.ParserJson;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

public class RestClientConnection {

    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public RestClientConnection(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     *
     * @param bankName
     * @param bankUrl
     * @return bank details in String format
     * @throws IOException
     */
    public String getBankDetails(String bankName, String bankUrl) throws IOException {
        String result ;
        try {
            String bankData = restTemplate.getForObject(bankUrl, String.class);
            BankModel bankModel = new ObjectMapper().readValue(bankData, BankModel.class);
            result = ParserJson.convertBankTypeToString(bankName, bankModel);
        }catch(HttpStatusCodeException e){
            result = convertNullValueToText(bankName);
        }catch (RuntimeException e){
            result = convertNullValueToText(bankName);
        }
        return result;
    }

    /**
     *
     * @param bic
     * @return Default text if bank details data can't be read
     * @throws JsonProcessingException
     */
    private String convertNullValueToText(String bic) throws JsonProcessingException {
        return ParserJson.convertNullValueToText(bic);
    }
}
