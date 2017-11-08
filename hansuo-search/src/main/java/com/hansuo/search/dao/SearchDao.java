package com.hansuo.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.hansuo.search.pojo.SearchResult;

public interface SearchDao {
	
	public SearchResult search(SolrQuery query);
}
