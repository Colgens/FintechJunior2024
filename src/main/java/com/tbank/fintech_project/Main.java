package com.tbank.fintech_project;

import com.tbank.fintech_project.model.entities.Location;
import com.tbank.fintech_project.service.converters.ObjectToXmlConverter;
import com.tbank.fintech_project.service.parsers.JsonToObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Начало выполнения программы");

        Location location1 = JsonToObject.parseJsonToObject("city.json", Location.class);
        ObjectToXmlConverter.toXML(location1, "location1.xml");

        Location location2 = JsonToObject.parseJsonToObject("city-error.json", Location.class);
        ObjectToXmlConverter.toXML(location2, "location2.xml");

        logger.info("Завершение выполнения программы");

    }
}
