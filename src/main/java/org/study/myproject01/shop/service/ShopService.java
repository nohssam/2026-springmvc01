package org.study.myproject01.shop.service;

import org.study.myproject01.shop.vo.ShopVO;

import java.util.List;

public interface ShopService {
    List<ShopVO> getShopList(String category);
    ShopVO getShopDetail(ShopVO shopVO);
}
