package com.usmobile.assessment.user_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SerializerUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Serialize an object to a JSON string
    public static String serialize(Object object) throws JsonProcessingException {
        if (object == null) {
            throw new IllegalArgumentException("Object to serialize cannot be null");
        }
        return objectMapper.writeValueAsString(object);
    }

    // Deserialize a JSON string to an object of a specified class
    public static <T> T deserialize(String json, Class<T> clazz) throws IOException {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON string cannot be null or empty");
        }
        return objectMapper.readValue(json, clazz);
    }
}
