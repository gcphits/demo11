package com.viettel.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String getElasticsearchResponse(String urlRequest, Object params) {
        BufferedReader reader = null;
        String result = null;
        HttpURLConnection connection = null;
        OutputStreamWriter writer = null;
        try {
            URL url = new URL("http://" + urlRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            if (null == params) {
                connection.setRequestMethod("GET");
            } else {
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(params.toString());
                writer.flush();
            }
            connection.connect();

            StringBuilder sbd = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sbd.append(line);
            }

            result = sbd.toString();
        } catch (IOException e) {
            logger.error("==== error get response from elasticsearch:::", e);
        } finally {
            try {
                if (null != reader) reader.close();
                if (null != writer) writer.close();
                if (null != connection) connection.disconnect();
            } catch (IOException e) {
                logger.error("==== error close session:::", e);
            }
        }
        return result;
    }
}
