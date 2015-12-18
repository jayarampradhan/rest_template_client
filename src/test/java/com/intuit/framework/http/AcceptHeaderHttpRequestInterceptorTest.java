//package com.intuit.framework.http;/*
//
//import com.intuit.framework.group.UnitTests;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.mock.http.client.MockClientHttpRequest;
//import org.springframework.mock.http.client.MockClientHttpResponse;
//
//import static org.mockito.Mockito.doReturn;
//
///**
// * Created by: jpradhan
// * Created on 2015/09/20.
// */
//@RunWith(MockitoJUnitRunner.class)
//@Category({UnitTests.class})
//public class AcceptHeaderHttpRequestInterceptorTest {
//    private static AcceptHeaderHttpRequestInterceptor acceptHeaderHttpRequestInterceptor;
//    @Mock
//    private ClientHttpRequestExecution execution;
//    @Mock
//    private MockClientHttpRequest request;
//    @Mock
//    private MockClientHttpResponse response;
//    @Mock
//    private HttpHeaders headers;
//
//    @BeforeClass
//    public static void setUpOnce() throws Exception {
//        acceptHeaderHttpRequestInterceptor = new AcceptHeaderHttpRequestInterceptor();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testIntercept() throws Exception {
//        doReturn(headers).when(request).getHeaders();
//        doReturn(response).when(execution).execute(request, new byte[0]);
//        acceptHeaderHttpRequestInterceptor.intercept(request, new byte[0], execution);
//    }
//}
