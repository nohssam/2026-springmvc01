package org.study.myproject01.guestbook.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

// lombok 이 자동으로 getter, setter, 기본생성자, 모든 변수를 포함하는 생성자
@Getter //getter만
@Setter // setter만
// @Data  // getter/setter 모두
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 변수를 포함하는 생성자
public class GuestBookVO {
    // f_name : DB 컬럼 이름과 같음 , db에 저장
    // file_name ; jsp 에서 controller에 올때 사용할 이름
    private String    g_idx, g_writer, g_subject ,
            g_email , g_pwd , g_content, g_regdate, g_active, f_name;

    private MultipartFile file_name;
}
