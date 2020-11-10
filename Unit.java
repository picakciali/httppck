/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *
 */
package com.pck.httppck;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class Unit {
    private Unit(){

    }

    public static int getResponseCode(HttpURLConnection connection) throws IOException {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            //noinspection ConstantConditions
            if (e.getMessage().equals("Received authentication challenge is null"))
                return 401;
            throw e;
        }
    }

    public static String getString(InputStream input) throws IOException {

        int maxLength = 64 * 1024;
        InputStreamReader reader = new InputStreamReader(input, "UTF-8");
        char[] buffer = new char[maxLength];
        StringBuilder builder = new StringBuilder();
        int len;
        while ((len = reader.read(buffer)) > 0) {
            builder.append(buffer, 0, len);
        }

        reader.close();
        return builder.toString();
    }

}
