package org.study.myproject01.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartVO {
    private String
    cart_idx ,
    p_num  ,
    p_name ,
    p_price ,
    p_saleprice,
    p_su  ,
    m_id ;
}
