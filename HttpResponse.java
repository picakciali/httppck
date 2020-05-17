/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.httppck;

import java.util.List;
import java.util.Map;
@SuppressWarnings("WeakerAccess")
public class HttpResponse {
    private int code;
    private Map<String, List<String>> headers;

    public HttpResponse(int code, Map<String, List<String>> headers) {
        this.code = code;
        this.headers = headers;
    }


    public int getCode() {
        return code;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
