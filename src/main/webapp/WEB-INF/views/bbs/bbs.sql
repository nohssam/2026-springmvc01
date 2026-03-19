CREATE TABLE bbs_t(
                      b_idx 	int not null PRIMARY KEY AUTO_INCREMENT,
                      subject VARCHAR(60),
                      writer	VARCHAR(60),
                      content longtext,
                      f_name VARCHAR(255),
                      pwd	  VARCHAR(255),
                      write_date DATETIME DEFAULT NOW(),
                      hit	   int,
                      active INT DEFAULT 0
);

CREATE TABLE comment_t(
                          c_idx 	INT not null PRIMARY KEY AUTO_INCREMENT,
                          writer	 VARCHAR(20),
                          content  longtext,
                          write_date DATETIME DEFAULT NOW(),
                          pwd	  VARCHAR(255),
                          active INT  DEFAULT 0,
                          b_idx	   INT,
                          CONSTRAINT comm_t_fk FOREIGN KEY (b_idx) REFERENCES bbs_t(b_idx)
);

insert into bbs_t (subject, writer, content, pwd, hit)
VALUES ( '5555', 'admin','5555' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '6666', 'admin','6666' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '7777', 'admin','7777' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '8888', 'admin','8888' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '9999', 'admin','9999' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '10_10_10', 'admin','10_10_10' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '11_11_11', 'admin','11_11_11' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '12번째', 'admin','12번째' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '13번째', 'admin','13번째' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '14번째', 'admin','14번째' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '15번째', 'admin','15번째' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '16번째', 'admin','16번째' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 ),
       ( '17번째', 'admin','17번째' ,'$2a$10$YbluOnEhu6ThYMoAb3v41OD9yNghbm6WkUgt.DkEeTCUxDN0PV8nS' , 0 );
