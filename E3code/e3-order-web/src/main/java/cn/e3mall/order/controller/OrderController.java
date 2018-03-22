package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

@Controller
public class OrderController {
	@Autowired
	private CartService cartService;
	@RequestMapping("order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		//取用户id
		TbUser user = (TbUser) request.getAttribute("user");
		//根据用户id取收货地址列表
		//使用静态数据
		//取支付方式列表
		//静态数据
		//根据用户id取购物车列表
		List<TbItem> cartList = cartService.getCartList(1);
		//购物车列表传递给jsp
		request.setAttribute("cartList", cartList);
		//返回页面
		return "order-cart";
	}
}
