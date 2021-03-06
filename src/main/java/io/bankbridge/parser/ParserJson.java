package io.bankbridge.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParserJson {

    /**
     *
     * @param bic
     * @return default data for bank deatils in String format
     * @throws JsonProcessingException
     */
    public static String convertNullValueToText(String bic) throws JsonProcessingException {
        String jsonInString ;
        ObjectMapper mapper = new ObjectMapper();
        BankModel bankModel = new BankModel();
        bankModel.setName("Data not found");
        bankModel.setBic("Data not found");
        bankModel.setCountryCode("Data not found");
        bankModel.setAuth("Data not found");
        HashMap<String, BankModel> map = new HashMap<>();
        map.put(bic, bankModel);
        jsonInString = mapper.writeValueAsString(map);
        return jsonInString;
    }

    /**
     *
     * @param bic
     * @param bankModel
     * @return bank details in String format
     * @throws JsonProcessingException
     */
    public static String convertBankTypeToString(String bic, BankModel bankModel) throws JsonProcessingException {
        String jsonInString;
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, BankModel> map = new HashMap<>();
        map.put(bic, bankModel);
        jsonInString = mapper.writeValueAsString(bankModel);
        return jsonInString;
    }


    /**
     *
     * @param bankData
     * @return map having key as bic and bank details in BankModel class as value
     * @throws IOException
     */
    public static Map<String, BankModel> convertJsonToBankType(String bankData) throws IOException {
        Map<String, BankModel> bankDetailsMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        BankModel bankModel = mapper.readValue(bankData, BankModel.class);
        bankDetailsMap.put(bankModel.getBic(), bankModel);
        return bankDetailsMap;
    }
}
