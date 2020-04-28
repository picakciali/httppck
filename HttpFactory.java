/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.http;

import android.content.Context;
import android.net.ConnectivityManager;

import com.pck.http.serializers.JsonHttpSerializer;

public class HttpFactory {

    public  static  Http create(Context context){

        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return  new HttpUrlConnection(new JsonHttpSerializer(),new NetworkImpl(connectivityManager));
    }
}
