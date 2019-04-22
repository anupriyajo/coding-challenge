package io.bankbridge.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class BanksRemoteCallsRemoteTest {

    @Mock
    private RestClientConnection restClientConnection;

    private String mockRemoteJson;

    @Before
    public void setUp(){
        restClientConnection = new RestClientConnection(new RestTemplate());
        mockRemoteJson = "{\n" +
                "\"bic\":\"1234\",\n" +
                "\"countryCode\":\"GB\",\n" +
                "\"auth\":\"OAUTH\"\n" +
                "}";
    }

    // test this one
    @Test
    public void testHandle() throws IOException {
        assertNotNull(restClientConnection);
        String bic = "1234";
        String bankUrl = "http://localhost:1234/rbb";
        when(restClientConnection.getBankDetails(bic, bankUrl))
                .thenReturn(mockRemoteJson);
        String expected = "[{\"Royal Bank of Boredom\":{\"bic\":\"1234\",\"name\":null,\"countryCode\":\"GB\",\"auth\":\"OAUTH\"}}]";
        String actual = BanksRemoteCalls.handle(null, null);
        assertEquals(expected, actual);

    }

}
