/*
 * *
 *  * Created by Ali PIÇAKCI on 29.04.2020 01:19
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 17.03.2020 20:41
 *
 */

package com.pck.http;

import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class HttpDataResponse extends  HttpResponse {
    private Object data;

    public HttpDataResponse(Object data, int code, Map<String, List<String>> headers) {
        super(code, headers);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
