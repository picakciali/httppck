/*
 * *
 *  * Created by Ali PIÇAKCI on 18.05.2020 23:22
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 18.05.2020 23:16
 *
 */

package com.pck.httppck;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class BasedAuthentication  implements  Authentication {

    private  String grant_type;
    private  String username;
    private  String password;
    private  String token;
    private  SharedPreferences sharedPreferences;
    private  final String propName;
    private  HttpRequest request;
    private  String expires_in;
    private  String tokenApiUrl;

    BasedAuthentication(Context context) {
        this.propName = "access_token";
        String cahceName = "authentication";
        this.sharedPreferences = context.getSharedPreferences(cahceName,Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString(propName,null);
        //grant_type default olarak password değerindedir
        this.grant_type = "password";

    }

    @Override
    public String getExpiresIn() {
        return  expires_in;
    }



    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;

    }

    @Override
    public void addHeaders() {
        request.header("Content-Type","application/json");
        request.header("Authorization","Bearer "+token);
    }


    @Override
    public void newToken() throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode
                .ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection =null;
        try {
            URL url = new URL(tokenApiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Content-Type","application/json");

            connection.setDoOutput(true);
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");

            writer.write("grant_type="+this.grant_type+"&username="+this.username+"&password="+this.password+"");
            writer.flush();

            TokenResponse response = readData(connection);
            if (response != null){
                token = response.access_token;
                expires_in = response.expires_in;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(propName,token);
                editor.apply();
            }

        }catch (Exception e){
            throw  new PckException(
                    e.getMessage(),
                    new HttpResponse(
                            connection != null ? connection.getResponseCode() : 500,
                            connection != null ? connection.getHeaderFields() : null)
                        );
        }finally {
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
    public String getToken(){
        token = sharedPreferences.getString(propName,null);
        return  token;
    }


    @Override
    public   void  clearToken(){
        sharedPreferences.edit().clear().apply();
    }

    @Override
    public void setApiUrl(String url) {
        this.tokenApiUrl = url;
    }


    private TokenResponse readData(HttpURLConnection connection) throws Exception {
        int responseCode = getResponseCode(connection);

        if (responseCode >= 400) {
            String err = getString(connection.getErrorStream());
            throw  new PckException(err);
        }

        InputStream input = new BufferedInputStream(connection.getInputStream());
        String value = getString(input);
        return  new Gson().fromJson(value,TokenResponse.class);
    }

    private  String getString(InputStream input) throws IOException {
        String result = null;

        int maxLength = 64 * 1024;
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
    private int getResponseCode(HttpURLConnection connection) throws IOException {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            if (e.getMessage().equals("Received authentication challenge is null"))
                return 401;
            throw e;
        }
    }

    static  class  TokenResponse{
        String  access_token;
        String token_type;
        String expires_in;
    }
}










