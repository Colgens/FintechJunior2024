package com.tbank.fintech_project.service.converters;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;

public final class ObjectToXmlConverter {
    private static final Logger logger = LoggerFactory.getLogger(ObjectToXmlConverter.class);

    public static <T> void toXML(T object, String filePath) {
        if (object == null) {
            logger.warn("Попытка конвертации пустого объекта в XML");
            return;
        }
        XmlMapper xmlMapper = new XmlMapper();
        logger.debug("Началась конвертация объекта в XML файл: {}", filePath);
        try {
            xmlMapper.writeValue(new File(filePath), object);
            logger.info("XML файл сохранён в: {}", filePath);
        } catch (IOException e) {
            logger.error("Ошибка записи XML файла: {}", e.getMessage());
        }

    }

}
