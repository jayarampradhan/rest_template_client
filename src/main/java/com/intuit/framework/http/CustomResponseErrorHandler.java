package com.intuit.framework.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * @author jpradhan
 *         12/15/15.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText(),
                response.getHeaders(), response.getStatusText().getBytes(), MediaType.APPLICATION_JSON.getCharSet());
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }
}