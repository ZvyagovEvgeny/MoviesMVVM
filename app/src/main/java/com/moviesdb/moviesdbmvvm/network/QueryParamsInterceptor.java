package com.moviesdb.moviesdbmvvm.network;



import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryParamsInterceptor implements Interceptor {
    private Map<String,String> values;

    public QueryParamsInterceptor(Map<String, String> values) {
        this.values = values;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl.Builder builder = originalHttpUrl.newBuilder();

        Collection<String> keys = values.values();
        for(String key: keys){
            String val = values.get(key);
            builder.addQueryParameter(key,val);
        }

        HttpUrl url = builder.build();
        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);

    }
}