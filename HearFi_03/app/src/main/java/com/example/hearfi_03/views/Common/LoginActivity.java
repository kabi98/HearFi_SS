package com.example.hearfi_03.views.Common;

import static android.os.Build.*;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfi_03.R;
import com.example.hearfi_03.db.dao.AccountDAO;
import com.example.hearfi_03.db.DTO.Account;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    String m_TAG = "LoginActivity";
    Context m_Context;

    AppCompatButton m_AppBtnLogin, m_AppBtnJoin;
    EditText m_EditLoginId;
    EditText m_EditLoginPassword;

    long m_BackKeyPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_Context = LoginActivity.this;

        Log.v(m_TAG, "onCreate");

        m_AppBtnLogin = findViewById(R.id.btnLogin);
        m_AppBtnLogin.setOnClickListener(this);

        m_AppBtnJoin = findViewById(R.id.btnJoin);
        m_AppBtnJoin.setOnClickListener(this);

        m_EditLoginId = findViewById(R.id.EditLoginId);
        m_EditLoginPassword = findViewById(R.id.EditLoginPassword);

        Log.v(m_TAG, "onCreate Model :" + MODEL );

        if(GlobalVar.g_AccJoin.getAcc_phone_id() != null){
            m_EditLoginId.setText(GlobalVar.g_AccJoin.getAcc_phone_id());
            m_EditLoginPassword.setText(GlobalVar.g_AccJoin.getAcc_pwd());

        } else {
            m_EditLoginId.setText("");
            m_EditLoginPassword.setText("");
            m_EditLoginId.setText("01012345678");
            m_EditLoginPassword.setText("541212");
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            getInfoAndCheckLogIn();
            GlobalVar.g_AccJoin = new Account();
        } else if (view.getId() == R.id.btnJoin) {
            Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if(System.currentTimeMillis() - m_BackKeyPressTime > TConst.APP_FINISH_DELAY ) {
            m_BackKeyPressTime = System.currentTimeMillis();
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        } else {
            AppFinish(); // 액티비티 종료
        }
    }

    public void AppFinish(){
        moveTaskToBack(true); // 태스크를 백그라운드로 이동
        finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void getInfoAndCheckLogIn() {
        Log.v(m_TAG, "getInfoAndCheckLogIn");

        GlobalVar.g_AccLogin = new Account();
        String strLoginId = m_EditLoginId.getText().toString().trim();
        String strLoginPassword = m_EditLoginPassword.getText().toString().trim();

        Log.v(m_TAG,
                String.format("LogIn Id %s, Pass %s", strLoginId, strLoginPassword));

        AccountDAO accountDAO = new AccountDAO(m_Context);
        Account accSelect = accountDAO.selectLogin(strLoginId, strLoginPassword);
        accountDAO.releaseAndClose();

        if(accSelect != null){
            Toast toast = Toast.makeText(getApplicationContext(), "로그인 성공 : 메뉴 화면으로 이동합니다.",Toast.LENGTH_SHORT);
            toast.show();
            GlobalVar.g_AccLogin = accSelect;
            Log.v(m_TAG, GlobalVar.g_AccLogin.toString());

            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "로그인 실패 : 아이디와 비밀번호를 확인하세요.",Toast.LENGTH_SHORT);
            toast.show();

        }

    }


}