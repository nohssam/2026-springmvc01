package org.study.myproject01.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.myproject01.shop.service.ShopService;
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
    public String showCart(HttpSession session) {
        try{
            String logInChk = (String) session.getAttribute("logInChk");
            if(logInChk.equals("ok")) {
                return "";
            }else{
                session.setAttribute("logInChk", "required");
                return "members/loginForm";
            }
        }catch (Exception e){
            session.setAttribute("logInChk", "required");
            return "members/loginForm";
        }
    }
}
