package io.bankbridge.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import io.bankbridge.parser.ParserJson;
import org.springframework.web.client.RestTemplate;
import spark.Request;
import spark.Response;

public class BanksRemoteCalls {

	private static Map<String, String> config;
	private static RestClientConnection restClientConnection;

	public static void init() throws Exception {
		config = new ObjectMapper()
				.readValue(Thread.currentThread().getContextClassLoader().getResource("banks-v2.json"), Map.class);
		restClientConnection = new RestClientConnection(new RestTemplate());
	}

	public static String handle(Request request, Response response) {
		List<Map<String, BankModel>> result = new ArrayList<>();
		config.entrySet().stream().forEach(entry ->{
			try {
				String bankData = restClientConnection.getBankDetails(entry.getKey(), entry.getValue());
				result.add(ParserJson.convertJsonToBankType(bankData));
			}catch(IOException e){
				throw new RuntimeException("Error while processing request");
			}
		});
		try {
			String resultAsString = new ObjectMapper().writeValueAsString(result);
			return resultAsString;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error while processing request");
		}
	}

}
