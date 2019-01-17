package com.suenara.exampleapp.data.cache.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Serializer {

    private final ObjectMapper mapper;

    @Inject Serializer() {
        mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public <T> T deserialize(String string, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(string, typeReference);
    }

}
