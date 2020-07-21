/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 23:06
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 23:06
 *
 */

package com.pck.httppck;

import android.content.Context;

import com.pck.httppck.authentication.Credentials;
import com.pck.httppck.network.Network;
import com.pck.httppck.serializers.HttpSerializer;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("WeakerAccess")
public class HttpAuthUrlConnection extends  AbstractHttp {


    private final Credentials credentials;
    private final Context context;

    public  HttpAuthUrlConnection(HttpSerializer serializer, Network network, Credentials credentials, Context context){
        super(serializer,network);
        this.credentials = credentials;
        this.context =context;
    }

    @Override
    public HttpRequest request(String url, String method) {
        try {
            HttpUrlConnectionRequest request = new HttpUrlConnectionRequest(new URL(url), method, serializer, network);
            request.setContext(context);
            request.authenticationEnabled(credentials);
            return  request;
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }
    }
}
