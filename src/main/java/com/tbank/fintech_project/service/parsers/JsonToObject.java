package com.tbank.fintech_project.service.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class JsonToObject {
    private static final Logger logger = LoggerFactory.getLogger(JsonToObject.class);

    public static <T> T parseJsonToObject(String resourcePath, Class<T> valueType) {
        URL resource = JsonToObject.class.getClassLoader().getResource(resourcePath);
        if (resource == null) {
            logger.error("Ресурс не найден: {}", resourcePath);
            return null;
        }
        File file = new File(resource.getFile());
        ObjectMapper mapper = new ObjectMapper();
        logger.debug("Начался парсинг JSON файла: {}", resourcePath);
        try {
            T object = mapper.readValue(file, valueType);
            logger.info("Парсинг JSON файла прошёл успешно: {}", object);
            return object;
        } catch (IOException e) {
            logger.error("Ошибка парсинга JSON файла: {}", e.getMessage());
            return null;
        }
    }

}
