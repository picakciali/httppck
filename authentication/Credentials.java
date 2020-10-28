/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 19:20
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 19.05.2020 01:21
 *
 */

package com.pck.httppck.authentication;


public class Credentials {
    public String grant_type;
    public String username;
    public String password;
    public String url;

    public Credentials() {
        //grant_type default olarak password değerindedir
        this.grant_type = "password";
    }
}
