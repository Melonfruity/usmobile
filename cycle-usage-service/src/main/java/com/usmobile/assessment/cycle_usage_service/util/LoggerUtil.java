package com.usmobile.assessment.cycle_usage_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    // Log at INFO level
    public static void logInfo(String message, Object... data) {
        logger.info(formatMessage(message, data));
    }

    public static void logInfo(String message) { logger.info(message); }

    // Log at DEBUG level
    public static void logDebug(String message, Object data) {
        logger.debug(formatMessage(message, data));
    }

    public static void logDebug(String message) { logger.info(message); }

    // Log at ERROR level
    public static void logError(String message, Object data, Throwable throwable) {
        logger.error(formatMessage(message, data), throwable);
    }
    public static void logError(String message) { logger.info(message); }

    // Log at WARN level
    public static void logWarn(String message, Object data) {
        logger.warn(formatMessage(message, data));
    }

    public static void logWarn(String message) { logger.info(message); }

    // Log at TRACE level
    public static void logTrace(String message, Object data) {
        logger.trace(formatMessage(message, data));
    }

    public static void logTrace(String message) { logger.info(message); }

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
            return SerializerUtil.serialize(data);
        } catch (JsonProcessingException e) {
            return "Could not convert data to JSON";
        }
    }
}
