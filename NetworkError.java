/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.httppck;

public enum  NetworkError {

    Offline,
    /**
     * Ağ kimlik doğrulaması
     */
    AuthenticationRequired,
    /**
     * Desteklenmeyen HTTP yöntemi isteniyor
     */
    UnsupportedMethod,
    /**
     * İstek zaman aşımına uğradı
     */
    Timeout, /**
     * Bilinmeyen hata
     */
    Unknown,
}
