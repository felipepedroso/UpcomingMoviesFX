/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pedroso.upcomingMovies.rest;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author felip
 */
public class ApiKeyInterceptor implements Interceptor {

    private final String API_KEY_QUERY_PARAMETER = "api_key";

    private String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();

        HttpUrl urlWithApiKey = originalUrl.newBuilder()
                .addQueryParameter(API_KEY_QUERY_PARAMETER, apiKey)
                .build();

        Request modifiedRequest = originalRequest.newBuilder()
                .url(urlWithApiKey)
                .build();

        okhttp3.Response response = chain.proceed(modifiedRequest);

        return response;
    }
}
