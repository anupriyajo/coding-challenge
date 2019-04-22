package io.bankbridge.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParserJson {
    public static String convertNullValueToText(String bic) {
        String jsonInString = "oooo";
        return jsonInString;
    }

    public static String convertBankTypeToString(String bic, BankModel bankModel) throws JsonProcessingException {
        String jsonInString;
        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(bankModel);
        return jsonInString;
    }

    public static Map<String, BankModel> convertJsonToBankType(String bankData) throws IOException {
        Map<String, BankModel> bankDetailsMap ;
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, BankModel>> reference = new TypeReference<HashMap<String, BankModel>>(){};
        bankDetailsMap = mapper.readValue(bankData, reference);
        return bankDetailsMap;
    }
}
