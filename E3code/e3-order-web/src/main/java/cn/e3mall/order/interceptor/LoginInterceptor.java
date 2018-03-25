package cn.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
/*
 * 用户登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${SSO_URL}")
	private String SSO_URL;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token");
		//判断token是否存在
		if (StringUtils.isBlank(token)) {
			//token不存在，未登录状态，跳转至sso登录界面。用户登录成功后跳转到当前请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirect-" + request.getRequestURL());
			//拦截
			return false;
		}
		//token存在，需要调用sso系统的服务，根据token获取用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		//如果取不到用户信息，说明token已经过期，需要登录
		if (e3Result.getStatus()!=200) {
			response.sendRedirect(SSO_URL + "/page/login?redirect-" + request.getRequestURL());
			return false;
		}
		//如果取到用户信息，说明是登录状态，需要把用户信息写入request
		TbUser user  = (TbUser) e3Result.getData();
		request.setAttribute("user", user);
		//判断cookie中是否有购物车数据，如果有合并到服务器
		String jsonCartList = CookieUtils.getCookieValue(request, "cart",true);
		if (StringUtils.isNotBlank(jsonCartList)) {
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}


}
