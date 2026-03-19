package org.study.myproject01.fileup.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileVO {
    // 변수 이름이 반드시 jsp에 넘어오는 이름과 같아야 한다.
    private MultipartFile file_name;
}
