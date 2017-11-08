package com.hansuo.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansuo.common.pojo.ExceptionUtil;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbUser;
import com.hansuo.sso.service.SSOUserService;

@Controller
@RequestMapping("/user")
public class SSOUsreController {

	
	@Autowired
	private SSOUserService sSOUserService;
	/**
	 * 
	* @Title: check 
	* @Description: TODO   验证
	* @param @param param
	* @param @param type
	* @param @param callback
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object check(@PathVariable String param,@PathVariable Integer type,String callback){
		try {
			TaotaoResult result = sSOUserService.checkData(param, type);
			if(StringUtils.isNotBlank(callback)){
				//请求为json调用，需要支持
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
		
	}
	/**
	 * 
	* @Title: register 
	* @Description: TODO 注册
	* @param @param user
	* @param @return    设定文件 
	* @return TaotaoResult    返回类型 
	* @throws
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user){
		
		try {
			TaotaoResult result = sSOUserService.register(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	/**
	 * 
	* @Title: login 
	* @Description: TODO 登陆
	* @param @param username
	* @param @param password
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return TaotaoResult    返回类型 
	* @throws
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			TaotaoResult result = sSOUserService.login(username, password, request, response);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	/**
	 * 
	* @Title: getUserByToken 
	* @Description: TODO	通过token查询用户信息
	* @param @param token
	* @param @param callback
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		try {
			TaotaoResult result = sSOUserService.getUserByToken(token);
			//支持jsonp调用
			if (StringUtils.isNotBlank(callback)) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token, String callback,HttpServletRequest request,
			HttpServletResponse response) {
		try {
			TaotaoResult result = sSOUserService.logout(token, request, response);
			//支持jsonp调用
			if (StringUtils.isNotBlank(callback)) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
}
