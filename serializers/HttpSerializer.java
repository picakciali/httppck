package com.pck.http.serializers;

import java.lang.reflect.Type;

public interface HttpSerializer {
    String getContentType();
    String serialize(Object object);
    Object deserialize(String value, Class type);
    Object deserialize(String value, Type type);
    <T>  T tdeseriliaze(String value, Class<T> type);
}
