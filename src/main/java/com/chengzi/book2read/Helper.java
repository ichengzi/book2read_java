package com.chengzi.book2read;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class Helper {
    public static String basicGetRequest(String url) throws ClientProtocolException, IOException {
        return Request.Get(url)
                .execute()
                .returnContent()
                .asString();
    }
}
