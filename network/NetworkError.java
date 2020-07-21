/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 19:45
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.05.2020 17:49
 *
 */

package com.pck.httppck.network;

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
