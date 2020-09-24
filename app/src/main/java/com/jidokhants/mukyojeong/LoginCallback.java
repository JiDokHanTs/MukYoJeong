package com.jidokhants.mukyojeong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class LoginCallback implements FacebookCallback<LoginResult> {
    private static final String TAG = "FACEBOOK_LOGIN_CALLBACK";

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e(TAG, "onSuccess");
        requestMe(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Log.e(TAG, "onCancel");
    }

    @Override
    public void onError(FacebookException error) {
        Log.e(TAG, "onError : " + error.getMessage());
    }

    public void requestMe(AccessToken token) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.e(TAG, "result : " + object.toString());
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
}
