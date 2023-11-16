package com.example.hearfi_03.views.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.hearfi_03.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton loginBtn;
    ImageButton joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(this);
//        loginBtn.setEnabled(false);

        joinBtn = findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(this);
//        joinBtn.setEnabled(false);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.joinBtn) {
            Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
            startActivity(intent);

        } else if (view.getId() == R.id.btnLogin) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

    }
}