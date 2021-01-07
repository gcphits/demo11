package com.example.demo11.service.impl;

import com.example.demo11.service.ConvertJsonService;
import com.example.demo11.utils.FormatJsonUtil;
import com.example.demo11.utils.HttpUtil;
import org.springframework.stereotype.Service;

@Service
public class ConvertJsonServiceImpl implements ConvertJsonService {
    public String convertJson(String url, String params) {
        return FormatJsonUtil.formatJsonElasticSearch(HttpUtil.getElasticsearchResponse(url));
    }
}