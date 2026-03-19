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
public class ShopVO {
    private String
    shop_idx     ,
    category    ,
    p_num      ,
    p_name     ,
    p_company  ,
    p_image_s  ,
    p_image_l  ,
    p_content  ,
    p_date     ,
    p_active  ;

    // 이미지
    public MultipartFile file_s, file_l ;

    private int p_price, p_saleprice ;

    // 할인률 계산 메서드
    public int getPercent(){
        return (p_price - p_saleprice) * 100 / p_price;
    }
}
