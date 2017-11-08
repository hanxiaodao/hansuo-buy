package com.hansuo.search.service;

import com.hansuo.search.pojo.SearchResult;

public interface SearchService {

	public SearchResult search(String queryString,int page,int rows);
}
