package com.chengzi.book2read.util;

import com.squareup.okhttp.OkHttpClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

@Slf4j
public class Helper {
    public static String basicGetRequest(String url) throws IOException {
        return Request.Get(url)
                .execute()
                .returnContent()
                .asString(Charset.forName("gbk"));
    }

    public static String basicGetRequest(String url, Charset charset) throws IOException {
        try {
            return Request.Get(url)
                    .execute()
                    .returnContent()
                    .asString(charset);
        } catch (Exception e) {
            log.error("basicGetRequest", e);
            return "";
        }
    }

    private static OkHttpClient client = new OkHttpClient();

    @SneakyThrows
    public static String getRsp(String url) {
        try {
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(url)
                    .header("User-Agent","curl/7.58.0")
                    .header("Accept","*/*")
                    .build();

            com.squareup.okhttp.Response response = client.newCall(request).execute();
            String body = response.body().string();
            System.out.println(body);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String str = "\r\n" + sw.toString() + "\r\n";
            sw.close();
            pw.close();
            return str;
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }
    }

    public static String DoGet(String urlstr) throws MalformedURLException, IOException {
        URL url = new URL(urlstr);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer buffer = new StringBuffer();
        String line;

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();

        return buffer.toString();
    }

    public static String DoGet2(String urlstr) throws MalformedURLException, IOException {
        URL url = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");

//        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
//        writer.write(URLEncoder.encode(jsonObj.toString(), "UTF-8"));
//        writer.close();

        int respCode = conn.getResponseCode(); // New items get NOT_FOUND on PUT
        if (respCode == HttpURLConnection.HTTP_OK) {
            StringBuffer response = new StringBuffer();
            String line;

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } else {
            return "error" + respCode;
        }
    }
}
