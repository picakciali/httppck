/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 19:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 20.05.2020 16:43
 *
 */

package com.pck.httppck.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.pck.httppck.HttpRequest;
import com.pck.httppck.HttpResponse;
import com.pck.httppck.PckException;
import com.pck.httppck.Unit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class TokenBasedAuthentication implements Authentication {


    private String token;
    private final SharedPreferences sharedPreferences;
    private final String propName;
    private HttpRequest request;
    private String expires_in;
    private Credentials credentials;

    TokenBasedAuthentication(Context context) {
        this.propName = "access_token";
        String cahceName = "authentication";
        this.sharedPreferences = context.getSharedPreferences(cahceName, Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString(propName, null);
        this.credentials = new Credentials();
    }

    @Override
    public String getExpiresIn() {
        return expires_in;
    }


    @Override
    public void addHeaders() {
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + token);
    }


    @Override
    public void newToken() throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode
                .ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        try {
            URL url = new URL(credentials.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");

            writer.write("grant_type="
                    +
                    credentials.grant_type
                    + "&username="
                    +  credentials.username
                    + "&password="
                    + credentials.password +
                    "");
            writer.flush();

            TokenResponse response = readData(connection);
            if (response != null) {
                token = response.access_token;
                expires_in = response.expires_in;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(propName, token);
                editor.apply();
            }

        } catch (Exception e) {
            throw new PckException(
                    e.getMessage(),
                    new HttpResponse(
                            connection != null ? connection.getResponseCode() : 500,
                            connection != null ? connection.getHeaderFields() : null)
            );
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    @Override
    public void refreshToken() {

    }

    @Override
    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String getToken() {
        token = sharedPreferences.getString(propName, null);
        return token;
    }


    @Override
    public void clearToken() {
        token = null;
        sharedPreferences.edit().clear().apply();
    }

    @Override
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }


    private TokenResponse readData(HttpURLConnection connection) throws Exception {
        int responseCode = Unit.getResponseCode(connection);

        if (responseCode >= 400) {
            String err = Unit.getString(connection.getErrorStream());
            throw new PckException(err);
        }

        InputStream input = new BufferedInputStream(connection.getInputStream());
        String value = Unit.getString(input);
        return new Gson().fromJson(value, TokenResponse.class);
    }





    static class TokenResponse {
        String access_token;
        String token_type;
        String expires_in;
    }
}










