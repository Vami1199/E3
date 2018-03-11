package cn.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注册功能Controller
 * 
 * @author DXJ
 *
 */
@Controller
public class RegisterController {
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
}
