package com.gmail.klausboeing.aws.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    @Override
    public String convert(final LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_DATE_TIME) : null;
    }

    @Override
    public LocalDateTime unconvert(final String formatedDateTime) {
        return formatedDateTime != null ? LocalDateTime.parse(formatedDateTime, DateTimeFormatter.ISO_DATE_TIME) : null;
    }
}
