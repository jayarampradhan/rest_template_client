package com.uimirror.framework.http;

import com.uimirror.framework.group.UnitTests;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * This is a collaborative test case for all the error handler and
 * Created by jpradhan on 9/7/15.
 */
@RunWith(MockitoJUnitRunner.class)
@Category({UnitTests.class})
public class CustomResponseErrorHandlerTest {

    private static CustomResponseErrorHandler customResponseErrorHandler;
    @Mock
    private MockClientHttpResponse response;
    @Mock
    private HttpHeaders httpHeaders;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        customResponseErrorHandler = new CustomResponseErrorHandler();
    }

    @Test
    public void testHasError() throws Exception {
        doReturn(HttpStatus.OK).doReturn(HttpStatus.CONFLICT).when(response).getStatusCode();
        assertThat(customResponseErrorHandler.hasError(response)).isFalse();
        assertThat(customResponseErrorHandler.hasError(response)).isTrue();
    }

    @Test(expected = HttpClientErrorException.class)
    public void testHandleError() throws Exception {
        doReturn(HttpStatus.CONFLICT).when(response).getStatusCode();
        doReturn("Already Exists really?").when(response).getStatusText();
        doReturn(httpHeaders).when(response).getHeaders();
        customResponseErrorHandler.handleError(response);
    }
}
