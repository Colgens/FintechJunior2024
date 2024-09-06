package com.tbank.fintech_project.service.parsers;

import com.tbank.fintech_project.model.entities.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonToObjectTest {
    @Test
    public void testParseCorrectJsonToObject() {
        Location location1 = JsonToObject.parseJsonToObject("city.json", Location.class);
        Assertions.assertNotNull(location1);
        Assertions.assertEquals(location1.getSlug(), "spb");
        Assertions.assertEquals(location1.getCoords().getLat(), 59.939095);
        Assertions.assertEquals(location1.getCoords().getLon(), 30.315868);
    }

    @Test
    public void testParseIncorrectJsonToObject() {
        Location location1 = JsonToObject.parseJsonToObject("city-error.json", Location.class);
        Assertions.assertNull(location1);
    }
}
