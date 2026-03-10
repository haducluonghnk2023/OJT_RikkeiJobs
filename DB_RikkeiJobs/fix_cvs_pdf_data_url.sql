-- Chạy script này khi KHÔNG có app Spring Boot nào đang chạy (và không có client nào đang lock bảng cvs).
-- Mở MySQL (Workbench, CLI, ...), chọn database db_rikkeijobs rồi chạy:

USE db_rikkeijobs;

ALTER TABLE cvs MODIFY COLUMN pdf_data_url TEXT;

-- Sau đó khởi động lại ứng dụng. Hibernate sẽ thấy cột đã đúng kiểu và không sinh ALTER nữa.
