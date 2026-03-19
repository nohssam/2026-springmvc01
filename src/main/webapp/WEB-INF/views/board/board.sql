CREATE TABLE board(
    b_idx INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    writer VARCHAR(60) NOT NULL,
    title VARCHAR(60) NOT NULL,
    content LONGTEXT NOT NULL,
    pwd  VARCHAR(255) NOT NULL,
    hit INT ,
    b_groups INT ,
    b_step INT ,
    b_lev INT ,
    f_name VARCHAR(255),
    regdate DATETIME DEFAULT NOW(),
    active int  default 0
);
