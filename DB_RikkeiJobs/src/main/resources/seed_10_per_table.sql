-- Seed data: 10 rows per table for DB_RikkeiJobs (MySQL)
-- Target DB: db_rikkeijobs (see application-dev.yml)
--
-- Notes:
-- - Uses explicit IDs (1..10) to make FK relationships deterministic.
-- - Password hash is BCrypt for plaintext "password" (works with BCryptPasswordEncoder).
-- - Safe to re-run: it truncates tables first (data loss).

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE interview_booking_update_times;
TRUNCATE TABLE interview_booking_ranks;
TRUNCATE TABLE interview_bookings;

TRUNCATE TABLE user_save_jobs;
TRUNCATE TABLE user_foreign_languages;
TRUNCATE TABLE user_certificates;

TRUNCATE TABLE certificate_type_values;
TRUNCATE TABLE certificate_types;

TRUNCATE TABLE cvs;
TRUNCATE TABLE cv_languages;

TRUNCATE TABLE job_requirements;
TRUNCATE TABLE job_benefits;
TRUNCATE TABLE job_ranks;
TRUNCATE TABLE job_descriptions;
TRUNCATE TABLE jobs;

TRUNCATE TABLE enterprises;
TRUNCATE TABLE carousel;
TRUNCATE TABLE users;

SET FOREIGN_KEY_CHECKS = 1;

-- ========== users (10) ==========
INSERT INTO users (
  id, first_name, last_name, email, user_name, full_name, password, password_hash,
  status, create_at, update_at, delete_at,
  address, phone, role, is_locked, permission_list, gender, level,
  skills, years_of_experience, avatar, position, birthdate
) VALUES
(1, 'Admin', 'One', 'admin1@rikkeijobs.local', 'admin1', 'Admin One',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Hanoi', '0900000001', 'admin', 0, 'ALL', 'male', 'senior',
 'Java,Spring,MySQL', 6, 'https://picsum.photos/seed/u1/200', 'Admin', '1995-01-01'),
(2, 'Admin', 'Two', 'admin2@rikkeijobs.local', 'admin2', 'Admin Two',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Hanoi', '0900000002', 'admin', 0, 'ALL', 'female', 'mid',
 'Vue,Node,SQL', 4, 'https://picsum.photos/seed/u2/200', 'Admin', '1996-02-02'),
(3, 'User', 'Three', 'user3@rikkeijobs.local', 'user3', 'User Three',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'HCM', '0900000003', 'user', 0, NULL, 'male', 'junior',
 'HTML,CSS,JS', 1, 'https://picsum.photos/seed/u3/200', 'Frontend', '2001-03-03'),
(4, 'User', 'Four', 'user4@rikkeijobs.local', 'user4', 'User Four',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Danang', '0900000004', 'user', 0, NULL, 'female', 'junior',
 'React,TypeScript', 2, 'https://picsum.photos/seed/u4/200', 'Frontend', '2000-04-04'),
(5, 'User', 'Five', 'user5@rikkeijobs.local', 'user5', 'User Five',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Hanoi', '0900000005', 'user', 0, NULL, 'male', 'mid',
 'Java,Spring', 3, 'https://picsum.photos/seed/u5/200', 'Backend', '1999-05-05'),
(6, 'User', 'Six', 'user6@rikkeijobs.local', 'user6', 'User Six',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'HCM', '0900000006', 'user', 0, NULL, 'female', 'mid',
 'Python,Django', 3, 'https://picsum.photos/seed/u6/200', 'Backend', '1998-06-06'),
(7, 'User', 'Seven', 'user7@rikkeijobs.local', 'user7', 'User Seven',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Hue', '0900000007', 'user', 0, NULL, 'male', 'senior',
 'DevOps,Docker,K8s', 7, 'https://picsum.photos/seed/u7/200', 'DevOps', '1994-07-07'),
(8, 'User', 'Eight', 'user8@rikkeijobs.local', 'user8', 'User Eight',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Hanoi', '0900000008', 'user', 0, NULL, 'female', 'junior',
 'QA,Automation', 1, 'https://picsum.photos/seed/u8/200', 'QA', '2002-08-08'),
(9, 'User', 'Nine', 'user9@rikkeijobs.local', 'user9', 'User Nine',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'HCM', '0900000009', 'user', 0, NULL, 'male', 'mid',
 'Data,SQL', 4, 'https://picsum.photos/seed/u9/200', 'Data', '1997-09-09'),
(10, 'User', 'Ten', 'user10@rikkeijobs.local', 'user10', 'User Ten',
 '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHIHnYy7iYvVQfZ9xkPpQUPVHtV6n2l6',
 'Active', NOW(), NOW(), NULL,
 'Danang', '0900000010', 'user', 0, NULL, 'female', 'senior',
 'Mobile,Flutter', 5, 'https://picsum.photos/seed/u10/200', 'Mobile', '1996-10-10');

-- ========== carousel (10) ==========
INSERT INTO carousel (id, title, `index`, img_url, status) VALUES
(1, 'Banner 1', 1, 'https://picsum.photos/seed/c1/1200/400', 'active'),
(2, 'Banner 2', 2, 'https://picsum.photos/seed/c2/1200/400', 'active'),
(3, 'Banner 3', 3, 'https://picsum.photos/seed/c3/1200/400', 'active'),
(4, 'Banner 4', 4, 'https://picsum.photos/seed/c4/1200/400', 'inactive'),
(5, 'Banner 5', 5, 'https://picsum.photos/seed/c5/1200/400', 'active'),
(6, 'Banner 6', 6, 'https://picsum.photos/seed/c6/1200/400', 'active'),
(7, 'Banner 7', 7, 'https://picsum.photos/seed/c7/1200/400', 'inactive'),
(8, 'Banner 8', 8, 'https://picsum.photos/seed/c8/1200/400', 'active'),
(9, 'Banner 9', 9, 'https://picsum.photos/seed/c9/1200/400', 'active'),
(10, 'Banner 10', 10, 'https://picsum.photos/seed/c10/1200/400', 'active');

-- ========== enterprises (10) ==========
INSERT INTO enterprises (
  id, title, email, company_size, phone_number, industry,
  introduction, website_url, facebook_url, linkedin_url, twitter_url,
  business_license, address, user_id, avatar, status
) VALUES
(1, 'Rikkei Enterprise 1', 'ent1@rikkeijobs.local', '50-100', '0911000001', 'Software',
 'Intro 1', 'https://example.com/1', 'https://facebook.com/1', 'https://linkedin.com/1', 'https://twitter.com/1',
 'BL-0001', 'Hanoi', 1, 'https://picsum.photos/seed/e1/200', 'active'),
(2, 'Rikkei Enterprise 2', 'ent2@rikkeijobs.local', '100-200', '0911000002', 'Fintech',
 'Intro 2', 'https://example.com/2', 'https://facebook.com/2', 'https://linkedin.com/2', 'https://twitter.com/2',
 'BL-0002', 'Hanoi', 2, 'https://picsum.photos/seed/e2/200', 'active'),
(3, 'Rikkei Enterprise 3', 'ent3@rikkeijobs.local', '10-50', '0911000003', 'E-commerce',
 'Intro 3', 'https://example.com/3', 'https://facebook.com/3', 'https://linkedin.com/3', 'https://twitter.com/3',
 'BL-0003', 'HCM', 1, 'https://picsum.photos/seed/e3/200', 'active'),
(4, 'Rikkei Enterprise 4', 'ent4@rikkeijobs.local', '200-500', '0911000004', 'Healthcare',
 'Intro 4', 'https://example.com/4', 'https://facebook.com/4', 'https://linkedin.com/4', 'https://twitter.com/4',
 'BL-0004', 'Danang', 2, 'https://picsum.photos/seed/e4/200', 'active'),
(5, 'Rikkei Enterprise 5', 'ent5@rikkeijobs.local', '50-100', '0911000005', 'EdTech',
 'Intro 5', 'https://example.com/5', 'https://facebook.com/5', 'https://linkedin.com/5', 'https://twitter.com/5',
 'BL-0005', 'Hue', 1, 'https://picsum.photos/seed/e5/200', 'active'),
(6, 'Rikkei Enterprise 6', 'ent6@rikkeijobs.local', '10-50', '0911000006', 'Software',
 'Intro 6', 'https://example.com/6', 'https://facebook.com/6', 'https://linkedin.com/6', 'https://twitter.com/6',
 'BL-0006', 'Hanoi', 2, 'https://picsum.photos/seed/e6/200', 'active'),
(7, 'Rikkei Enterprise 7', 'ent7@rikkeijobs.local', '100-200', '0911000007', 'Logistics',
 'Intro 7', 'https://example.com/7', 'https://facebook.com/7', 'https://linkedin.com/7', 'https://twitter.com/7',
 'BL-0007', 'HCM', 1, 'https://picsum.photos/seed/e7/200', 'active'),
(8, 'Rikkei Enterprise 8', 'ent8@rikkeijobs.local', '500+', '0911000008', 'Banking',
 'Intro 8', 'https://example.com/8', 'https://facebook.com/8', 'https://linkedin.com/8', 'https://twitter.com/8',
 'BL-0008', 'Hanoi', 2, 'https://picsum.photos/seed/e8/200', 'active'),
(9, 'Rikkei Enterprise 9', 'ent9@rikkeijobs.local', '50-100', '0911000009', 'Retail',
 'Intro 9', 'https://example.com/9', 'https://facebook.com/9', 'https://linkedin.com/9', 'https://twitter.com/9',
 'BL-0009', 'Danang', 1, 'https://picsum.photos/seed/e9/200', 'active'),
(10, 'Rikkei Enterprise 10', 'ent10@rikkeijobs.local', '10-50', '0911000010', 'Software',
 'Intro 10', 'https://example.com/10', 'https://facebook.com/10', 'https://linkedin.com/10', 'https://twitter.com/10',
 'BL-0010', 'HCM', 2, 'https://picsum.photos/seed/e10/200', 'active');

-- ========== jobs (10) ==========
INSERT INTO jobs (
  id, title, quantity, gender, skills, salary_current, salary,
  province, district, image, address,
  working_time, deadline, industry, enterprise_id, flight, update_date
) VALUES
(1, 'Java Backend Developer', 3, 'any', 'Java,Spring', '20M', '20-30M',
 'Hanoi', 'Cau Giay', 'https://picsum.photos/seed/j1/600/300', 'Hanoi', 'Mon-Fri', '2026-12-31', 'Software', 1, 'A', NOW()),
(2, 'Frontend Vue Developer', 2, 'any', 'Vue,JS', '18M', '18-25M',
 'Hanoi', 'Nam Tu Liem', 'https://picsum.photos/seed/j2/600/300', 'Hanoi', 'Mon-Fri', '2026-12-31', 'Software', 2, 'B', NOW()),
(3, 'QA Engineer', 2, 'any', 'QA,Automation', '15M', '15-20M',
 'HCM', 'District 1', 'https://picsum.photos/seed/j3/600/300', 'HCM', 'Mon-Fri', '2026-12-31', 'Software', 3, 'C', NOW()),
(4, 'DevOps Engineer', 1, 'any', 'Docker,K8s', '25M', '25-40M',
 'Danang', 'Hai Chau', 'https://picsum.photos/seed/j4/600/300', 'Danang', 'Mon-Fri', '2026-12-31', 'Logistics', 4, 'D', NOW()),
(5, 'Data Analyst', 1, 'any', 'SQL,BI', '20M', '20-30M',
 'Hanoi', 'Dong Da', 'https://picsum.photos/seed/j5/600/300', 'Hanoi', 'Mon-Fri', '2026-12-31', 'Fintech', 5, 'E', NOW()),
(6, 'Mobile Flutter', 2, 'any', 'Flutter,Dart', '18M', '18-28M',
 'HCM', 'District 7', 'https://picsum.photos/seed/j6/600/300', 'HCM', 'Mon-Fri', '2026-12-31', 'EdTech', 6, 'F', NOW()),
(7, 'NodeJS Developer', 2, 'any', 'Node,Express', '19M', '19-29M',
 'Hanoi', 'Ba Dinh', 'https://picsum.photos/seed/j7/600/300', 'Hanoi', 'Mon-Fri', '2026-12-31', 'E-commerce', 7, 'G', NOW()),
(8, 'Product Designer', 1, 'any', 'Figma,UX', '17M', '17-25M',
 'Hanoi', 'Thanh Xuan', 'https://picsum.photos/seed/j8/600/300', 'Hanoi', 'Mon-Fri', '2026-12-31', 'Retail', 8, 'H', NOW()),
(9, 'Security Engineer', 1, 'any', 'AppSec,OWASP', '30M', '30-50M',
 'Danang', 'Son Tra', 'https://picsum.photos/seed/j9/600/300', 'Danang', 'Mon-Fri', '2026-12-31', 'Banking', 9, 'I', NOW()),
(10, 'Project Manager', 1, 'any', 'PM,Agile', '35M', '35-55M',
 'HCM', 'District 3', 'https://picsum.photos/seed/j10/600/300', 'HCM', 'Mon-Fri', '2026-12-31', 'Software', 10, 'J', NOW());

-- ========== job_descriptions (10) ==========
INSERT INTO job_descriptions (id, job_id, description) VALUES
(1, 1, 'Build and maintain backend services with Spring Boot.'),
(2, 2, 'Develop UI with Vue 3 and Ant Design Vue.'),
(3, 3, 'Create automated tests and ensure product quality.'),
(4, 4, 'Maintain CI/CD and cloud infrastructure.'),
(5, 5, 'Analyze data and produce BI reports.'),
(6, 6, 'Build mobile apps with Flutter.'),
(7, 7, 'Build APIs with Node.js and Express.'),
(8, 8, 'Design product flows and UI components.'),
(9, 9, 'Improve security posture and review code.'),
(10, 10, 'Manage project delivery and stakeholders.');

-- ========== job_ranks (10) ==========
INSERT INTO job_ranks (id, job_id, rank_value) VALUES
(1, 1, 'Senior'),
(2, 2, 'Mid'),
(3, 3, 'Junior'),
(4, 4, 'Senior'),
(5, 5, 'Mid'),
(6, 6, 'Mid'),
(7, 7, 'Mid'),
(8, 8, 'Junior'),
(9, 9, 'Senior'),
(10, 10, 'Senior');

-- ========== job_benefits (10) ==========
INSERT INTO job_benefits (id, job_id, benefit) VALUES
(1, 1, 'Competitive salary and performance bonus.'),
(2, 2, 'Remote-friendly policy.'),
(3, 3, 'Full insurance and healthcare.'),
(4, 4, 'Training budget and certifications.'),
(5, 5, 'Flexible working hours.'),
(6, 6, 'Latest devices provided.'),
(7, 7, 'Annual company trip.'),
(8, 8, 'Modern office and snacks.'),
(9, 9, 'Security trainings and conferences.'),
(10, 10, 'Leadership coaching.');

-- ========== job_requirements (10) ==========
INSERT INTO job_requirements (id, job_id, requirement) VALUES
(1, 1, '3+ years Java/Spring experience.'),
(2, 2, 'Vue 3 + modern JS fundamentals.'),
(3, 3, 'Testing mindset and attention to detail.'),
(4, 4, 'Hands-on Docker/Kubernetes.'),
(5, 5, 'Strong SQL and BI understanding.'),
(6, 6, 'Flutter and mobile app lifecycle.'),
(7, 7, 'Node.js REST API experience.'),
(8, 8, 'Portfolio of UI/UX work.'),
(9, 9, 'Knowledge of OWASP Top 10.'),
(10, 10, 'Excellent communication and planning.');

-- ========== cv_languages (10) ==========
INSERT INTO cv_languages (id, language, code, status) VALUES
(1, 'Vietnamese', 'vi', 1),
(2, 'English', 'en', 1),
(3, 'Japanese', 'ja', 1),
(4, 'Korean', 'ko', 1),
(5, 'Chinese', 'zh', 1),
(6, 'French', 'fr', 1),
(7, 'German', 'de', 1),
(8, 'Spanish', 'es', 1),
(9, 'Italian', 'it', 1),
(10, 'Thai', 'th', 1);

-- ========== cvs (10) ==========
INSERT INTO cvs (
  id, language_id, language, title, pdf, pdf_data_url, user_id, date, status
) VALUES
(1, 1, 'Vietnamese', 'CV User 1', 'cv1.pdf', NULL, 1, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(2, 2, 'English', 'CV User 2', 'cv2.pdf', NULL, 2, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(3, 3, 'Japanese', 'CV User 3', 'cv3.pdf', NULL, 3, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(4, 4, 'Korean', 'CV User 4', 'cv4.pdf', NULL, 4, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(5, 5, 'Chinese', 'CV User 5', 'cv5.pdf', NULL, 5, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(6, 6, 'French', 'CV User 6', 'cv6.pdf', NULL, 6, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(7, 7, 'German', 'CV User 7', 'cv7.pdf', NULL, 7, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(8, 8, 'Spanish', 'CV User 8', 'cv8.pdf', NULL, 8, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(9, 9, 'Italian', 'CV User 9', 'cv9.pdf', NULL, 9, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1),
(10, 10, 'Thai', 'CV User 10', 'cv10.pdf', NULL, 10, DATE_FORMAT(NOW(), '%Y-%m-%d'), 1);

-- ========== certificate_types (10) ==========
INSERT INTO certificate_types (id, type, language, status, code) VALUES
(1, 'IELTS', 'en', 1, 'IELTS'),
(2, 'TOEIC', 'en', 1, 'TOEIC'),
(3, 'JLPT', 'ja', 1, 'JLPT'),
(4, 'TOPIK', 'ko', 1, 'TOPIK'),
(5, 'HSK', 'zh', 1, 'HSK'),
(6, 'DELF', 'fr', 1, 'DELF'),
(7, 'Goethe', 'de', 1, 'GOETHE'),
(8, 'DELE', 'es', 1, 'DELE'),
(9, 'CELI', 'it', 1, 'CELI'),
(10, 'TCT', 'th', 1, 'TCT');

-- ========== certificate_type_values (10) ==========
INSERT INTO certificate_type_values (id, certificate_type_id, value) VALUES
(1, 1, '7.0'),
(2, 2, '900'),
(3, 3, 'N2'),
(4, 4, '4'),
(5, 5, '5'),
(6, 6, 'B2'),
(7, 7, 'B1'),
(8, 8, 'B2'),
(9, 9, 'B1'),
(10, 10, 'A2');

-- ========== user_certificates (10) ==========
INSERT INTO user_certificates (
  id, certificate_type, certificate_value, received_date, expiration_date, user_id, certificate_id
) VALUES
(1, 'IELTS', '7.0', '2024-01-01', '2026-01-01', 1, 1),
(2, 'TOEIC', '900', '2024-02-01', '2026-02-01', 2, 2),
(3, 'JLPT', 'N2', '2024-03-01', '2029-03-01', 3, 3),
(4, 'TOPIK', '4', '2024-04-01', '2029-04-01', 4, 4),
(5, 'HSK', '5', '2024-05-01', '2029-05-01', 5, 5),
(6, 'DELF', 'B2', '2024-06-01', '2029-06-01', 6, 6),
(7, 'Goethe', 'B1', '2024-07-01', '2029-07-01', 7, 7),
(8, 'DELE', 'B2', '2024-08-01', '2029-08-01', 8, 8),
(9, 'CELI', 'B1', '2024-09-01', '2029-09-01', 9, 9),
(10, 'TCT', 'A2', '2024-10-01', '2029-10-01', 10, 10);

-- ========== user_foreign_languages (10) ==========
INSERT INTO user_foreign_languages (id, user_id, language) VALUES
(1, 1, 'English'),
(2, 2, 'English'),
(3, 3, 'Japanese'),
(4, 4, 'Korean'),
(5, 5, 'Chinese'),
(6, 6, 'French'),
(7, 7, 'German'),
(8, 8, 'Spanish'),
(9, 9, 'Italian'),
(10, 10, 'Thai');

-- ========== user_save_jobs (10) ==========
INSERT INTO user_save_jobs (id, user_id, job_id, created_at) VALUES
(1, 1, 1, NOW()),
(2, 2, 2, NOW()),
(3, 3, 3, NOW()),
(4, 4, 4, NOW()),
(5, 5, 5, NOW()),
(6, 6, 6, NOW()),
(7, 7, 7, NOW()),
(8, 8, 8, NOW()),
(9, 9, 9, NOW()),
(10, 10, 10, NOW());

-- ========== interview_bookings (10) ==========
INSERT INTO interview_bookings (
  id, enterprise_id, job_id, time, date, user_id, status, create_at,
  meeting_link, cancel_reason, interview_mode, description, skills,
  province, district, address, benefits_description, working_time
) VALUES
(1, 1, 1, '09:00', '2026-01-10', 3, 'pending', NOW(),
 'https://meet.example.com/1', NULL, 'online', 'Interview for Java Backend', 'Java,Spring',
 'Hanoi', 'Cau Giay', 'Hanoi', 'Benefits included', 'Mon-Fri'),
(2, 2, 2, '10:00', '2026-01-11', 4, 'pending', NOW(),
 'https://meet.example.com/2', NULL, 'online', 'Interview for Vue Developer', 'Vue,JS',
 'Hanoi', 'Nam Tu Liem', 'Hanoi', 'Benefits included', 'Mon-Fri'),
(3, 3, 3, '11:00', '2026-01-12', 5, 'pending', NOW(),
 'https://meet.example.com/3', NULL, 'offline', 'Interview for QA', 'QA,Automation',
 'HCM', 'District 1', 'HCM', 'Benefits included', 'Mon-Fri'),
(4, 4, 4, '14:00', '2026-01-13', 6, 'pending', NOW(),
 'https://meet.example.com/4', NULL, 'online', 'Interview for DevOps', 'Docker,K8s',
 'Danang', 'Hai Chau', 'Danang', 'Benefits included', 'Mon-Fri'),
(5, 5, 5, '15:00', '2026-01-14', 7, 'pending', NOW(),
 'https://meet.example.com/5', NULL, 'online', 'Interview for Data Analyst', 'SQL,BI',
 'Hanoi', 'Dong Da', 'Hanoi', 'Benefits included', 'Mon-Fri'),
(6, 6, 6, '16:00', '2026-01-15', 8, 'pending', NOW(),
 'https://meet.example.com/6', NULL, 'online', 'Interview for Flutter', 'Flutter',
 'HCM', 'District 7', 'HCM', 'Benefits included', 'Mon-Fri'),
(7, 7, 7, '09:30', '2026-01-16', 9, 'pending', NOW(),
 'https://meet.example.com/7', NULL, 'offline', 'Interview for NodeJS', 'Node,Express',
 'Hanoi', 'Ba Dinh', 'Hanoi', 'Benefits included', 'Mon-Fri'),
(8, 8, 8, '10:30', '2026-01-17', 10, 'pending', NOW(),
 'https://meet.example.com/8', NULL, 'online', 'Interview for Designer', 'Figma,UX',
 'Hanoi', 'Thanh Xuan', 'Hanoi', 'Benefits included', 'Mon-Fri'),
(9, 9, 9, '13:30', '2026-01-18', 3, 'pending', NOW(),
 'https://meet.example.com/9', NULL, 'online', 'Interview for Security', 'AppSec',
 'Danang', 'Son Tra', 'Danang', 'Benefits included', 'Mon-Fri'),
(10, 10, 10, '14:30', '2026-01-19', 4, 'pending', NOW(),
 'https://meet.example.com/10', NULL, 'offline', 'Interview for PM', 'PM,Agile',
 'HCM', 'District 3', 'HCM', 'Benefits included', 'Mon-Fri');

-- ========== interview_booking_ranks (10) ==========
INSERT INTO interview_booking_ranks (id, interview_booking_id, rank_value) VALUES
(1, 1, 'A'),
(2, 2, 'B'),
(3, 3, 'C'),
(4, 4, 'A'),
(5, 5, 'B'),
(6, 6, 'C'),
(7, 7, 'A'),
(8, 8, 'B'),
(9, 9, 'C'),
(10, 10, 'A');

-- ========== interview_booking_update_times (10) ==========
INSERT INTO interview_booking_update_times (id, interview_booking_id, update_time) VALUES
(1, 1, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(2, 2, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(3, 3, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(4, 4, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(5, 5, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(6, 6, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(7, 7, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(8, 8, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(9, 9, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')),
(10, 10, DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'));

-- Done.

