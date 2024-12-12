CREATE TABLE todo (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    completed BOOLEAN DEFAULT FALSE
);

INSERT INTO todo (email, content, completed) VALUES
('admin@admin.com', 'Content 1', FALSE),
('admin@admin.com', 'Content 2', FALSE),
('admin@admin.com', 'Content 3', FALSE);