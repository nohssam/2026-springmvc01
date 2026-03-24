package org.study.myproject01.shop.service;

import org.study.myproject01.shop.vo.CartVO;
import org.study.myproject01.shop.vo.ShopVO;

import java.util.List;

public interface ShopService {
    List<ShopVO> getShopList(String category);
    ShopVO getShopDetail(ShopVO shopVO);
    List<CartVO> getCartList(String m_id);
    int getCartChk(String m_id, String p_num);
    void getCartInsert(CartVO cvo);
    void getCartUpdate(String m_id, String p_num);
    void getCartEdit(CartVO cvo);
    void getCartDelete(CartVO cvo);
    void addShop(ShopVO shopVO);
}
