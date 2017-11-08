package com.hansuo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.EasyUITree;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.mapper.TbContentCategoryMapper;
import com.hansuo.pojo.TbContentCategory;
import com.hansuo.pojo.TbContentCategoryExample;
import com.hansuo.pojo.TbContentCategoryExample.Criteria;
import com.hansuo.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITree> getContentCatList(Long parentId) {
		//根据paentId查询子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//转换成easyuitree列表
		List<EasyUITree> resultList = new ArrayList<>();
		for (TbContentCategory contentCategory : list) {
			EasyUITree eu = new EasyUITree();
			eu.setId(contentCategory.getId());
			eu.setText(contentCategory.getName());
			eu.setState(contentCategory.getIsParent()?"closed":"open");
			resultList.add(eu);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertCatgory(Long parentId, String name) {
		//创建一个pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		contentCategory.setIsParent(false);
		//'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入数据
		contentCategoryMapper.insert(contentCategory);
		//取返回的主键
		Long id = contentCategory.getId();
		//判断父节点的isparent属性
		//查询父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		//返回主键
		return TaotaoResult.ok(id);
	}

		
}
