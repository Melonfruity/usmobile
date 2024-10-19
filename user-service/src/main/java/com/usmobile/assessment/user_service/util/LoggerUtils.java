package com.usmobile.assessment.user_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    // Log at INFO level
    public static void logInfo(String message, Object data) {
        logger.info(formatMessage(message, data));
    }

    // Log at DEBUG level
    public static void logDebug(String message, Object data) {
        logger.debug(formatMessage(message, data));
    }

    // Log at ERROR level
    public static void logError(String message, Object data, Throwable throwable) {
        logger.error(formatMessage(message, data), throwable);
    }

    // Log at WARN level
    public static void logWarn(String message, Object data) {
        logger.warn(formatMessage(message, data));
    }

    // Log at TRACE level
    public static void logTrace(String message, Object data) {
        logger.trace(formatMessage(message, data));
    }

    // Format the log message
    private static String formatMessage(String message, Object data) {
        StringBuilder logMessage = new StringBuilder(message);
        if (data != null) {
            logMessage.append(" | Data: ").append(convertToJson(data));
        }
        return logMessage.toString();
    }

    // Convert object to JSON for better readability
    private static String convertToJson(Object data) {
        try {
            return SerializerUtils.serialize(data);
        } catch (JsonProcessingException e) {
            return "Could not convert data to JSON";
        }
    }
}
