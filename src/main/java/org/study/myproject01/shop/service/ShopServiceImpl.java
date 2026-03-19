package org.study.myproject01.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.shop.mapper.ShopMapper;
import org.study.myproject01.shop.vo.ShopVO;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public List<ShopVO> getShopList(String category) {
        return shopMapper.getShopList(category);
    }

    @Override
    public ShopVO getShopDetail(ShopVO shopVO) {
        return shopMapper.getShopDetail(shopVO);
    }
}
