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
        String result = null;

        int maxLength = 64 * 1024; //64 kb - bu kütüphane bir rest  api istemcisi olduğunu unutmayın, 64 kb yeterli bir tampon uzunluğu olacaktır
        //noinspection CharsetObjectCanBeUsed
        InputStreamReader reader = new InputStreamReader(input, "UTF-8");

        char[] buffer = new char[maxLength];

        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            int pct = (100 * numChars) / maxLength;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);

        }
        if (numChars != -1) {
            result = new String(buffer, 0, numChars);
        }
        return result;
    }

}
