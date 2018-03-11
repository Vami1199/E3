package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 商品详情 页面展示
 * @author DXJ
 *
 */

public class ItemController {
	@Autowired
	private ItemService  itemService;
	
	@RequestMapping("item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model){
		//调用服务获取商品基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item  = new Item(tbItem);
		//取商品描述信息
		TbItemDesc itemDesc = itemService.geTbItemDesc(itemId);
		model.addAttribute("item",item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
