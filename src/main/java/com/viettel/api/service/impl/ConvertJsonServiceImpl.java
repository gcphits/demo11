package com.viettel.api.service.impl;

import com.viettel.api.service.ConvertJsonService;
import com.viettel.api.utils.FormatJsonUtils;
import com.viettel.api.utils.HttpUtils;
import org.springframework.stereotype.Service;

@Service
public class ConvertJsonServiceImpl implements ConvertJsonService {
    public String convertJson(String url, Object params) {
        return FormatJsonUtils.formatJsonElasticSearch(HttpUtils.getElasticsearchResponse(url, params));
    }
}