package org.study.myproject01.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.myproject01.members.vo.MemberVO;
import org.study.myproject01.shop.service.ShopService;
import org.study.myproject01.shop.vo.CartVO;
import org.study.myproject01.shop.vo.ShopVO;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "ele002") String category, Model model) {
       List<ShopVO> list = shopService.getShopList(category);
       model.addAttribute("list", list);
       return "shop/list";
    }
    @GetMapping("/detail")
    public String detail(ShopVO shopVO, Model model) {
       ShopVO svo = shopService.getShopDetail(shopVO);
       model.addAttribute("svo", svo);
       return "shop/detail";
    }
    @GetMapping("/showCart")
    public String showCart(HttpSession session, Model model) {
        try{
            String logInChk = (String) session.getAttribute("logInChk");
            // 로그인 성공
            if(logInChk.equals("ok")) {
                // 로그인 한 사람의 id가 session 담아 있으므로 가져오자
                MemberVO mvo = (MemberVO) session.getAttribute("mvo");
                // m_id를 가지고 DB에서 cart 테이블에서 정보를 가져오자
                List<CartVO> cartList =  shopService.getCartList(mvo.getM_id());
                model.addAttribute("cartList", cartList);
                return "shop/cartList";
            }else{
                model.addAttribute("logInChk", "required");
                return "members/loginForm";
            }
        }catch (Exception e){
            model.addAttribute("logInChk", "required");
            return "members/loginForm";
        }
    }
    @GetMapping("/addCart")
    public String addCart(ShopVO shopVO,
                          HttpSession session, Model model) {
        // 로그인 한 사람의 id가 session 담아 있으므로 가져오자
        MemberVO mvo = (MemberVO) session.getAttribute("mvo");
        String m_id = mvo.getM_id();

        // shop_idx를 이용해서 제품 정보 먼저 가져오자
        ShopVO svo = shopService.getShopDetail(shopVO);

        // cart 테이블에 m_id가 shop_idx 를 가지고 있는지
        int result = shopService.getCartChk(m_id, svo.getP_num());
        if(result == 0) {
            // cart에 없다, 추가 한다.
            CartVO cvo = new CartVO();
            cvo.setM_id(m_id);
            cvo.setP_num(svo.getP_num());
            cvo.setP_name(svo.getP_name());
            cvo.setP_price(String.valueOf(svo.getP_price()));
            cvo.setP_saleprice(svo.getP_saleprice()+"");
            cvo.setP_su("1");
            shopService.getCartInsert(cvo);
        }else{
            // cart에 있다. 수량 변경 (가지고 있는것에 1증가)
            shopService.getCartUpdate(m_id, svo.getP_num());
        }
        return "redirect:/shop/showCart";
    }
    @PostMapping("/editCart")
    public String editCart(CartVO cvo) {
        shopService.getCartEdit(cvo);
        return "redirect:/shop/showCart";
    }
    @PostMapping("/deleteCart")
    public String deleteCart(CartVO cvo) {
        shopService.getCartDelete(cvo);
        return "redirect:/shop/showCart";
    }
    @GetMapping("/addForm")
    public String addForm() {
        return "shop/addForm";
    }
}
