package com.example.hearfiss_01.views.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;

public class JoinResultActivity extends AppCompatActivity implements View.OnClickListener{
    String m_TAG = "JoinResultActivity";
    Context m_Context;
    TextView m_TextPhoneId;
    TextView m_TextPassword;
    AppCompatButton m_AppBtnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_result);

        m_Context = JoinResultActivity.this;

        Log.v(m_TAG, String.format("onCreate"));

        m_TextPhoneId = findViewById(R.id.TextJoinResultPhoneId);
        m_TextPassword = findViewById(R.id.TextJoinResultPassword);

        m_AppBtnGoLogin = findViewById(R.id.AppBtnJoinResultGoLogin);
        m_AppBtnGoLogin.setOnClickListener(this);

        m_TextPhoneId.setText( GlobalVar.g_AccJoin.getAcc_phone_id() );
        m_TextPassword.setText( GlobalVar.g_AccJoin.getAcc_pwd() );
    }

    @Override
    public void onClick(View view) {
        Log.v(m_TAG, String.format("onClick id : %d ", view.getId()));
        if (view.getId() == R.id.AppBtnJoinResultGoLogin) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}