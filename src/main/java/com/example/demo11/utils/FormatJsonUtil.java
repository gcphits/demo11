package com.example.demo11.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class FormatJsonUtil {
    public static String formatJsonElasticSearch(String json) {
        String result;
        Gson gson = new Gson();
        StringBuilder sbJson = new StringBuilder();
        JsonObject jsonObject = gson.fromJson(json, new TypeToken<JsonObject>(){}.getType());
        JsonArray jsonArray = jsonObject.getAsJsonObject(StringUtil.getValueFromConfig("elasticsearch.json.hits.key")).getAsJsonArray(StringUtil.getValueFromConfig("elasticsearch.json.hits.key"));
        for (int j = 0; j < jsonArray.size(); j++) {
            jsonObject = jsonArray.get(j).getAsJsonObject();
            JsonObject object = jsonObject.getAsJsonObject(StringUtil.getValueFromConfig("elasticsearch.json.source.key"));
            sbJson.append(object.toString());
            sbJson.append(",");
        }
        result = "[" + sbJson.substring(0, sbJson.toString().length() - 1) + "]";
        return result;
    }
}
