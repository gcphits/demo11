package com.example.demo11.utils;

import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static String getElasticsearchResponse(String urlRequest) {
        BufferedReader reader = null;
        String result = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            StringBuilder sbd = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sbd.append(line);
            }

            result = sbd.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) reader.close();
                if (null != connection) connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
