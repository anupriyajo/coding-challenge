package io.bankbridge.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

public class RestClientConnectionTest {

    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private RestClientConnection restClientConnection;

    @Before
    public void init() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        restClientConnection = new RestClientConnection(restTemplate);
    }

    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenResponseIsCorrect_thenReturnsMockedObject() throws IOException {
        String expected="{\"bic\":\"1234\",\"name\":\"Royal Bank of Boredom\",\"countryCode\":\"GB\",\"auth\":\"OAUTH\"}";
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:1234/rbb"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(expected)
                );
        String actual = restClientConnection.getBankDetails("1234", "http://localhost:1234/rbb");
        mockServer.verify();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenResponseIs404_thenReturnsMockedObject() throws IOException {
        String expected="{\"1234\":{\"bic\":\"Data not found\",\"name\":\"Data not found\",\"countryCode\":\"Data not found\",\"auth\":\"Data not found\"}}";
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:1234/rbb"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());
        String actual = restClientConnection.getBankDetails("1234", "http://localhost:1234/rbb");
        mockServer.verify();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenResponseIs500_thenReturnsMockedObject() throws IOException {
        String expected="{\"1234\":{\"bic\":\"Data not found\",\"name\":\"Data not found\",\"countryCode\":\"Data not found\",\"auth\":\"Data not found\"}}";
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:1234/rbb"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());
        String actual = restClientConnection.getBankDetails("1234", "http://localhost:1234/rbb");
        mockServer.verify();
        Assert.assertEquals(expected, actual);
    }

}
