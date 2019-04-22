package io.bankbridge.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class BanksRemoteCallsRemoteTest {

    @Mock
    private RestClientConnection restClientConnection;

    private String mockRemoteJson;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockRemoteJson = "{\n" +
                "\"bic\":\"1234\",\n" +
                "\"countryCode\":\"GB\",\n" +
                "\"auth\":\"OAUTH\"\n" +
                "}";
    }

    // test this one
    @Test
    public void testHandle() throws IOException {
        restClientConnection.setRestTemplate(new RestTemplate());
        assertNotNull(restClientConnection);
        String bankName = "Royal Bank of Boredom";
        String bankUrl = "http://localhost:1234/rbb";
        HashMap<String, String> map = new HashMap<>();
        map.put(bankName, bankUrl);
        BanksRemoteCalls.setConfig(map);
        BanksRemoteCalls.setRestClientConnection(restClientConnection);
        when(restClientConnection.getBankDetails(bankName, bankUrl))
                .thenReturn(mockRemoteJson);
        String expected = "[{\"Royal Bank of Boredom\":{\"bic\":\"1234\",\"name\":null,\"countryCode\":\"GB\",\"auth\":\"OAUTH\"}}]";
        String actual = BanksRemoteCalls.handle(null, null);
        assertEquals(expected, actual);

    }

}
