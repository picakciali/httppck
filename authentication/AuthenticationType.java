/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 21:11
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 21.07.2020 21:11
 *
 */

package com.pck.httppck.authentication;

/**
 * Kütüphane tarafından desteklenenker şu an için
 *    TokenBasedAuthentication )
 */
public enum AuthenticationType {
        /**
         */
        None,
        /**
         * Basic Authentication
         * https://en.wikipedia.org/wiki/Basic_access_authentication
         */
        BasicAuthentication,
        /**
         * Token Based Authentication
         * https://stackoverflow.com/questions/1592534/what-is-token-based-authentication
         */
        TokenBasedAuthentication
}
