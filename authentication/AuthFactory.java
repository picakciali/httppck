/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *
 */
package com.pck.httppck.authentication;

import android.content.Context;

import com.pck.httppck.PckException;

public  class AuthFactory {

    private AuthFactory(){}

    public static Authentication create(AuthType type, Context context){
        if (type == AuthType.TokenBasedAuthentication){
            return new  TokenBasedAuthentication(context);
        }
        throw  new PckException("unsupported authentication");
    }
}
