package com.pck.httppck.serializers;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class JsonHttpSerializer implements  HttpSerializer {
    private Gson gson =  new GsonBuilder()
            .setLenient()
            .create();

    @Override
    public String getContentType() {
        return "application/json";    }

    @Override
    public String serialize(Object object)
    {
        return gson.toJson(object);
    }

    @Override
    public Object deserialize(String value, Class type)
    {
        return gson.fromJson(value,type);
    }

    @Override
    public Object deserialize(String value, Type type) {
        return  gson.fromJson(value,type);
    }

    @Override
    public <T> T tdeseriliaze(String value, Class<T> type) {
        return  gson.fromJson(value,type);
    }


    private class ByteArrayToBase64Adapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

        @Override
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        @Override
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }

}
