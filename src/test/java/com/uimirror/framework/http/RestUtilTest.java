package com.uimirror.framework.http;

import com.uimirror.framework.group.UnitTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jpradhan on 9/7/15.
 */
@Category({UnitTests.class})
public class RestUtilTest {

    @Test
    public void testIsError() throws Exception {
        assertThat(RestUtil.isError(HttpStatus.CONFLICT)).isTrue();
        assertThat(RestUtil.isError(HttpStatus.ACCEPTED)).isFalse();
    }

    @Test
    public void testBuildTargetURLValid() throws Exception {
        String expectedUrl = "http://test.com/test";
        assertThat(RestUtil.buildTargetURL("http://test.com/", "/test")).isEqualTo(expectedUrl);
        assertThat(RestUtil.buildTargetURL("http://test.com/", "test")).isEqualTo(expectedUrl);
        assertThat(RestUtil.buildTargetURL("http://test.com", "test")).isEqualTo(expectedUrl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildTargetURLInValidContext() throws Exception {
        RestUtil.buildTargetURL(null, "/test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildTargetURLInValidResourcePath() throws Exception {
        RestUtil.buildTargetURL("http://test.com/", null);
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<RestUtil> constructor = RestUtil.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
