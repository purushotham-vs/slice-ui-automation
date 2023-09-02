package com.slice.helper.lib;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
public class GenericLib {

    /**
     * convert json file to string
     * @param path
     * @return
     * @throws IOException
     */
    public static String convertJsonFileToString(String path) throws IOException {
        try {
            return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            log.info("Exception while converting the file to string");
            ioException.printStackTrace();
            throw new IOException();
        }
    }

    public static String getJsonStringForPayload(Object object){
        String payload = null;
        try {
            payload = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing payload");
        }
        return payload;
    }
}
