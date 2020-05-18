/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 13:43
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 13:43
 *
 */

package com.pck.httppck;

@SuppressWarnings("WeakerAccess")
public class PckException extends  RuntimeException {

    private  HttpResponse response;

    public PckException(String message) {
        super(message);
    }

    public PckException(String message, HttpResponse response) {
        super(message);
        this.response = response;
    }


    public  HttpResponse getResponse(){
        return  response;
    }
}
