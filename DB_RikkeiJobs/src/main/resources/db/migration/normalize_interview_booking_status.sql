-- Normalize interview_bookings.status from legacy display strings to snake_case codes.
-- Safe to run multiple times.

UPDATE interview_bookings SET status = 'enterprise_verified' WHERE LOWER(status) = 'enterprise verified';
UPDATE interview_bookings SET status = 'student_verified' WHERE LOWER(status) = 'student verified';
UPDATE interview_bookings SET status = 'waiting_for_interview_day' WHERE LOWER(status) = 'waiting for interview day';
UPDATE interview_bookings SET status = 'waiting_for_result' WHERE LOWER(status) = 'waiting for result';
UPDATE interview_bookings SET status = 'interviewing' WHERE LOWER(status) = 'interviewing';


