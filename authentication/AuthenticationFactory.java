package com.pck.httppck.authentication;

import android.content.Context;

import com.pck.httppck.PckException;

public  class AuthenticationFactory {

    private AuthenticationFactory(){}

    public static Authentication create(AuthenticationType type, Context context){
        if (type == AuthenticationType.TokenBasedAuthentication){
            return new  TokenBasedAuthentication(context);
        }
        throw  new PckException("unsupported authentication");
    }
}
