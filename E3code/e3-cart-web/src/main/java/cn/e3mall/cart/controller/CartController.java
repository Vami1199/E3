package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;

/**
 * 购物车处理
 * @author DXJ
 *
 */
@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE; 
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//判断用户是否登录
		
		//如果是登录状态，把购物车写入redis
		
			//保存到服务端
			
			//返回逻辑视图
			
		
		//如果未登录使用cookie
		//从cookie中取购物车列表
		List<TbItem> cartList  = getCartListFromCookie(request); 
		//判断商品在商品列表中是否存在
		boolean flag  = true;
		for (TbItem tbItem : cartList) {
			//如果存在数量相加
			flag = false;
			//找到商品，数量相加
			tbItem.setNum(tbItem.getNum()+num);
			//跳出循环
			break;
		}
				
		//如果不存在
		if (!flag) {
			
			//根据商品id查询商品信息。得到一个TbItem对象
			TbItem tbItem = itemService.getItemById(itemId);
			//设置商品数量
			tbItem.setNum(num);
			//取一张图片
			String image = tbItem.getImage();
			if (StringUtils.isNotBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			
			//把商品添加到商品列表
			cartList.add(tbItem);
		}
		
		//写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回添加成功页面
		return "cartSuccess";
	}
	
	/**
	 * 从cookie中取购物车列表的处理
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "cart", true);
		//判断json是否为空
		if (!StringUtils.isNotBlank(json)) {
			return new ArrayList<>();
		}
		//把json转换成商品列表
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
		
	}
	
	
}
