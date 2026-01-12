package com.cinema.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for JSON serialization and deserialization operations.
 */
public class JsonUtil {
    
    private static final Logger logger = Logger.getLogger(JsonUtil.class.getName());
    private static final ObjectMapper objectMapper;
    
    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    /**
     * Writes a list of objects to a JSON file.
     */
    public static <T> void writeToFile(List<T> data, String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        objectMapper.writeValue(file, data);
        logger.log(Level.INFO, "Successfully wrote {0} items to {1}", new Object[]{data.size(), filePath});
    }
    
    /**
     * Reads a list of objects from a JSON file.
     */
    public static <T> List<T> readFromFile(String filePath, Class<T> clazz) throws IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            logger.log(Level.WARNING, "File not found: {0}. Returning empty list.", filePath);
            return new ArrayList<>();
        }
        
        List<T> result = objectMapper.readValue(
                file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
        
        logger.log(Level.INFO, "Successfully read {0} items from {1}", new Object[]{result.size(), filePath});
        return result;
    }
    
    /**
     * Converts an object to JSON string.
     */
    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }
    
    /**
     * Converts a JSON string to an object.
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
    
    /**
     * Gets the configured ObjectMapper instance.
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
