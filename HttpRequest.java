/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 19.03.2020 02:53
 *
 */

package com.pck.httppck;

import android.content.Context;

import com.pck.httppck.authentication.Authentication;
import com.pck.httppck.authentication.Credentials;

import java.net.Proxy;
@SuppressWarnings("UnusedReturnValue")
public interface HttpRequest {
    /**
     * Sunucuya gönderilecek olan nesne
     * @param data data entity
     * @return Zincirleme nesne dönümü
     */
    HttpRequest data(Object data);
    /**
     * Request Heedar Bilgileri
     * @param key Gönderilecek nesnein keyi
     * @param value Gönderilcek nesnein valuesi
     * @return ""
     */
    HttpRequest header(String key, String value);
    /**
     * Requestin contentType türünü belirtir
     * genelde Json olarak gönderlir
     * @param value Type:
     * @return ""
     */
    HttpRequest contentType(String value);
    /**
     * Talep bittikden sonra Ui Threada
     * yürütüşecek olan işlemlerin handlerini sağlanır
     * @param handler handler
     * @return ""
     */
    HttpRequest handler(ResponseHandler<?> handler);
    /**
     * isteği Authentication ile yapmak için
     */
    HttpRequest authentication(Credentials authRes);
    /**
     * İstek zaman aşım değeri
     * varsayılan olarak 60 Saniyedir
     * @param timeout 60 Saniye
     * @return ""
     */
    HttpRequest timeout(int timeout);
    /**
     * arka plan işcigini ms cinsinden
     * bekletir
     * @param sleep ms cinsinden
     * @return ""
     */
    HttpRequest sleep(int sleep);
    /**
     * Requeste bir proxy gönderilecek gönderilir
     * varsayilen olarak boştuyr
     * @param proxy proxy
     * @return ""
     */
    HttpRequest proxy(Proxy proxy);
    /**
     * Log durumu
     * istek ve cevaplarla ilgili
     * detayların LogChat ile basılıp
     * basılmama tercihini be method ile belirlenmektedir
     */
    HttpRequest logStatus(boolean status);
    /**
     * Application Context ihtiyac duyulduğu
     * zaman bu methotdan
     * client tarafıbdab set edilebilir
     * @param context ""
     */
    HttpRequest  setContext(Context context);
    /**
     * Authentication kullanıyorsa
     * bu nesneyi clinte cagirdigi
     * zaman geri verir
     * @return ""
     */
    Authentication getAuthentication();
    /**
     * Arka Plan istekleri yürütmek
     * icin kullanılcak
     */
    void send();
}
