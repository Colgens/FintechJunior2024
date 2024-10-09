package com.tbank.rateconverter.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tbank.rateconverter.exception.CurrencyNotFoundException;
import com.tbank.rateconverter.model.CurrencyRate;
import com.tbank.rateconverter.model.ValCurs;
import com.tbank.rateconverter.model.Valute;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.logging.Logger;

@Component
public class XmlParser {

    private static final Logger logger = Logger.getLogger(XmlParser.class.getName());

    public ValCurs parseXml(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(new StringReader(xml), ValCurs.class);
        } catch (Exception e) {
            logger.severe("Error parsing XML: " + e.getMessage());
            throw new RuntimeException("Error parsing XML", e);
        }
    }

    public CurrencyRate findCurrencyRate(ValCurs valCurs, String code) {
        for (Valute valute : valCurs.getValutes()) {
            if (valute.getCharCode().equals(code)) {
                int nominal = valute.getNominal();
                double value = Double.parseDouble(valute.getValue().replace(",", "."));
                double vunitRate = Double.parseDouble(valute.getVunitRate().replace(",", "."));
                return new CurrencyRate(valute.getCharCode(), nominal, value, vunitRate);
            }
        }
        throw new CurrencyNotFoundException("Currency code not found: " + code);
    }
}