package com.chengzi.book2read.util;

import com.squareup.okhttp.OkHttpClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtils {
    private static final OkHttpClient client = new OkHttpClient();

    @SneakyThrows
    public static String getRsp(String url) {
        try {
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(url)
                    .header("User-Agent", "curl/7.58.0")
                    .header("Accept", "*/*")
                    .build();

            com.squareup.okhttp.Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.error("getRsp error", e);
            return "";
        }
    }
}
