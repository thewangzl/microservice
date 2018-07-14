package com.thewangzl.authcodeapp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.thewangzl.authcodeapp.R;
import com.thewangzl.authcodeapp.client.ClientAPI;
import com.thewangzl.authcodeapp.client.oauth2.AccessToken;
import com.thewangzl.authcodeapp.client.oauth2.AccessTokenRequest;
import com.thewangzl.authcodeapp.client.oauth2.OAuth2StateManager;
import com.thewangzl.authcodeapp.client.oauth2.TokenStore;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationCodeActivity extends AppCompatActivity {

    private String code;

    private String state;

    private TokenStore tokenStore;

    private OAuth2StateManager manager;

    private TextView textAccessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_code);

        textAccessToken = findViewById(R.id.access_token);

        tokenStore = new TokenStore(this);
        manager = new OAuth2StateManager(this);

        Uri callbackUri = Uri.parse(getIntent().getDataString());

        code = callbackUri.getQueryParameter("code");
        state = callbackUri.getQueryParameter("state");

        // validates state
        if(!manager.isValidState(state)){
            Toast.makeText(this,"CSRF Attack detected", Toast.LENGTH_SHORT);
            return;
        }

        Call<AccessToken> accessTokenCall = ClientAPI.oauth2().requestToken(AccessTokenRequest.fromCode(code));
        Log.e("AuthCode","---------------------------------------------");
        Log.e("AuthCode", accessTokenCall.request().url().encodedPath()+"\n"+ accessTokenCall.request().body().toString() + "\n"+accessTokenCall.request().url().encodedQuery()+"\n"+ accessTokenCall.request().headers().names());
        HttpUrl url = accessTokenCall.request().url();
        RequestBody body = accessTokenCall.request().body();
        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.e("AuthorizationCode", "success:"+ response.body());
                AccessToken accessToken = response.body();
                System.out.println(accessToken);
                textAccessToken.setText(accessToken.toString());
                Toast.makeText(AuthorizationCodeActivity.this,accessToken.toString(),Toast.LENGTH_SHORT);
                tokenStore.save(accessToken);

                // go to the other activity with an access token in handles!!!

                Intent intent = new Intent(AuthorizationCodeActivity.this, UserInfoActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.e("AuthorizationCode", "Error retrieving access token"+t.getMessage(), t);

                Toast.makeText(AuthorizationCodeActivity.this,t.getMessage(),Toast.LENGTH_SHORT);

            }
        });

    }
}
