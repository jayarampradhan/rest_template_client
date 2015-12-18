package com.intuit.framework.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by jpradhan on 7/31/15.
 */
public class RestTemplateWrapper {

    private static Logger LOG = LoggerFactory.getLogger(RestTemplateWrapper.class);
    private final RestTemplate _TEMPLATE;

    public RestTemplateWrapper() {
        this(new HttpComponentsClientHttpRequestFactory());
    }

    public RestTemplateWrapper(HttpComponentsClientHttpRequestFactory requestFactory) {
        _TEMPLATE = new RestTemplate(requestFactory);
        init();
    }

    private void init() {
        configureInterceptors();
        customizeConverters();
    }

    /**
     * Adds Different Interceptors such as Logging and Accept header and error handlers
     */
    private void configureInterceptors() {
        _TEMPLATE.getInterceptors().add(new AcceptHeaderHttpRequestInterceptor());
        _TEMPLATE.getInterceptors().add(new LoggingRequestInterceptor());
        _TEMPLATE.setErrorHandler(new CustomResponseErrorHandler());
    }

    /**
     * Adds Different Message Body converter such as {@link MappingJackson2HttpMessageConverter}
     */
    private void customizeConverters() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(ObjectMapperFactory.getMapper());
        List<HttpMessageConverter<?>> ls = new ArrayList<>();
        ls.add(jsonConverter);
        ls.add(new FormHttpMessageConverter());
        getTemplate().getMessageConverters().addAll(ls);
    }

    /**
     * Configures the Time out in milliseconds options for the http request
     *
     * @param readTimeOut    number of milliseconds to wait for server response
     * @param connectTimeout number of milliseconds to wait for sending the data to server
     */
    public void configureTimeOuts(int readTimeOut, int connectTimeout) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        if (readTimeOut > 0) {
            requestFactory.setReadTimeout(readTimeOut);
        } else {
            LOG.warn("Read Time Out is not Configured as Its a invalid parameter.");
        }
        if (connectTimeout > 0) {
            requestFactory.setConnectTimeout(connectTimeout);
        } else {
            LOG.warn("Write Time Out is not Configured as Its a invalid parameter.");
        }
        LOG.debug("Time Outs[readTimeOut={},connectTimeout={}] has been applied.", readTimeOut, connectTimeout);
        getTemplate().setRequestFactory(requestFactory);
    }

    public RestTemplate getTemplate() {
        return _TEMPLATE;
    }

    public static class UrlBuilder {
        private UriComponentsBuilder builder;

        public UrlBuilder(String url) {
            this.builder = UriComponentsBuilder.fromHttpUrl(url);
        }

        public UrlBuilder addRequestParam(Map<String, String> urlFrags) {
            if (!CollectionUtils.isEmpty(urlFrags)) {
                MultiValueMap<String, String> mm = new LinkedMultiValueMap<>();
                for (String key : urlFrags.keySet()) {
                    mm.add(key, urlFrags.get(key));
                }
                this.builder.queryParams(mm);
            }
            return this;
        }

        public String build() {
            return builder.toUriString();
        }

        public String build(Map<String, ?> urlVariables) {
            return builder.buildAndExpand(urlVariables).toUriString();
        }

    }

    public static class Caller<R> {

        private final RestTemplate template;
        private HttpMethod method;
        private String url;
        private Object requestBody;
        private Class<R> claz;
        private Map<String, String> headers = new WeakHashMap<>();

        public Caller(RestTemplate template) {
            Assert.notNull(template, "HTTP Connection Manager Is Invalid");
            this.template = template;
        }

        public Caller<R> make(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Caller<R> to(String url) {
            this.url = url;
            return this;
        }

        public Caller<R> with(Object requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Caller<R> responseIsOfType(Class<R> claz) {
            this.claz = claz;
            return this;
        }

        public Caller<R> withHeaders(Map<String, String> headers) {
            if (!CollectionUtils.isEmpty(headers)) {
                this.headers.putAll(headers);
            } else {
                LOG.warn("Headers will be ignored.");
            }
            return this;
        }

        public ResponseEntity<R> call() {
            Assert.hasText(url, "Target URL is Invalid");
            Assert.notNull(method, "Target Http Method Is Invalid");
            HttpEntity<Object> request = buildRequestEntity();
            return doCall(method, url, request, claz);
        }

        private <R> ResponseEntity<R> doCall(HttpMethod method, String url, HttpEntity request, Class<R> claz) {
            if (null == claz) {
                //TODO get rid of this block completly in next release
                template.exchange(url, method, request, String.class);
                return null;
            }
            return template.exchange(url, method, request, claz);
        }

        /**
         * Constructs the http request entity from header and request body.
         *
         * @return http request entity
         */
        private HttpEntity<Object> buildRequestEntity() {
            MultiValueMap<String, String> hdrs = new LinkedMultiValueMap<>();
            for (String key : headers.keySet()) {
                hdrs.add(key, headers.get(key));
            }
            return new HttpEntity<>(requestBody, hdrs);
        }
    }
}
