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

import com.pck.httppck.authentication.AuthType;
import com.pck.httppck.authentication.Credentials;
import com.pck.httppck.network.Network;
import com.pck.httppck.network.NetworkImpl;
import com.pck.httppck.serializers.HttpSerializer;
import com.pck.httppck.serializers.JsonHttpSerializer;


/*
 Http Pck Rest Client Factory
 */

public class HttpBuilder {

    private Credentials credentials;
    private AuthType authType;


    public HttpBuilder(){
        AuthType authType = AuthType.None;
    }


    /**
     * istek bir doğrulama gerçekleştirilecekse
     * bu method ile doğrulama tipi  ile (örn: basic authentication) bildirilir
     */
    public  HttpBuilder authenticationType(AuthType authType){
        this.authType = authType;
        return  this;
    }

    /**
     * istek bir doğrulama gerçekleştirilecekse
     * bu method ile kimlik bilgileri doldurulur
     */
    public HttpBuilder credentials(Credentials credentials) {
        if (authType == AuthType.None) throw  new PckException("authenticationType");
        if (credentials == null) throw new PckException("credentials is nulll");
        this.credentials = credentials;
        return  this;
    }

    /**
     * static #com.pck.httppck.HttpLog.LOG
     */
    public  HttpBuilder enableLog(){
        PckHttpkLog.LOG = true;
        return  this;
    }

    /**
     * static #com.pck.httppck.HttpLog.LOG
     */
    public  HttpBuilder disableLog(){
        PckHttpkLog.LOG = false;
        return  this;
    }


    /**
     * Http nesnesini oluşturur
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public Http build(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       
        Network network = new NetworkImpl(connectivityManager);
        
        HttpSerializer serializer = new JsonHttpSerializer();

        if (authType == AuthType.None) {//standart
            HttpUrlConnection con = new HttpUrlConnection(serializer, network);
            return  con;
        }

        HttpAuthUrlConnection authUrlConnection = new HttpAuthUrlConnection
                (
                serializer,
                network,
                credentials,
                context,
                 authType
                 );
        return authUrlConnection;

    }

}
