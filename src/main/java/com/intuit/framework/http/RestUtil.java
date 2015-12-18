package com.intuit.framework.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

/**
 * @author jpradhan
 *         12/15/15.
 */
public class RestUtil {
    private static final String STR_TO_SIGN = "get_temporary_credential?api_key_id={0}&nonce={1}&time={2}";
    private static Logger LOG = LoggerFactory.getLogger(RestUtil.class);

    private RestUtil() {
        //NOP
    }

    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

    public static String buildTargetURL(String contextUrl, String resourcePath) {
        Assert.hasText(contextUrl, "IDPS Service URL Is Invalid");
        Assert.hasText(resourcePath, "IDPS Service Resource Path Is Invalid");
        if (resourcePath.charAt(0) == '/') {
            resourcePath = resourcePath.substring(1);
        }
        if (contextUrl.charAt(contextUrl.length() - 1) != '/') {
            contextUrl += "/";
        }
        return contextUrl + resourcePath;
    }

}
