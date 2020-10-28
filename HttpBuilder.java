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
 Http Pck Rest Client Factory
 */

public class HttpBuilder {

    private Credentials credentials;
    private final AuthenticationType type;
    /*
     * Bu constructor ile oluşuturulan
     * Factory nesnesi herhangi bir kimlik
     * doğrulama olmayan doğrudan istek durumlarında kullanılabilir
     */
    public HttpBuilder(){
        this(AuthenticationType.None);
    }

    /**
     * Kimlik doğrulama gerektiren  http
     * isteklerinizi bu constructor ile oluşturdugunuz
     * HttpFactory nesnesinden talep edebilirsiniz
     * @param type Authentication Type
     */
    public HttpBuilder(AuthenticationType type) {
        this.type = type;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public Http build(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = new NetworkImpl(connectivityManager);
        HttpSerializer serializer = new JsonHttpSerializer();

        if (type == AuthenticationType.None) {//standart
            HttpUrlConnection con = new HttpUrlConnection(serializer, network);
            return  con;
        }

        HttpAuthUrlConnection authUrlConnection = new HttpAuthUrlConnection
                (
                serializer,
                network,
                credentials,
                context,
                type
                 );
        return authUrlConnection;

    }

    public HttpBuilder credentials(Credentials credentials) {
        if (credentials == null) throw  new PckException("credentials === null");
        this.credentials = credentials;
        return  this;
    }

}