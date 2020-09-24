package com.jidokhants.mukyojeong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.response.model.UserAccount;


public class AfterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRevoke, btnLogOut;
    TextView tvMemberName;
    GoogleSignInAccount acct;
    GoogleSignInClient mGoogleSignInClient;

    CallbackManager callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        btnLogOut = findViewById(R.id.btn_logout);
        btnRevoke = findViewById(R.id.btn_revoke);

        btnLogOut.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);
        tvMemberName = findViewById(R.id.member_name);

        acct = GoogleSignIn.getLastSignedInAccount(this);
        Session session = Session.getCurrentSession();

        if(acct != null){
            String personName =acct.getDisplayName();
            tvMemberName.setText(personName + " 구글 로그인 중");
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        }else if(Profile.getCurrentProfile()!=null){
//            String personName = Profile.getCurrentProfile().getName();
//            tvMemberName.setText(personName+ " 페북 로그인 중");
//            Log.e("PROFILE",Profile.getCurrentProfile().getName());
        }else if(session.isOpened()){
            Intent intent = getIntent();
            String personName = intent.getStringExtra("name");
            tvMemberName.setText(personName+ " 카카오 로그인 중");
        }

    }

    private void signOut(){
        if(mGoogleSignInClient!=null){
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });

        }else if(Profile.getCurrentProfile()!=null){
            LoginManager.getInstance().logOut();
        }else if(UserManagement.getInstance()!= null){
            UserManagement.getInstance()
                    .requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Toast.makeText(AfterActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void revokeAccess(){
        if(mGoogleSignInClient!=null) {
            mGoogleSignInClient.revokeAccess()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });
        }else if(Profile.getCurrentProfile()!=null){
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                signOut();
                Intent intent = new Intent(AfterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            case R.id.btn_revoke:
                revokeAccess();
                finishAffinity();
        }
    }
}
