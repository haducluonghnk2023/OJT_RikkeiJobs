package com.data.db_rikkeijobs.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class InterviewBookingStatusConverter implements AttributeConverter<InterviewBookingStatus, String> {
    @Override
    public String convertToDatabaseColumn(InterviewBookingStatus attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public InterviewBookingStatus convertToEntityAttribute(String dbData) {
        return InterviewBookingStatus.fromDbValue(dbData);
    }
}


