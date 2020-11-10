/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.httppck;


import com.pck.httppck.network.Network;
import com.pck.httppck.serializers.HttpSerializer;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("WeakerAccess")

class HttpUrlConnection extends  AbstractHttp{

    HttpUrlConnection(HttpSerializer serializer, Network network){
        super(serializer,network);
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
