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


    private final Credentials credentials;

    public  HttpAuthUrlConnection(HttpSerializer serializer, Network network, Credentials credentials){
        super(serializer,network);
        this.credentials = credentials;
    }

    @Override
    public HttpRequest request(String url, String method) {
        try {
            HttpUrlConnectionRequest request = new HttpUrlConnectionRequest(new URL(url), method, serializer, network);
            request.authenticationEnabled(credentials);
            return  request;
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }
    }
}
