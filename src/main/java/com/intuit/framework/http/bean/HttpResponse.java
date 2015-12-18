package com.intuit.framework.http.bean;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class HttpResponse<R>{
        private ResponseEntity<R> responseEntity;
        public static String DEFAULT_ENCODING = "UTF-8";
        private static String CONTENT_TYPE_KEY = "Content-Type";
        private static String CONTENT_LENGTH_KEY = "Content-Length";
        private String targetedUrl;
        private String targetedURI;
        private HttpMethod requestMethod;

        public HttpResponse(ResponseEntity<R> responseEntity, String targetedUrl, String targetedURI, HttpMethod requestMethod){
            Assert.notNull(responseEntity);
            this.responseEntity = responseEntity;
            this.targetedUrl = targetedUrl;
            this.targetedURI = targetedURI;
            this.requestMethod = requestMethod;
        }

        public String getContentType(){
            return this.responseEntity.getHeaders().getFirst(CONTENT_TYPE_KEY);
        }

        public String getCharset() {
            String v = this.responseEntity.getHeaders().getFirst(CONTENT_TYPE_KEY);
            if(v == null || !v.contains("charset")) {
                return DEFAULT_ENCODING;
            }
            int pos = v.indexOf("charset");
            pos = v.indexOf("=", pos);
            try {
                String substring = v.substring(pos + 1);
                return substring.trim();
            } catch(RuntimeException e) {
                return DEFAULT_ENCODING;
            }
        }

        public String getContentLength() {
            return this.responseEntity.getHeaders().getFirst(CONTENT_LENGTH_KEY);
        }

        public String getTargetedUrl() {
            return targetedUrl;
        }

        public void setTargetedUrl(String targetedUrl) {
            this.targetedUrl = targetedUrl;
        }

        public ResponseEntity<R> getResponseEntity() {
            return responseEntity;
        }

        public void setResponseEntity(ResponseEntity<R> responseEntity) {
            this.responseEntity = responseEntity;
        }

        public String getTargetedURI() {
            return targetedURI;
        }

        public void setTargetedURI(String targetedURI) {
            this.targetedURI = targetedURI;
        }

        public HttpMethod getRequestMethod() {
            return requestMethod;
        }

        public void setRequestMethod(HttpMethod requestMethod) {
            this.requestMethod = requestMethod;
        }
    }