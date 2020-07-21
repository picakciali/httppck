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

import com.pck.httppck.authentication.AuthenticationType;
import com.pck.httppck.authentication.Credentials;
import com.pck.httppck.network.Network;
import com.pck.httppck.network.NetworkImpl;
import com.pck.httppck.serializers.HttpSerializer;
import com.pck.httppck.serializers.JsonHttpSerializer;


/*
 Factory
 */

public class HttpFactory {


    public final static int DEFAULT = 1;
    public final static int TOKEN_BASED_AUTH = 2;




    private Credentials credentials;
    private AuthenticationType type;
    private boolean log;


    /*
     * Bu constructor ile oluşuturulan
     * Factory nesnesi normal Http
     * nesnesi oluştururlar
     */
    public  HttpFactory(){
        this(AuthenticationType.None);
    }

    /**
     * Authentication gerektiren zamanlarda
     *
     * @param type Authentication Type
     */
    public HttpFactory(AuthenticationType type) {
        this.type = type;
    }

    public Http create(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = new NetworkImpl(connectivityManager);
        HttpSerializer serializer = new JsonHttpSerializer();
        if (type == AuthenticationType.None) {
            HttpUrlConnection con = new HttpUrlConnection(serializer, network);
            con.log = log;
            return  con;
        } else if (type == AuthenticationType.TokenBasedAuthentication) {
            if (credentials == null) {
                throw new PckException("credentials == null");
            }
            credentials.type = AuthenticationType.TokenBasedAuthentication;
            HttpAuthUrlConnection authUrlConnection = new HttpAuthUrlConnection(serializer, network, credentials,context);
            authUrlConnection.log = log;
            return authUrlConnection;
        }
        return null;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }


    public void enableLog(){
        this.log = true;
    }

}