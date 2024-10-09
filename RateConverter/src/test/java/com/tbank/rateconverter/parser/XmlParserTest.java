package com.tbank.rateconverter.parser;

import com.tbank.rateconverter.exception.CurrencyNotFoundException;
import com.tbank.rateconverter.model.CurrencyRate;
import com.tbank.rateconverter.model.ValCurs;
import com.tbank.rateconverter.model.Valute;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class XmlParserTest {

    XmlParser xmlParser = new XmlParser();

    @Test
    public void testParseXml() {
        String xml = """
                <ValCurs Date="02.03.2002" name="Foreign Currency Market">
                <Valute ID="R01010">
                <NumCode>036</NumCode>
                <CharCode>AUD</CharCode>
                <Nominal>1</Nominal>
                <Name>Австралийский доллар</Name>
                <Value>16,0102</Value>
                <VunitRate>16,0102</VunitRate>
                </Valute>
                <Valute ID="R01035">
                <NumCode>826</NumCode>
                <CharCode>GBP</CharCode>
                <Nominal>1</Nominal>
                <Name>Фунт стерлингов Соединенного королевства</Name>
                <Value>43,8254</Value>
                <VunitRate>43,8254</VunitRate>
                </Valute>
                </ValCurs>""";
        ValCurs valCurs = xmlParser.parseXml(xml);
        assertNotNull(valCurs);
        assertNotNull(valCurs.getValutes());
    }

    @Test
    public void testFindCurrencyRate_FoundCode() {
        Valute valute = new Valute();
        valute.setCharCode("USD");
        valute.setNominal(1);
        valute.setValue("100,00");
        valute.setVunitRate("1,00");

        ValCurs valCurs = new ValCurs();
        valCurs.setValutes(List.of(valute));

        CurrencyRate rate = xmlParser.findCurrencyRate(valCurs, "USD");
        assertNotNull(rate);
        assertEquals("USD", rate.getCharCode());
        assertEquals(1, rate.getNominal());
        assertEquals(100.00, rate.getValue());
        assertEquals(1.00, rate.getVunitRate());
    }

    @Test
    public void testFindCurrencyRate_NotFoundCode() {
        ValCurs valCurs = new ValCurs();
        Valute valute = new Valute();
        valute.setCharCode("USD");
        valute.setNominal(1);
        valute.setValue("100,00");
        valute.setVunitRate("1,00");
        valCurs.setValutes(List.of(valute)); // Пустой список

        Exception exception = assertThrows(CurrencyNotFoundException.class,
                () -> xmlParser.findCurrencyRate(valCurs, "EUR"));
        assertEquals("Currency code not found: EUR", exception.getMessage());
    }
}
