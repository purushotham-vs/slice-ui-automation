package com.slice.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtils {
    @Getter
    private static ObjectMapper mapper;

    /**
     * init
     */
    public static void init(final ObjectMapper mapper) {
        MapperUtils.mapper = mapper;
    }

    /**
     * Serialize
     */
    public static <T> String serialize(ObjectMapper mapper, final T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            log.error("Json Processing exception",e);
        }
        return null;
    }

    /**
     * deserialize
     */
    public static <T> T deserialize(final String data, final Class<T> tClass) {
        try {
            return mapper.readValue(data, tClass);
        } catch (final IOException e) {
            log.error("Json Processing exception",e);
        }
        return null;
    }

//    public static <T,U> T deserialize(final U data, final Class<T> tClass) {
//        try {
//            return mapper.readValue(serialize(data), tClass);
//        } catch (final IOException e) {
//            log.error("Json Processing exception",e);
//        }
//        return null;
//    }

    public static <T> T deserialize(final String data, final TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(data, valueTypeRef);
        } catch (final IOException e) {
            log.error("Json Processing exception",e);
            if(e instanceof JsonMappingException){
                log.info("");
            }
        }
        return null;
    }
}

