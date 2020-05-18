/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 23:06
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 23:06
 *
 */

package com.pck.httppck;

import com.pck.httppck.serializers.HttpSerializer;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("WeakerAccess")
public class HttpAuthUrlConnection extends  AbstractHttp {


    private final AuthReseource authReseource;

    public  HttpAuthUrlConnection(HttpSerializer serializer, Network network,AuthReseource authReseource){
        super(serializer,network);
        this.authReseource = authReseource;
    }

    @Override
    public HttpRequest request(String url, String method) {
        try {
            HttpUrlConnectionRequest request = new HttpUrlConnectionRequest(new URL(url), method, serializer, network);
            request.authenticationEnabled(authReseource);
            return  request;
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }
    }
}
