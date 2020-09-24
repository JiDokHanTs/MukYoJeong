package com.jidokhants.mukyojeong;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SIGN_IN";
    private SignInButton googleSignInButton;

    private CallbackManager callbackManager;
    private LoginButton facebookLoginButton;
    private LoginCallback loginCallback;

    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    private com.kakao.usermgmt.LoginButton kakaoLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn){
            Intent intent = new Intent(this, AfterActivity.class);
            startActivity(intent);
            finish();
        }
        callbackManager = CallbackManager.Factory.create();
        loginCallback = new LoginCallback();

        facebookLoginButton = findViewById(R.id.facebook_login_button);
        facebookLoginButton.setPermissions(Arrays.asList("public_profile", "email"));
        facebookLoginButton.registerCallback(callbackManager, loginCallback);

        kakaoLoginButton = findViewById(R.id.kakao_login_button);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void signIn(){
        Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Intent intent = new Intent(this, AfterActivity.class);
            startActivity(intent);
            finish();
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Intent intent = new Intent(this, AfterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        }catch (ApiException e){
            Log.w(TAG, "signInResult: failed code="+e.getStatusCode());
        }
    }
    private void updateUI(GoogleSignInAccount user){
        if(user!=null){
            Intent intent = new Intent(this, AfterActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_sign_in_button:
                signIn();
                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }
    public class SessionCallback implements ISessionCallback {
        private static final String TAG = "KAKAO_API";

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        public void requestMe() {
            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e(TAG, "session on closed" + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e(TAG, "session Failed" + errorResult);
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            Log.e(TAG, "user id: " + result.getId());

                            UserAccount kakaoAccount = result.getKakaoAccount();

                            if (kakaoAccount != null) {

                                // 이메일
                                String email = kakaoAccount.getEmail();

                                if (email != null) {
                                    Log.i(TAG, "email: " + email);

                                } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 이메일 획득 가능
                                    // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                } else {
                                    // 이메일 획득 불가
                                }

                                // 프로필
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    Intent intent = new Intent(MainActivity.this, AfterActivity.class);
                                    intent.putExtra("name", profile.getNickname());
                                    startActivity(intent);
                                    finish();
                                } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 프로필 정보 획득 가능

                                } else {
                                    // 프로필 획득 불가
                                }
                            }

                        }
                    });
        }
    }
}