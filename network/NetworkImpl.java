/*
 * *
 *  * Created by Ali PIÇAKCI on 21.07.2020 19:45
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.05.2020 17:49
 *
 */

package com.pck.httppck.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkImpl implements Network {
    private final ConnectivityManager manager;

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
