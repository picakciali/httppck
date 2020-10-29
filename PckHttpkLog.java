/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *
 */
package com.pck.httppck;

import com.pck.httppck.serializers.HttpSerializer;

public  class PckHttpkLog {
    public static final String TAG = "|httpPck|";
    public static boolean LOG = false;

    private PckHttpkLog(){}


    public static void  infoLog(String message){
        if (LOG){
            android.util.Log.i(TAG,message);
        }
    }
    public static void  errLog(String message){
        if (LOG){
            android.util.Log.e(TAG,message);
        }
    }

    public static void sendDataLog(String send,Object data){
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


    public static void okStringResLog(String response,int code,Class<?> type){
        try {
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
        }catch (Exception ignored){}
    }

    public static void erResLog(String error,int code,Class<?> type){
        try {
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
        }catch (Exception ignored){}
    }

    public static void resLog(String value, int responseCode, Class<?> type, HttpSerializer serializer){
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

}
