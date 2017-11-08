package com.hansuo.service;

import java.util.List;

import com.hansuo.pojo.TbItemCat;
import com.hansuo.pojo.TbItemCatExample;

public interface ItemCatService {

	List<TbItemCat> selectByExample(TbItemCatExample example);
}
