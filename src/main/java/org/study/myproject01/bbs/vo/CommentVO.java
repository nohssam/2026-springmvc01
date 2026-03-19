package org.study.myproject01.bbs.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
   private String 	c_idx ,  writer	,   content  ,   write_date ,   pwd	 ,   active,  b_idx ;
}
