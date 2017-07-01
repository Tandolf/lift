package se.andolf.converter;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Thomas on 2017-06-19.
 */
public class LocalDateConverter implements AttributeConverter<LocalDate, String>{

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String toGraphProperty(LocalDate localDate) {
        return localDate.format(formatter);
    }

    @Override
    public LocalDate toEntityAttribute(String dateAsString) {
        return LocalDate.parse(dateAsString, formatter);
    }
}
