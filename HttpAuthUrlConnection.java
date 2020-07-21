/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 20:12
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 21.07.2020 19:47
 *
 */

package com.pck.httppck;

import android.content.Context;
import android.util.Log;

import com.pck.candostum.ui.base.BaseActivity;
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

            return new HttpUrlConnectionRequest(new URL(url), method, serializer, network)
            .setContext(context)
            .authenticationEnabled(credentials)
            .logStatus(log);
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }
    }
}
