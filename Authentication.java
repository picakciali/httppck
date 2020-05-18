/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 15:22
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 15:22
 *
 */

package com.pck.httppck;

public interface Authentication {

    String getToken();
    String getExpiresIn();
    void addHeaders();
    void setRequest(HttpRequest request);
    void newToken() throws Exception;
    void refreshToken();
    void clearToken();
    void  setCredentials(Credentials credentials);

}
