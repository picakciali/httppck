/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 16:00
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 16:00
 *
 */

package com.pck.httppck;

import android.content.Context;

@SuppressWarnings("WeakerAccess")
public class Credentials {
    public String grant_type;
    public AuthType type;
    public String username;
    public String password;
    public String url;

    public Credentials() {
        //grant_type default olarak password değerindedir
        this.grant_type = "password";
    }
}
