/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 20:12
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 21.07.2020 19:47
 *
 */

package com.pck.httppck;

import android.content.Context;

import com.pck.httppck.authentication.AuthType;
import com.pck.httppck.authentication.Credentials;
import com.pck.httppck.network.Network;
import com.pck.httppck.serializers.HttpSerializer;

import java.net.MalformedURLException;
import java.net.URL;

class HttpAuthUrlConnection extends  AbstractHttp {


    private final Credentials credentials;
    private final Context context;
    private final AuthType authType;

    HttpAuthUrlConnection(
            HttpSerializer serializer,
            Network network,
            Credentials credentials,
            Context context,
            AuthType authType
    ){
        super(serializer,network);
        this.credentials = credentials;
        this.context =context;
        this.authType = authType;
    }

    @Override
    public HttpRequest request(String url, String method) {
        try {

            return new HttpUrlConnectionRequest(new URL(url), method, serializer, network)
            .context(context)
            .authentication(credentials, authType);
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }
    }
}
