package org.study.myproject01.bbs.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BBSVO {
    private String 	b_idx ,
    subject,
    writer,
    content ,
    f_name ,
    pwd	  ,
    write_date ,
    hit	   ,
    active ;

    private MultipartFile file_name ;

}
