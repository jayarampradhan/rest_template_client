package com.uimirror.framework.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * Created by jpradhan on 8/24/15.
 */
public class ObjectMapperFactory {
    private static final ObjectMapper _MAPPER = new ObjectMapper();

    private ObjectMapperFactory() {
        //NOP
    }

    public static ObjectMapper getMapper() {
        _MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setAnnotationIntrospector(createJaxbJacksonAnnotationIntrospector());
        return _MAPPER;
    }

    private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {

        final AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        final AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();
        return AnnotationIntrospector.pair(jacksonIntrospector, jaxbIntrospector);
    }
}
