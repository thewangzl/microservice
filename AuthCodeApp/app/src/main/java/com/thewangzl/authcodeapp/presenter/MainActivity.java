package com.thewangzl.authcodeapp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.thewangzl.authcodeapp.R;
import com.thewangzl.authcodeapp.client.oauth2.AccessToken;
import com.thewangzl.authcodeapp.client.oauth2.AuthorizationRequest;
import com.thewangzl.authcodeapp.client.oauth2.OAuth2StateManager;
import com.thewangzl.authcodeapp.client.oauth2.TokenStore;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button userInfoButton;

    private TokenStore tokenStore;

    private OAuth2StateManager oAuth2StateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenStore = new TokenStore(this);
        oAuth2StateManager = new OAuth2StateManager(this);

        userInfoButton = findViewById(R.id.userinfo_button);
        userInfoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        AccessToken accessToken = tokenStore.getToken();

        if(accessToken != null && !accessToken.isExipired()){
            //
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
            return;
        }

        // create a state parameter to start the authorization flow
        String state = UUID.randomUUID().toString();
        oAuth2StateManager.saveState(state);

        // create the thorization URI to redirect user
        Uri uri = AuthorizationRequest.createAuthorizationUri(state);

        Intent authorizationIntent = new Intent(Intent.ACTION_VIEW);
        authorizationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        authorizationIntent.setData(uri);
        startActivity(authorizationIntent);
    }
}
