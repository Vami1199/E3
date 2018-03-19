package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

/**
 * 根据token获取用户信息
 * @author DXJ
 *
 */
@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;
	/*
	//响应类型contentType可以指定produces="application/json;charset=utf-8"，
	//也可以使用常量值 APPLICATION_JSON_UTF8_VALUE
	@RequestMapping(value = "/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		//响应结果之前，要先判断是否为jsonp形式，即判断callback是否有数据
		if (StringUtils.isNoneBlank(callback)) {
			//callback不为空，则是jsonp请求
			//把结果封装成一个js语句响应
			return callback  + "(" + JsonUtils.objectToJson(result) + ");";
			
		}
		return JsonUtils.objectToJson(result);
	}*/
	
	//spring4.1之后可使用以下方法,不用手动拼串
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getuserByToken(@PathVariable String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			//把结果封装成一个jsonp请求
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			//设置jsonp方法为callback
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
	
}
