package com.thewangzl.authcodeapp.client.inteceptor;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */
public class BearerTokenHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        List<String> headers = request.headers("Authorization");
        if(headers.size() > 0) {
            String accessToken  = headers.get(0);

            request.newBuilder()    //
                .removeHeader("Authorization")//
                .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
        }
        return chain.proceed(request);
    }
}
