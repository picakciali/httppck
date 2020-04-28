/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.http;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
@SuppressWarnings("WeakerAccess")
public class NetworkImpl implements Network {
    private ConnectivityManager manager;

    public NetworkImpl(ConnectivityManager manager) {
        this.manager = manager;
    }
    @Override
    public boolean isOffline() {
        return !isOnline();
    }

    @Override
    public boolean isOnline() {
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnectedOrConnecting());
    }
}
