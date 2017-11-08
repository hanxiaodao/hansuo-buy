package com.hansuo.portal.service;

import com.hansuo.portal.pojo.SearchResult;

public interface SearchService {
	
	public SearchResult search(String keyword, int page, int rows);
}
