package com.moviesdb.moviesdbmvvm.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class EnumConverter extends Converter.Factory{

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Converter<?, String> converter = null;
        if (type instanceof Class && ((Class<?>)type).isEnum()) {
            converter = value -> EnumUtils.GetSerializedNameValue((Enum) value);
        }
        return converter;
    }
}
