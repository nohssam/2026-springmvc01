package org.study.myproject01.board.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
    private String     b_idx ,    writer,    title ,    content ,    pwd ,    hit ,
    b_groups  ,    b_step  ,    b_lev  ,    f_name,    regdate ,    active ;
    private MultipartFile file_name ;
}
