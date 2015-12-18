//package com.intuit.framework.http;
//
//import ch.qos.logback.classic.Level;
//import com.intuit.framework.LogBackVerifier;
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
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//import java.util.WeakHashMap;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.http.HttpMethod.POST;
//
///**
// * Created by jpradhan on 9/3/15.
// */
//@RunWith(MockitoJUnitRunner.class)
//@Category({UnitTests.class})
//public class RestTemplateWrapperTest {
//
//    private static RestTemplateWrapper restTemplateWrapper;
//    @Rule
//    public LogBackVerifier logBackVerifier = new LogBackVerifier();
//    @Mock
//    private RestTemplate template;
//    @Mock
//    private ResponseEntity responseEntity;
//    @Mock
//    private HttpClientErrorException clientErrorException;
//    private static final String _PI_BY_NAME = "/test";
//
//    @BeforeClass
//    public static void setUpOnce() {
//        restTemplateWrapper = new RestTemplateWrapper();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//    }
//
//    @Test
//    public void testHasProperMessageConverter() throws Exception {
//        assertThat(restTemplateWrapper.getTemplate().getMessageConverters()).hasSize(1);
//
//    }
//
//    @Test
//    public void testAddTimeOuts() throws Exception {
//        logBackVerifier.expectMessage(Level.DEBUG, "Time Outs[readTimeOut=10,connectTimeout=10] has been applied.");
//        restTemplateWrapper.configureTimeOuts(10, 10);
//    }
//
//    @Test
//    public void testAddTimeOutsInvalid() throws Exception {
//        logBackVerifier.expectMessage(Level.WARN, "Read Time Out is not Configured as Its a invalid parameter.");
//        logBackVerifier.expectMessage(Level.WARN, "Write Time Out is not Configured as Its a invalid parameter.");
//        restTemplateWrapper.configureTimeOuts(0, 0);
//    }
//
//    @Test
//    public void testAddTimeOutsInNegative() throws Exception {
//        logBackVerifier.expectMessage(Level.WARN, "Read Time Out is not Configured as Its a invalid parameter.");
//        logBackVerifier.expectMessage(Level.WARN, "Write Time Out is not Configured as Its a invalid parameter.");
//        restTemplateWrapper.configureTimeOuts(-1, -1);
//    }
//
//    @Test
//    public void testAddTimeOutsInNegativeInRead() throws Exception {
//        logBackVerifier.expectMessage(Level.WARN, "Read Time Out is not Configured as Its a invalid parameter.");
//        logBackVerifier.expectMessage(Level.WARN, "Write Time Out is not Configured as Its a invalid parameter.");
//        restTemplateWrapper.configureTimeOuts(10, -1);
//        restTemplateWrapper.configureTimeOuts(-1, 10);
//    }
//
////    @Test
////    public void testURLBuilder() throws Exception {
////        String url = new UrlBuilder("http://intuit.test.com").build();
////        assertThat(url).isEqualTo("http://intuit.test.com");
////    }
////
////    @Test
////    public void testURLBuilderWithQueryParam() throws Exception {
////        Map<String, String> queryParams = new WeakHashMap<>();
////        queryParams.put("test", "test");
////
////        Map<String, String> urlFrags = new WeakHashMap<>();
////        urlFrags.put("algorithm", "AES");
////
////        String url = new UrlBuilder("http://intuit.test.com?op=generate&alg={algorithm}").addRequestParam(queryParams).build(urlFrags);
////        assertThat(url).isEqualTo("http://intuit.test.com?op=generate&alg=AES&test=test");
////    }
////
////    @Test
////    public void testURLBuilderWithQueryParamMissing() throws Exception {
////        Map<String, String> queryParams = new WeakHashMap<>();
////        queryParams.put("algorithm", "AES");
////        String url = new UrlBuilder("http://intuit.test.com?op=generate&alg={algorithm}").build();
////        //%7B and %7D for { and } respectively
////        assertThat(url).isEqualTo("http://intuit.test.com?op=generate&alg=%7Balgorithm%7D");
////    }
////
////    @Test
////    public void testURLBuilderWithPathParam() throws Exception {
////        Map<String, String> pathParams = new WeakHashMap<>();
////        pathParams.put("itemName", "test");
////        String url = new UrlBuilder("http://intuit.test.com/keys/{itemName}").build(pathParams);
////        assertThat(url).isEqualTo("http://intuit.test.com/keys/test");
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testURLBuilderWithPathParamNull() throws Exception {
////        new UrlBuilder("http://intuit.test.com/keys/{itemName}").build(null);
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testURLBuilderInvalid() throws Exception {
////        new RestTemplateWrapper.UrlBuilder(null).build();
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testURLBuilderWithEmptyURL() throws Exception {
////        new RestTemplateWrapper.UrlBuilder("").build();
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testURLBuilderWithInvalidURL() throws Exception {
////        new RestTemplateWrapper.UrlBuilder("test.com").build();
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testCallerWithInvalidTargetURL() throws Exception {
////        new Caller<String>(template).call();
////
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testCallerWithInvalidHttpMethod() throws Exception {
////        new Caller<String>(template).to("http://intuit.test.com/").call();
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testCallerWithInvalidTemplate() throws Exception {
////        new Caller<String>(null).to("http://intuit.test.com/").call();
////    }
//
//    @Test
//    public void testCallerWithValidAuth() throws Exception {
//        RestTemplateWrapper.Caller caller = new RestTemplateWrapper.Caller(template).to("http://intuit.test.com/");
//        Map<String, String> headers = (Map<String, String>) ReflectionTestUtils.getField(caller, "headers");
//        assertThat(headers).hasSize(1);
//        assertThat(headers).containsEntry("IDPS-creds", "TEST");
//    }
//
//    @Test
//    public void testCallerWithInValidAuth() throws Exception {
//        logBackVerifier.expectMessage(Level.WARN, "Auth Token Is Ignored.");
//        RestTemplateWrapper.Caller caller = new RestTemplateWrapper.Caller(template).to("http://intuit.test.com/");
//        Map<String, String> headers = (Map<String, String>) ReflectionTestUtils.getField(caller, "headers");
//        assertThat(headers).hasSize(0);
//    }
//
//    @Test
//    public void testCallerWithValidAuthAndOtherHeader() throws Exception {
//        Map<String, String> headerToSet = new WeakHashMap<String, String>() {{
//            put("some_header_key", "some_header_value");
//        }};
//
//        RestTemplateWrapper.Caller caller = new RestTemplateWrapper.Caller(template)
//                .to("http://intuit.test.com/")
//                .withHeaders(headerToSet);
//        Map<String, String> headers = (Map<String, String>) ReflectionTestUtils.getField(caller, "headers");
//        assertThat(headers).hasSize(2);
//        assertThat(headers).containsEntry("IDPS-creds", "TEST");
//        assertThat(headers).containsEntry("some_header_key", "some_header_value");
//    }
//
//    @Test
//    public void testCallerWithValidAuthAndInvalidOtherHeader() throws Exception {
//        logBackVerifier.expectMessage(Level.WARN, "Headers will be ignored.");
//        RestTemplateWrapper.Caller caller = new RestTemplateWrapper.Caller(template)
//                .to("http://intuit.test.com/")
//                .withHeaders(null);
//        Map<String, String> headers = (Map<String, String>) ReflectionTestUtils.getField(caller, "headers");
//        assertThat(headers).hasSize(1);
//        assertThat(headers).containsEntry("IDPS-creds", "TEST");
//    }
//
//    @Test
//    public void testCallerWithInValidAuthAndValidOtherHeader() throws Exception {
//        Map<String, String> headerToSet = new WeakHashMap<String, String>() {{
//            put("some_header_key", "some_header_value");
//        }};
//        RestTemplateWrapper.Caller caller = new RestTemplateWrapper.Caller(template)
//                .to("http://intuit.test.com/")
//                .withHeaders(headerToSet);
//        Map<String, String> headers = (Map<String, String>) ReflectionTestUtils.getField(caller, "headers");
//        assertThat(headers).hasSize(1);
//        assertThat(headers).containsEntry("some_header_key", "some_header_value");
//    }
//
//    @Test
//    public void testCallWithNullRequestBodyAndExpectedResponse() throws Exception {
//        Map<String, String> requestParam = new WeakHashMap<>();
//        requestParam.put("itemName", "test");
//        doReturn(responseEntity).when(template).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any());
//        doReturn("Test").when(responseEntity).getBody();
//
//        RestTemplateWrapper.UrlBuilder urlBuilder = new RestTemplateWrapper.UrlBuilder(RestUtil.buildTargetURL("http://test.intuit.com/", _PI_BY_NAME));
//
//        Object response = new RestTemplateWrapper.Caller(template)
//                .to(urlBuilder.build(requestParam))
//                .make(POST)
//                .responseIsOfType(Object.class).call();
//        assertThat(response).isEqualTo("Test");
//    }
//
//    @Test
//    public void testCallWithValidRequestBodyAndExpectedResponse() throws Exception {
//        Map<String, String> requestParam = new WeakHashMap<>();
//        requestParam.put("itemName", "test");
//        RestTemplateWrapper.UrlBuilder urlBuilder = new RestTemplateWrapper.UrlBuilder(RestUtil.buildTargetURL("http://test.intuit.com/", _PI_BY_NAME));
//
//        Map<String, String> requestBody = new WeakHashMap<String, String>() {{
//            put("some_header_key", "some_header_value");
//        }};
//
//        Map<String, String> headerToSet = new WeakHashMap<String, String>() {{
//            put("some_header_key", "some_header_value");
//        }};
//
//        doReturn(responseEntity).when(template).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any());
//        doReturn("Test").when(responseEntity).getBody();
//
//
//        Object response = new RestTemplateWrapper.Caller(template)
//                .to(urlBuilder.build(requestParam))
//                .make(POST)
//                .with(requestBody)
//                .withHeaders(headerToSet)
//                .responseIsOfType(Object.class).call();
//
//        assertThat(response).isEqualTo("Test");
//    }
//
//    @Test
//    public void testCallWithValidRequestBodyAndHeaderAndExpectedResponse() throws Exception {
//        Map<String, String> requestParam = new WeakHashMap<>();
//        requestParam.put("itemName", "test");
//        RestTemplateWrapper.UrlBuilder urlBuilder = new RestTemplateWrapper.UrlBuilder(RestUtil.buildTargetURL("http://test.intuit.com/", _PI_BY_NAME));
//
//        Map<String, String> requestBody = new WeakHashMap<String, String>() {{
//            put("some_header_key", "some_header_value");
//        }};
//
//
//        doReturn(responseEntity).when(template).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any());
//        doReturn("Test").when(responseEntity).getBody();
//
//
//        Object response = new RestTemplateWrapper.Caller(template)
//                .to(urlBuilder.build(requestParam))
//                .make(POST)
//                .with(requestBody)
//                .withHeaders(requestBody)
//                .responseIsOfType(Object.class).call();
//
//        assertThat(response).isEqualTo("Test");
//    }
//
//    @Test
//    public void testExchangeWithOutResponse() throws Exception {
//
//        Map<String, String> requestParam = new WeakHashMap<>();
//        requestParam.put("itemName", "test");
//
//        doReturn(responseEntity).when(template).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any());
//        doReturn("Test").when(responseEntity).getBody();
//
//        RestTemplateWrapper.UrlBuilder urlBuilder = new RestTemplateWrapper.UrlBuilder(RestUtil.buildTargetURL("http://test.intuit.com/", _PI_BY_NAME));
//
//        Object response = new RestTemplateWrapper.Caller(template)
//                .to(urlBuilder.build(requestParam))
//                .make(POST).call();
//        assertThat(response).isNull();
//    }
//
//    @Test
//    public void testExchangeWithError() {
//        Map<String, String> requestParam = new WeakHashMap<>();
//        requestParam.put("itemName", "test");
//        RestTemplateWrapper.UrlBuilder urlBuilder = new RestTemplateWrapper.UrlBuilder(RestUtil.buildTargetURL("http://test.intuit.com/", _PI_BY_NAME));
//
//        doThrow(clientErrorException).when(template).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any());
//        doReturn("Dummy Message").when(clientErrorException).getMessage();
//        doReturn(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).when(clientErrorException).getStatusCode();
//
//        RestTemplateWrapper.Caller someCallWithOutResponse = new RestTemplateWrapper.Caller(template)
//                .to(urlBuilder.build(requestParam))
//                .make(POST)
//                .withHeaders(requestParam);
//        try {
//            someCallWithOutResponse.call();
//        } catch (HttpClientErrorException e) {
//            assertThat(e.getStatusCode().value()).isEqualTo(HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value());
//        }
//    }
//
//}
