package org.study.myproject01.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.myproject01.shop.mapper.ShopMapper;
import org.study.myproject01.shop.vo.CartVO;
import org.study.myproject01.shop.vo.ShopVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<CartVO> getCartList(String m_id) {
        return shopMapper.getCartList(m_id);
    }

    @Override
    public int getCartChk(String m_id, String p_num) {
        CartVO cartVO = new CartVO();
        cartVO.setM_id(m_id);
        cartVO.setP_num(p_num);
        return shopMapper.getCartChk(cartVO);
    }
//    public int getCartChk2(String m_id, String p_num) {
//        Map<String, String> map = new HashMap<>();
//        map.put("m_id", m_id);
//        map.put("p_num", p_num);
//        return shopMapper.getCartChk(map);
//    }

    @Override
    public void getCartInsert(CartVO cvo) {
        shopMapper.getCartInsert(cvo);
    }

    @Override
    public void getCartUpdate(String m_id, String p_num) {
        CartVO cartVO = new CartVO();
        cartVO.setM_id(m_id);
        cartVO.setP_num(p_num);
        shopMapper.getCartUpdate(cartVO);
    }

    @Override
    public void getCartEdit(CartVO cvo) {
        shopMapper.getCartEdit(cvo);
    }

    @Override
    public void getCartDelete(CartVO cvo) {
        shopMapper.getCartDelete(cvo);
    }

    @Override
    public void addShop(ShopVO shopVO) {
        shopMapper.addShop(shopVO);
    }


}
