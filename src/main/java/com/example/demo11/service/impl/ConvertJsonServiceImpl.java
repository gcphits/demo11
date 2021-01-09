package com.example.demo11.service.impl;

import com.example.demo11.service.ConvertJsonService;
import com.example.demo11.utils.FormatJsonUtils;
import com.example.demo11.utils.HttpUtils;
import org.springframework.stereotype.Service;

@Service
public class ConvertJsonServiceImpl implements ConvertJsonService {
    public String convertJson(String url, Object params) {
        return FormatJsonUtils.formatJsonElasticSearch(HttpUtils.getElasticsearchResponse(url, params));
    }
}