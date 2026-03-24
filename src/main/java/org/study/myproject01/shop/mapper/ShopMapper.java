package org.study.myproject01.shop.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.study.myproject01.shop.vo.CartVO;
import org.study.myproject01.shop.vo.ShopVO;

import java.util.List;

@Mapper
public interface ShopMapper {
    List<ShopVO> getShopList(String category);
    ShopVO getShopDetail(ShopVO shopVO);
    List<CartVO> getCartList(String m_id);
    int getCartChk(CartVO cartVO);
    // int getCartChk( Map<String, String> map);

    void getCartInsert(CartVO cvo);
    void getCartUpdate(CartVO cartVO);
    void getCartEdit(CartVO cvo);
    void getCartDelete(CartVO cvo);
    void addShop(ShopVO shopVO);
}
