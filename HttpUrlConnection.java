/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.http;



import com.pck.http.serializers.HttpSerializer;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlConnection implements  Http{

    private final HttpSerializer serializer;
    private final Network network;

    public  HttpUrlConnection(HttpSerializer serializer, Network network){
        this.serializer = serializer;
        this.network  = network;
    }

    @Override
    public HttpRequest get(String url) {
        return request(url,"GET");
    }

    @Override
    public HttpRequest post(String url) {
        return request(url,"POST");
    }

    @Override
    public HttpRequest put(String url) {
        return request(url,"PUT");
    }

    @Override
    public HttpRequest delete(String url) {
        return request(url,"DELETE");
    }

    @Override
    public HttpRequest request(String url, String method) {
        try {
            return new HttpUrlConnectionRequest(new URL(url), method, serializer, network);
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }
    }
}
