package com.thewangzl.authcodeapp.client;

import com.thewangzl.authcodeapp.client.inteceptor.BearerTokenHeaderInterceptor;
import com.thewangzl.authcodeapp.client.inteceptor.ErrorInterceptor;
import com.thewangzl.authcodeapp.client.inteceptor.OAuth2ClientAuthenticationInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitAPIFactory {

    private Retrofit retrofit;

    RetrofitAPIFactory(String baseUrl, OAuth2ClientAuthenticationInterceptor clientAuthentication){
        retrofit = new Retrofit.Builder()//
                            .baseUrl("http://"+baseUrl)//
                            .addConverterFactory(JacksonConverterFactory.create())//
                            .client(createClient(clientAuthentication))//
                    .build();
    }

    private OkHttpClient createClient(OAuth2ClientAuthenticationInterceptor clientAuthentication){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new ErrorInterceptor());
        client.addInterceptor(new BearerTokenHeaderInterceptor());
        if(clientAuthentication != null){
            client.addInterceptor(clientAuthentication);
        }
        return client.build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
