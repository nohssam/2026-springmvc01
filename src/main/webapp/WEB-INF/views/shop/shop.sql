CREATE TABLE shop_t    (
                           shop_idx     INT AUTO_INCREMENT PRIMARY KEY ,
                           category     varchar(100)        Not Null,
                           p_num         varchar(100)        Not Null,
                           p_name         VARCHAR(100)        Not Null,
                           p_company    VARCHAR(100)        Not Null,
                           p_price         int            Not Null,
                           p_saleprice int            Not Null,
                           p_image_s     VARCHAR(100)        Null,
                           p_image_l     VARCHAR(100)        Null,
                           p_content     longtext    Not Null,
                           p_date        DATETIME DEFAULT NOW(),
                           p_active  int  default 0
) ;



insert into shop_t(category,p_num,p_name,p_company,p_price,p_saleprice,p_image_s,p_image_l,p_content,p_date, p_active)
values('sp003', 'RC-113',
       '로체스 인라인','로체스',3200,1150,'pds1.jpg','pds1_z.jpg',
       '바이오맥스 통풍 나일론-HGPU SHELL * 특수 충격 흡수 밑창 * 신발끈 메모리 버클 * 힐 락에 의한 신속한 신발끈 시스템 * 느린 메모리 포말에 의한 편안한 통풍성의 숨쉬는 라이너 * 쿨 통풍 시스템 * 통풍성의 인체공학적 신발밑창 * 손쉬운 엔트리 시스템(신기 편한 입구) * 몰디드 알루미늄 프레임 * 80mm 82a hyper dubbs 휠 * 강철 스페이서 * ABEC-5 베어링',NOW(),0);

insert into shop_t(category,p_num,p_name,p_company,p_price,p_saleprice,p_image_s,p_image_l,p_content,p_date, p_active)
VALUES('ele002', 'vC-13','사니PDP-TV','사니',9200,4750,'pds4.jpg','pds4_z.jpg',
       '질러~ 질러! 무조건 질러봐~ 후회 하지 않아~~',NOW(),0);


create table cart(
                     cart_idx int not null primary KEY AUTO_INCREMENT,
                     p_num VARCHAR(100)  not null ,
                     p_name VARCHAR(100)  not null ,
                     p_price int  not null,
                     p_saleprice int  not null,
                     p_su  INT  not null,
                     m_id  VARCHAR(100) NOT null
);



CREATE TABLE members (
                         m_idx INT AUTO_INCREMENT PRIMARY KEY,      -- 회원 고유번호 (자동 증가)
                         m_id VARCHAR(50) UNIQUE NOT NULL,          -- 사용자 ID (고유값, 필수)
                         m_pw VARCHAR(255) NOT NULL,                -- 암호화된 비밀번호 (필수)
                         m_name VARCHAR(100) NOT NULL,              -- 사용자 이름 (필수)
                         m_addr VARCHAR(255) NOT NULL,              -- 사용자 주소  (필수)
                         m_email VARCHAR(100) NOT NULL,              -- 사용자 email (필수)
                         m_phone VARCHAR(100) NOT NULL,              -- 사용자 폰  (필수)
                         m_reg DATETIME DEFAULT NOW(),               -- 가입일 (기본값: 현재 시간)
                         m_active int not null DEFAULT 0,            -- 탈퇴 (기본값: 0, 탈퇴 : 1)
                         m_active_reg DATETIME DEFAULT NOW(), -- 탈퇴일 (기본값: 현재 시간)
                         sns_email_naver VARCHAR(100) DEFAULT NULL, -- 네이버 이메일 (선택)
                         sns_email_kakao VARCHAR(100) DEFAULT NULL, -- 카카오 이메일 (선택)
                         sns_provider VARCHAR(100) DEFAULT NULL      -- SNS 제공자 정보 (선택)
);

insert into members (m_id, m_pw, m_name, m_addr,m_email, m_phone)
values ('aaaa', '$2a$10$/H0AYgOOwmbi0ruzuDYCKu4hcamWS7/aEPL/DjBiBPvkknASoO9Ji', 'hong', '서울시','hong@naver.com' ,'010-7979-9797');