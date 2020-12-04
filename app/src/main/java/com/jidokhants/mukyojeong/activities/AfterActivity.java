package com.jidokhants.mukyojeong.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jidokhants.mukyojeong.activities.login.LoginActivity;
import com.jidokhants.mukyojeong.R;


public class AfterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRevoke, btnLogOut;
    TextView tvMemberName;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        btnLogOut = findViewById(R.id.btn_logout);
        btnRevoke = findViewById(R.id.btn_revoke);

        btnLogOut.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);
        tvMemberName = findViewById(R.id.member_name);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            String personName =currentUser.getDisplayName();
            tvMemberName.setText(personName + " 구글 로그인 중");
        }
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
    private void revokeAccess(){
        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finishAffinity();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                signOut();
                Intent intent = new Intent(AfterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_revoke:
                revokeAccess();
                break;
        }
    }
}
