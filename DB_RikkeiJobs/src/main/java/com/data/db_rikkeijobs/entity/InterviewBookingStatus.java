package com.data.db_rikkeijobs.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Canonical status for interview/application pipeline.
 *
 * NOTE:
 * - dbValue is the standardized value (snake_case) that we persist & return via API.
 * - We still accept legacy values (e.g. "Enterprise Verified") for backward compatibility.
 */
public enum InterviewBookingStatus {
    PENDING("pending"),
    ENTERPRISE_VERIFIED("enterprise_verified"),
    STUDENT_VERIFIED("student_verified"),
    WAITING_FOR_INTERVIEW_DAY("waiting_for_interview_day"),
    INTERVIEWING("interviewing"),
    WAITING_FOR_RESULT("waiting_for_result"),
    DONE("done"),
    CANCELLED("cancelled");

    private final String dbValue;

    InterviewBookingStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    public static InterviewBookingStatus fromDbValue(String raw) {
        if (raw == null) return null;
        final String trimmed = raw.trim();
        // first: standardized values
        for (InterviewBookingStatus s : values()) {
            if (s.dbValue.equalsIgnoreCase(trimmed)) return s;
        }
        // second: legacy values stored in DB from older versions
        return switch (trimmed.toLowerCase()) {
            case "enterprise verified" -> ENTERPRISE_VERIFIED;
            case "student verified" -> STUDENT_VERIFIED;
            case "waiting for interview day" -> WAITING_FOR_INTERVIEW_DAY;
            case "waiting for result" -> WAITING_FOR_RESULT;
            case "interviewing" -> INTERVIEWING;
            default -> throw new IllegalArgumentException("Unknown InterviewBookingStatus: " + raw);
        };
    }

    /**
     * Accept both legacy string values (dbValue) and enum names (e.g. PENDING),
     * case-insensitive, to be tolerant with API callers.
     */
    @JsonCreator
    public static InterviewBookingStatus fromJson(String raw) {
        if (raw == null) return null;
        final String trimmed = raw.trim();
        // standardized db value (snake_case) OR legacy db value
        try {
            return fromDbValue(trimmed);
        } catch (IllegalArgumentException ignored) {
            // fall through
        }
        // enum name
        for (InterviewBookingStatus s : values()) {
            if (s.name().equalsIgnoreCase(trimmed)) return s;
        }
        throw new IllegalArgumentException("Unknown InterviewBookingStatus: " + raw);
    }
}


