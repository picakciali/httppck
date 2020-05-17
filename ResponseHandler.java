/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.httppck;
@SuppressWarnings("WeakerAccess")
public class ResponseHandler<T>{

    public final static int ERROR = -1;
    public final static int CONNECT_SUCCESS = 0;
    public final static int GET_INPUT_STREAM_SUCCESS = 1;
    public final static int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
    public final static int PROCESS_INPUT_STREAM_SUCCESS = 3;

    /**
     * Arka plan işlemi başlamadan önce
     * main threade bir işe çalıştırmak icin
     */
    public  void  post(){};

    /**
     * Başarılı işlemler icin bilgi verir
     * @param data Geri döndürülen nesne datası
     * @param response Http yanıt nesnesi
     */
    public void success(T data, HttpResponse response){}


    /**
     * Hatayla ilgili handler etme (com.pck.evyapimi.http response code >= 400)
     * @param message Hata mesajı
     * @param response Döndürülen Http nesnesi
     */
    public void error(String message, HttpResponse response){}


    /**
     * Ağ hatasını bildirir çevrim dışı vs durumları
     * @param error
     */
    public void failure(NetworkError error){}

    /**
     * Bütün durumlarda işlemin
     * bittiğin bildirir
     */
    public void complete(){}
}
