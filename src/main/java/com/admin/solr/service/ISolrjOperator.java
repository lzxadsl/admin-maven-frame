package com.admin.solr.service;

import java.util.List;

import com.admin.solr.model.SolrProperty;

/**
 * Solrj 常用操作接口
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-11 下午5:22:27
 */
public interface ISolrjOperator {

	/** 
     * 获得搜索结果 
     *  
     * @param propertyDO 
     * @param compositorDO 
     * @param startIndex 
     * @param pageSize 
     * @return 
     * @throws Exception 
     */  
    public List<Object> querySolrResult(Object property,Object compositorDO, Long startIndex, Long pageSize)  throws Exception;  
  
    /** 
     * 获得搜索结果条数 
     *  
     * @param property
     * @param compositorDO 
     * @return 
     * @throws Exception 
     */  
    public Long querySolrResultCount(SolrProperty property,Object compositorDO) throws Exception;  
}
