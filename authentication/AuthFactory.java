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
