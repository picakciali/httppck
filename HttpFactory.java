/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.httppck;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.IntDef;

import com.pck.httppck.serializers.HttpSerializer;
import com.pck.httppck.serializers.JsonHttpSerializer;


/*
 Factory
 */
@SuppressWarnings("WeakerAccess")
public class HttpFactory {


    public final static int DEFAULT = 1;
    public final static int AUTH = 2;


    @IntDef(value = {DEFAULT, AUTH})
    private @interface FactoryType {
    }

    private Credentials credentials;
    private @FactoryType
    int type;


    public  HttpFactory(){
        this(DEFAULT);
    }

    public HttpFactory(@FactoryType int type) {
        this.type = type;
    }

    public Http create(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = new NetworkImpl(connectivityManager);
        HttpSerializer serializer = new JsonHttpSerializer();
        if (type == DEFAULT) {
            return new HttpUrlConnection(serializer, network);
        } else if (type == AUTH) {
            if (credentials == null) {
                throw new PckException("credentials == null");
            }
            credentials.type = AuthType.BasedAuthentication;
            credentials.context = context;
            return new HttpAuthUrlConnection(serializer, network, credentials);
        }
        return null;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}