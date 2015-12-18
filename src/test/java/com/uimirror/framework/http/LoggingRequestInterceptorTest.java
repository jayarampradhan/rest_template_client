package com.uimirror.framework.http;

import ch.qos.logback.classic.Level;
import com.uimirror.framework.LogBackVerifier;
import com.uimirror.framework.group.UnitTests;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

import static org.mockito.Mockito.doReturn;

/**
 * Created by jpradhan on 9/7/15.
 */
@RunWith(MockitoJUnitRunner.class)
@Category({UnitTests.class})
public class LoggingRequestInterceptorTest {

    private static LoggingRequestInterceptor loggingRequestInterceptor;
    @Rule
    public LogBackVerifier logBackVerifier = new LogBackVerifier();
    @Mock
    private ClientHttpRequestExecution execution;
    @Mock
    private MockClientHttpRequest request;
    @Mock
    private MockClientHttpResponse response;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        loggingRequestInterceptor = new LoggingRequestInterceptor();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogIntercept() throws Exception {
        logBackVerifier.expectMessage(Level.DEBUG, "Request Header null");
        doReturn(response).when(execution).execute(request, new byte[0]);
        loggingRequestInterceptor.intercept(request, new byte[0], execution);
    }
}
