package org.study.myproject01.shop.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.shop.vo.ShopVO;

import java.util.List;

@Mapper
public interface ShopMapper {
    List<ShopVO> getShopList(String category);
    ShopVO getShopDetail(ShopVO shopVO);
}
