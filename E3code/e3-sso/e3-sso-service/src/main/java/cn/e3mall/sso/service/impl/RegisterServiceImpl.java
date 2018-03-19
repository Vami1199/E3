package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;
/**
 * 用户注册处理
 * @author DXJ
 *
 */
@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	@Override
	public E3Result checkData(String param, int type) {
		//根据不同的 type生成不同的查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1:用户名 2：手机号 3:邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		}else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		}else if (type == 3) {
			criteria.andEmailEqualTo(param);
		}else {
			return E3Result.build(400, "数据类型错误");
		};
		//执行查询
		List<TbUser> list= userMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			//结果中包含数据 false
			return E3Result.ok(false);
		}
		//结果中不包含数据  true 
		return E3Result.ok(true);
	}
	
	@Override
	public E3Result register(TbUser user) {
		 //校验数据有效性
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPhone()) 
				|| StringUtils.isBlank(user.getPassword())) {
			return E3Result.build(400, "用户数据不完整，请重新注册！");
		}
		//1:用户名 2:手机号 3：邮箱
		E3Result result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "此用户名已被占用");
		}
		result = checkData(user.getPhone(), 2);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "手机号被占用");
		}
		//补全pojo的属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码进行MD5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//把用户数据插入到数据库中
		userMapper.insert(user);
		//返回添加成功
		return E3Result.ok();
	}

}
