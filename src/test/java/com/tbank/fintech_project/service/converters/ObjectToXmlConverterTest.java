package com.tbank.fintech_project.service.converters;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tbank.fintech_project.model.entities.Coords;
import com.tbank.fintech_project.model.entities.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


public class ObjectToXmlConverterTest {

    @Test
    void testObjectToXMLConvert() {
        Location location = new Location("spb", new Coords(59.939095, 30.315868));
        String filename = "location.xml";

        ObjectToXmlConverter.toXML(location, filename);

        File file = new File(filename);
        Assertions.assertTrue(file.exists() && file.canRead(),
                "Файл должен существовать и быть доступным для чтения");

        XmlMapper xmlMapper = new XmlMapper();
        Assertions.assertDoesNotThrow(() -> xmlMapper.readValue(file, Location.class));

        Location parsedLocation = null;
        try {
            parsedLocation = xmlMapper.readValue(file, Location.class);
        } catch (IOException e) {
            Assertions.fail("IOException не должно быть выброшено");
        }

        Assertions.assertNotNull(parsedLocation);
        Assertions.assertEquals(location.getSlug(), parsedLocation.getSlug());
        Assertions.assertEquals(location.getCoords().getLat(), parsedLocation.getCoords().getLat(), 0.000001);
        Assertions.assertEquals(location.getCoords().getLon(), parsedLocation.getCoords().getLon(), 0.000001);
    }
}