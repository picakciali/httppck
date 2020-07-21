/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 09.04.2020 01:29
 *
 */

package com.pck.httppck;


import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.pck.httppck.authentication.AuthenticationType;
import com.pck.httppck.authentication.Authentication;
import com.pck.httppck.authentication.Credentials;
import com.pck.httppck.authentication.TokenBasedAuthentication;
import com.pck.httppck.network.Network;
import com.pck.httppck.network.NetworkError;
import com.pck.httppck.serializers.HttpSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class HttpUrlConnectionRequest implements HttpRequest {

    private static final String TAG = "|httpPck|";
    private static final int DEFAULT_TIMEOUT = 60000;
    private static final int DEFAULT_SLEEP = 500;

    private Proxy proxy = Proxy.NO_PROXY;
    private int timeout = DEFAULT_TIMEOUT;
    private int sleep = DEFAULT_SLEEP;
    private String contentType;
    private ResponseHandler handler = new ResponseHandler();
    private Map<String, String> headers = new HashMap<>();
    private Class<?> type;
    private Object data;
    private URL url;
    private String method;
    private HttpSerializer serializer;
    private Network network;
    private boolean auth = false;
    private Authentication authentication;
    private Context context;
    private boolean log;

    HttpUrlConnectionRequest(URL url, String method, HttpSerializer serializer, Network network) {
        this.url = url;
        this.method = method;
        this.serializer = serializer;
        this.network = network;
    }

    @Override
    public HttpRequest data(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public HttpRequest header(String key, String value) {
        headers.put(key, value);
        return this;
    }


    @Override
    public HttpRequest contentType(String value) {
        this.contentType = value;
        return this;
    }


    @Override
    public HttpRequest timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public HttpRequest sleep(int sleep) {
        this.sleep = sleep;
        return this;
    }

    @Override
    public HttpRequest proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public Authentication getAuthentication() {
        return  authentication;
    }

    @Override
    public HttpRequest logStatus(boolean status) {
        this.log = status;
        return this;
    }



    @Override
    public HttpRequest handler(ResponseHandler<?> handler) {
        this.handler = handler;
        this.type    = findType(handler);
        return this;
    }


    @Override
    public HttpRequest authenticationEnabled(Credentials credentials) {
        this.auth = true;
        if (credentials.type == AuthenticationType.TokenBasedAuthentication) {
            authentication = new TokenBasedAuthentication(context);
            authentication.setRequest(this);
            authentication.setCredentials(credentials);
        }

        return  this;
    }

    @Override
    public void send() {
        if (network.isOffline()) {
            handler.failure(NetworkError.Offline);
            handler.complete();
            infoLog("Cancellable.EMPTY");
            return;
        }
        new RequestTask(this).execute();
    }

    @Override
    public HttpRequest setContext(Context context) {
        this.context = context;
        return  this;
    }


    private static class RequestTask extends AsyncTask<Void, Integer, Action> {
        private HttpUrlConnectionRequest request;
        private HttpURLConnection connection;
        RequestTask(HttpUrlConnectionRequest request) {
            this.request = request;
        }

        @Override
        protected void onPreExecute() {
            request.handler.post();
        }


        @Override
        protected Action doInBackground(Void... params) {
            try {
                Thread.sleep(request.sleep);
            } catch (InterruptedException ignored) {

            }


            StrictMode.ThreadPolicy policy = new StrictMode
                    .ThreadPolicy
                    .Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);

            try {
                HttpDataResponse response = getResponse();
                return new Action() {
                    @Override
                    public void call() {
                        if (response.getCode() < 400) {
                            //noinspection unchecked
                            request.handler.success(response.getData(), response);
                        }else{
                            String error = (String) response.getData();
                            if (request.auth){
                                if (error != null){
                                    if ( error.contains("yetkilendirme")
                                            || error.contains("authentication")
                                            || error.contains("Authentication")){
                                        //token yenileme
                                        if (connection != null){
                                            connection.disconnect();
                                            connection = null;
                                        }
                                        request.authentication.clearToken();
                                        request.errLog("old token deleted");
                                        HttpDataResponse newResponse = getResponse();
                                        if (newResponse.getCode() < 400) {
                                            //noinspection unchecked
                                            request.handler.success(newResponse.getData(), newResponse);
                                        }else {
                                            request.handler.error((String) newResponse.getData(),
                                                    newResponse);
                                        }
                                        return;
                                    }
                                }
                            }
                            request.handler.error((String) response.getData(), response);
                        }
                    }
                };
            } catch (final Exception e) {
                return new Action() {
                    @Override
                    public void call() {
                        request.handler.error(TAG + ":" + e.getMessage(), null);
                    }
                };
            } finally {
                if (connection != null)
                    connection.disconnect();
            }

        }
        @Override
        protected void onPostExecute(Action action) {
            action.call();
            request.handler.complete();
            request.infoLog("httpPck is completed");
        }



        private  HttpDataResponse  getResponse()  {
            try {
                if (request.auth) {
                    Authentication authentication = request.authentication;
                    String token = authentication.getToken();
                    if (token == null) {
                        authentication.newToken();
                        request.infoLog("token refresh :"+authentication.getToken());
                    }
                    authentication.addHeaders();

                }
                connection = (HttpURLConnection) request.url.openConnection(request.proxy);
                request.init(connection);
                request.sendData(connection);
                return request.readData(connection, this);
            }catch (Exception e){
                if (e instanceof  PckException){
                    PckException exception = (PckException) e;
                    return  new HttpDataResponse(e.getMessage(),exception.getResponse().getCode(),exception.getResponse().getHeaders());
                }
                return new HttpDataResponse(e.getMessage(),500,null);
            }
        }
    }


    private HttpDataResponse readData(HttpURLConnection connection, RequestTask task) throws Exception {
        int responseCode = getResponseCode(connection);

        if (responseCode >= 500) {
            String response = getString(connection.getErrorStream(), task);
            erResLog(response,responseCode);
            return new HttpDataResponse(response, responseCode, connection.getHeaderFields());
        }

        if (responseCode >= 400) {
            String errResponse = getString(connection.getErrorStream(), task);
            erResLog(errResponse,responseCode);
            return new HttpDataResponse(errResponse, responseCode, connection.getHeaderFields());
        }

        InputStream input = new BufferedInputStream(connection.getInputStream());
        validate(connection);


        if (type.equals(Void.class))
            return new HttpDataResponse(null, responseCode, connection.getHeaderFields());

        if (type.equals(InputStream.class)) {
            ByteArrayOutputStream memory = new ByteArrayOutputStream();
            copyStream(input, memory);
            return new HttpDataResponse(new ByteArrayInputStream(memory.toByteArray()), responseCode, connection.getHeaderFields());
        }

        if (type.equals(String.class)) {
            String r = getString(input, task);
            okStringResLog(r,responseCode);
            return new HttpDataResponse(r, responseCode, connection.getHeaderFields());
        }
        String value = getString(input, task);
        if (type == Object.class) {
            okStringResLog(value,responseCode);
            return new HttpDataResponse(value, responseCode, connection.getHeaderFields());
        }
        resLog(value,responseCode);
        return new HttpDataResponse(serializer.deserialize(value, type), responseCode, connection.getHeaderFields());
    }

    private String getString(InputStream input, RequestTask task) throws IOException {
        String result = null;

        int maxLength = 64 * 1024;
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

    private int getResponseCode(HttpURLConnection connection) throws IOException {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            //noinspection ConstantConditions
            if (e.getMessage().equals("Received authentication challenge is null"))
                return 401;
            throw e;
        }
    }

    private void validate(HttpURLConnection connection) {
        if (!url.getHost().equals(connection.getURL().getHost())) {
            throw new PckException("\"NetworkAuthenticationException\"");
        }
    }

    private void init(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(method);
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        for (Map.Entry<String, String> enty : headers.entrySet()) {
            connection.setRequestProperty(enty.getKey(), enty.getValue());
        }
        setContentType(data, connection);
    }


    private void sendData(HttpURLConnection connection) throws IOException {
        if (data == null)
            return;
        connection.setDoOutput(true);
        OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
        try {
            if (data instanceof InputStream) {
                copyStream((InputStream) data, outputStream);
            } else if (data instanceof String) {
                //noinspection CharsetObjectCanBeUsed
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                infoLog("SENT: " + data);
                writer.write((String) data);
                writer.flush();
            } else {
                //noinspection CharsetObjectCanBeUsed
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                String output = serializer.serialize(data);
                sendDataLog(output,data);
                writer.write(output);
                writer.flush();
            }
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
    private void sendDataLog(String send,Object data){
        try {
            String builder = "  " +
                    "\nHttpPck               :Request\n" +
                    "Method Name           :send_data" +
                    "\n" +
                    "send object type      :" + data.getClass().getName() +
                    "\n" +
                    "toString              :" + data.toString() +
                    "\n" +
                    "seriliaze             :" + send ;
            infoLog(builder);
        }catch (Exception ignored){}
    }


    private void okStringResLog(String response,int code){
        String builder = "  " +
                "\nHttpPck                    :Response\n" +
                "Method Name                :read_data" +
                "\n" +
                "respose code               :" + code+
                "\n" +
                "success object type        :" + type.getName() +
                "\n" +
                "value                      :" + response ;
        infoLog(builder);
    }

    private void erResLog(String error,int code){
        String builder = "  " +
                "\nHttpPck                 :Error  Response\n" +
                "Method Name             :read_data" +
                "\n" +
                "respose code            :" + code+
                "\n" +
                "success object type     :" + type.getName() +
                "\n" +
                "Error                   :" + error ;
        errLog(builder);
    }

    private void resLog(String value,int responseCode){
       try {
           String builder = "  " +
                   "\nHttpPck                      :Response\n" +
                   "Method Name                  :read_data" +
                   "\n" +
                   "respose code                 :" + responseCode +
                   "\n" +
                   "success object type          :" + type.getName() +
                   "\n" +
                   "response                     :" + value +
                   "\n" +
                   "deserialize                  :" + serializer.deserialize(value, type);
           infoLog(builder);

       }catch (Exception ignored){}
    }
    private void  infoLog(String message){
        if (this.log){
            Log.i(TAG,message);
        }
    }
    private void  errLog(String message){
        if (this.log){
            Log.e(TAG,message);
        }
    }

    private void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[64 * 1024];
        int bytes;
        while ((bytes = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytes);
        }
    }

    private void setContentType(Object data, HttpURLConnection connection) {
        if (headers.containsKey("Content-Type"))
            return;
        if (contentType != null) {
            connection.setRequestProperty("Content-Type", contentType);
            return;
        }
        if (data instanceof InputStream)
            connection.setRequestProperty("Content-Type", "application/octet-stream");
        else
            connection.setRequestProperty("Content-Type", serializer.getContentType());

    }


    private Class<?> findType(ResponseHandler<?> handler) {
        Method[] methods = handler.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("success")) {
                Class<?> param = method.getParameterTypes()[0];
                if (!param.equals(Object.class))
                    return param;
            }
        }

        return Object.class;
    }
}