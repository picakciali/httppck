/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 19:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 21.07.2020 19:15
 *
 */

package com.pck.httppck.authentication;

import com.pck.httppck.HttpRequest;

public interface Authentication {
    /**
     * @return token
     */
    String getToken();
    /*
     * @return token süresi
     */
    String getExpiresIn();
    /**
     * tokeni headere
     * ekler
     */
    void addHeaders();
    /**
     * mevcut http isteğini
     * Authentication nesnesine
     * set eder
     */
    void setRequest(HttpRequest request);
    /**
     * yeni token alma sunucudan
     */
    void newToken() throws Exception;
    /**
     * tokeni yenile
     * TokenBasedAuthentication ile doğrulamada
     * token yenilemeside #newToken() methodu ile
     * gerçekleştirilmiştir
     */
    void refreshToken();
    /**
     * tokeni bellekten ve
     * cahceden temizler
     */
    void clearToken();
    /**
     * kimlik bilgilerini
     * nesneye parametre olarak geçer
     */
    void  setCredentials(Credentials credentials);
}
