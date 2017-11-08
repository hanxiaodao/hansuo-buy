package com.hansuo.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hansuo.pojo.TbUser;

public interface UserService {

	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) ;
}
