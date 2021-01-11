package com.viettel.convert.service.impl;

import com.viettel.convert.service.ConvertJsonService;
import com.viettel.convert.utils.FormatJsonUtils;
import com.viettel.convert.utils.HttpUtils;
import org.springframework.stereotype.Service;

@Service
public class ConvertJsonServiceImpl implements ConvertJsonService {
    public String convertJson(String url, Object params) {
        return FormatJsonUtils.formatJsonElasticSearch(HttpUtils.getElasticsearchResponse(url, params));
    }
}