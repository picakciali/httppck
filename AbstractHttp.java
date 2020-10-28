/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 23:16
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 23:16
 *
 */

package com.pck.httppck;

import com.pck.httppck.network.Network;
import com.pck.httppck.serializers.HttpSerializer;

abstract class AbstractHttp implements Http{


    final HttpSerializer serializer;
    final Network network;


    AbstractHttp(HttpSerializer serializer, Network network){
        this.serializer = serializer;
        this.network    = network;
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
    public abstract HttpRequest request(String url, String method);


}
