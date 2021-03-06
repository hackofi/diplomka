package cz.vse.mapping.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateToStringConverter extends BidirectionalConverter<LocalDate, String> {
    private final Logger l = Logger.getLogger(this.getClass());

    @Override
    public String convertTo(LocalDate localDate, Type<String> type) {
        l.info("convertTo");
        l.info("data: " + localDate + "->" + type);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
        String formattedLocalDate = localDate.format(formatter);
        l.info("formattedLocalDate: " + formattedLocalDate);
        return formattedLocalDate;
    }

    @Override
    public LocalDate convertFrom(String s, Type<LocalDate> type) {
        l.info("convertFrom");
        l.info("data: " + s + "->" + type);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
        formatter = formatter.withLocale(Locale.UK);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(s, formatter);
        l.info("converted date: " + date);
        return date;
    }
}


