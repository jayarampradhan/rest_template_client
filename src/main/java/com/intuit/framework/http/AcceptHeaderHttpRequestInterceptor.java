package com.intuit.framework.http;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.util.Collections.singletonList;

/**
 * @author jpradhan
 *         12/15/15.
 */
public class AcceptHeaderHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final static String UTF_8 = "UTF-8";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        requestWrapper.getHeaders().setAccept(singletonList(MediaType.APPLICATION_JSON));
        requestWrapper.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        requestWrapper.getHeaders().setAcceptCharset(singletonList(Charset.forName(UTF_8)));
        return execution.execute(requestWrapper, body);
    }
}
