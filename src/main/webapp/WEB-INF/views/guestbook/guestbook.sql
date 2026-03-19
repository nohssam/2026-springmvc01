CREATE TABLE guestbook(
 g_idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 g_writer VARCHAR(50),
 g_subject VARCHAR(50),
 g_email VARCHAR(50),
 g_pwd VARCHAR(255) NOT NULL,
 g_content VARCHAR(4000),
 g_regdate DATETIME,
 f_name VARCHAR(255),
 g_active TINYINT DEFAULT 0
);