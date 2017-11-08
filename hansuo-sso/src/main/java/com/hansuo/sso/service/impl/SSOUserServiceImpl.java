package com.hansuo.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.hansuo.common.pojo.JsonUtils;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.common.utils.CookieUtils;
import com.hansuo.mapper.TbUserMapper;
import com.hansuo.pojo.TbUser;
import com.hansuo.pojo.TbUserExample;
import com.hansuo.pojo.TbUserExample.Criteria;
import com.hansuo.sso.component.JedisClient;
import com.hansuo.sso.service.SSOUserService;
@Service
public class SSOUserServiceImpl implements SSOUserService  {
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TaotaoResult checkData(String param, int type) {
		//根据数据类型检查数据
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1,2,3分别代表username,phone,email
		switch(type){
		case 1:
			criteria.andUsernameEqualTo(param);
			break;
		case 2:
			criteria.andPhoneEqualTo(param);
			break;
		case 3:
			criteria.andEmailEqualTo(param);
			break;
		default:
			break;
		}
		//执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//判断查询结果是否为空
		if(list==null||list.isEmpty()){
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}


	@Override
	public TaotaoResult register(TbUser user) {
		//校验数据
		//校验用户名、密码不为空
		if(StringUtils.isBlank(user.getUsername() )|| StringUtils.isBlank(user.getPassword())){
			return TaotaoResult.build(400, "用户名密码不能为空");
			
		}
		//校验数据重复
		//校验用户名
		TaotaoResult result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "用户名重复");
		}
		//校验手机号
		if (user.getPhone() != null) {
			result = checkData(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "手机号重复");
			}
		}
		//校验手机号
		if (user.getEmail() != null) {
			result = checkData(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "邮箱重复");
			}
		}
		//插入数据
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		tbUserMapper.insert(user);
		return TaotaoResult.ok();

	}


	@Override
	public TaotaoResult login(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		//校验用户名密码是否正确
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//取用户信息
		if (list == null || list.isEmpty()) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		//校验密码
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//登录成功
		//生成token		
		String token = UUID.randomUUID().toString();
		//把用户信息写入redis
		//value:user 转 json
		user.setPassword(null);
		jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置session的过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
		//写入cookie
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}

	
	@Override
	public TaotaoResult getUserByToken(String token) {
		// 根据token取用户信息
		String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
		//判断是否查询到结果
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户session已经过期");
		}
		//把json转换成java对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		//更新session的过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
		
		return TaotaoResult.ok(user);

	}


	@Override
	public TaotaoResult logout(String token,HttpServletRequest request,
			HttpServletResponse response) {
		// 根据token取用户信息
	/*	String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
		//判断是否查询到结果
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户session已经过期");
		}
		//把json转换成java对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		//更新session的过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);*/
		jedisClient.del(REDIS_SESSION_KEY + ":" + token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		return TaotaoResult.ok();
		
	}
	
}
