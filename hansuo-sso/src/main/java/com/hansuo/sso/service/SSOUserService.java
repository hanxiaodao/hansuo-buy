package com.hansuo.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbUser;

public interface SSOUserService {

	public TaotaoResult checkData(String param,int type);
	
	public TaotaoResult register(TbUser user);
	
	public TaotaoResult login(String username, String password, HttpServletRequest request,
			HttpServletResponse response);
	
	public TaotaoResult getUserByToken(String token);
	
	public TaotaoResult logout(String token,HttpServletRequest request,
			HttpServletResponse response);
}
