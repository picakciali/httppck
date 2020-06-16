/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 15:22
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 15:22
 *
 */

package com.pck.httppck;

public interface Authentication {

    /**
     * @return token
     */
    String getToken();

    /*
     * @return
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
     * doğrulama bilgilerini
     * nesneye parametre olarak geçer
     */
    void  setCredentials(Credentials credentials);

}
