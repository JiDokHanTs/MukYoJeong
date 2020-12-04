package com.jidokhants.mukyojeong.fragments.setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.AfterActivity;
import com.jidokhants.mukyojeong.activities.MainActivity;
import com.jidokhants.mukyojeong.activities.login.LoginActivity;

public class FragmentSetting extends Fragment implements View.OnClickListener {
    TextView userEmail, userName, logout, revoke;
    LinearLayout serviceMail;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showSearchMenu(false);
        ((MainActivity) getActivity()).showWriteMenu(false);
        ((MainActivity) getActivity()).setActionbarshow();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);

        userEmail = view.findViewById(R.id.setting_user_email);
        userName = view.findViewById(R.id.setting_user_name);
        logout = view.findViewById(R.id.setting_logout);
        revoke = view.findViewById(R.id.setting_revoke);
        serviceMail = view.findViewById(R.id.setting_service);

        userEmail.setText(currentUser.getEmail());
        userName.setText(currentUser.getDisplayName());

        logout.setOnClickListener(this);
        revoke.setOnClickListener(this);
        serviceMail.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_logout:
                signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.setting_revoke:
                revokeAccess();
                break;
            case R.id.setting_service:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/Text");
                email.putExtra(Intent.EXTRA_EMAIL, "77sy777@gmail.com");
      
                email.putExtra(Intent.EXTRA_SUBJECT, "<먹요정 문의사항>");
                email.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + 1.0 + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");
                email.setType("message/rfc822");
                startActivity(email);

        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void revokeAccess() {
        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getActivity().finishAffinity();
            }
        });
    }
}
