package com.viettel.convert.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatJsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(FormatJsonUtils.class);
    private static final String KEY_HITS = BundleUtils.getConfigValue("elasticsearch.json.hits.key");
    private static final String KEY_SOURCE = BundleUtils.getConfigValue("elasticsearch.json.source.key");

    public static String formatJsonElasticSearch(String json) {
        String result = null;
        try {
            Gson gson = new Gson();
            StringBuilder sbJson = new StringBuilder();
            JsonObject jsonObject = gson.fromJson(json, new TypeToken<JsonObject>(){}.getType());
            JsonArray jsonArray = jsonObject.getAsJsonObject(KEY_HITS).getAsJsonArray(KEY_HITS);
            for (int j = 0; j < jsonArray.size(); j++) {
                jsonObject = jsonArray.get(j).getAsJsonObject();
                JsonObject object = jsonObject.getAsJsonObject(KEY_SOURCE);
                sbJson.append(object.toString());
                sbJson.append(",");
            }
            result = "[" + sbJson.substring(0, sbJson.toString().length() - 1) + "]";
        } catch (Exception ex) {
            logger.error("==== error format json:::", ex);
        }
        return result;
    }
}
