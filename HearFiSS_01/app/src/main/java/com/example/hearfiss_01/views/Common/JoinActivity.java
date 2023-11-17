package com.example.hearfiss_01.views.Common;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.db.dao.AccountDAO;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

public class JoinActivity extends AppCompatActivity
        implements View.OnClickListener
            , DatePickerDialog.OnDateSetListener
            , View.OnFocusChangeListener
            , CompoundButton.OnCheckedChangeListener {
    String m_TAG = "JoinActivity";
    Context m_Context;

    EditText m_EditName;
    EditText m_EditPhone;
    EditText m_EditBirthDay;

    DatePickerDialog m_datePickerDialog;

    ToggleButton m_ToggleMale;
    ToggleButton m_ToggleFemale;
    AppCompatButton m_AppBtnJoin;

    int m_ResIdBlueButton;
    int m_ResIdGrayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        m_Context = JoinActivity.this;

        m_EditName = findViewById(R.id.EditJoinName);
        m_EditPhone = findViewById(R.id.EditJoinPhone);
        m_EditBirthDay = findViewById(R.id.EditJoinBirthday);

        m_EditPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        m_EditBirthDay.setOnClickListener(this);
        m_EditBirthDay.setOnFocusChangeListener(this);

        m_EditName.setOnFocusChangeListener(this);
        m_EditPhone.setOnFocusChangeListener(this);


//        Calendar c = Calendar.getInstance();
//        int iYear = c.get(Calendar.YEAR);
//        int iMonth = c.get(Calendar.MONTH);
//        int iDay = c.get(Calendar.DAY_OF_MONTH);

        int iYear = 1980;
        int iMonth = 1;
        int iDay= 1;

        m_datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT , this, iYear,iMonth,iDay);

        m_ToggleMale = findViewById(R.id.AppBtnJoinMale);
        m_ToggleMale.setOnCheckedChangeListener(this);

        m_ToggleFemale = findViewById(R.id.AppBtnJoinFemale);
        m_ToggleFemale.setOnCheckedChangeListener(this);

        m_AppBtnJoin = findViewById(R.id.AppBtnJoinJoin);
        m_ResIdBlueButton = getResources().getIdentifier("blue_button","drawable", getPackageName());
        m_ResIdGrayButton = getResources().getIdentifier("gray_button","drawable", getPackageName());
        checkJoinBtnClickable();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.EditJoinBirthday) {
            m_datePickerDialog.show();

        }else if (view.getId() == R.id.AppBtnJoinJoin) {
            onClickJoinBtn();
        }
        checkJoinBtnClickable();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        int iShortYear = year % 100;

        m_EditBirthDay.setText(String.format("%02d-%02d-%02d", iShortYear, (month+1), day));
    }

    @Override
    public void onFocusChange(View view, boolean isFocused) {
        if (view.getId() == R.id.EditJoinBirthday && isFocused) {
            m_datePickerDialog.show();
        }
        checkJoinBtnClickable();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            compoundButton.setTextColor(getColor(R.color.white));
            uncheckOtherToggleButton(compoundButton);

        } else {
            compoundButton.setTextColor(getColor(R.color.gray));
            checkOtherToggleButton(compoundButton);

        }
        checkJoinBtnClickable();
    }

    private void uncheckOtherToggleButton(CompoundButton compoundButton){
        if(compoundButton.getId() == R.id.AppBtnJoinMale) {
            m_ToggleFemale.setChecked(false);

        } else if (compoundButton.getId() == R.id.AppBtnJoinFemale) {
            m_ToggleMale.setChecked(false);

        }
    }

    private void checkOtherToggleButton(CompoundButton compoundButton){
        if(compoundButton.getId() == R.id.AppBtnJoinMale) {
            m_ToggleFemale.setChecked(true);

        } else if (compoundButton.getId() == R.id.AppBtnJoinFemale) {
            m_ToggleMale.setChecked(true);

        }
    }

    private void checkJoinBtnClickable(){

        if(isAccountInfoAllChecked()){
            m_AppBtnJoin.setClickable(true);
            m_AppBtnJoin.setBackgroundResource(m_ResIdBlueButton);
            m_AppBtnJoin.setOnClickListener(this);

        } else {
            m_AppBtnJoin.setClickable(false);
            m_AppBtnJoin.setBackgroundResource(m_ResIdGrayButton);

        }

    }

    private boolean isAccountInfoAllChecked(){
        String strPhone = m_EditPhone.getText().toString().trim();
        String strName = m_EditName.getText().toString().trim();
        String strBirthDay = m_EditBirthDay.getText().toString().trim();

        if((strPhone.length() < 10) || (strName.length() < 1) || (strBirthDay.length() < 6) ){
            return false;
        }

        if(m_ToggleMale.isChecked() || m_ToggleFemale.isChecked()) {
            return true;
        } else {
            return false;
        }
    }

    private void onClickJoinBtn() {
        Log.v(m_TAG,
                String.format("onClickJoinBtn "));
        if(!isAccountInfoAllChecked()){
            showMessageJoinFail();
            return;
        }

        AccountDAO accountDAO = new AccountDAO(m_Context);

        Account accInsert = getAccountFromScreen();
        int iRetInsert = accountDAO.insertAccount(accInsert);

        accountDAO.releaseAndClose();

        if( 1 == iRetInsert ){
            Log.v(m_TAG, String.format("insertAccount is Success "));
            GlobalVar.g_AccJoin = accInsert;
            Intent intent = new Intent(getApplicationContext(), JoinResultActivity.class);
            startActivity(intent);
            finish();

        } else {
            Log.v(m_TAG, String.format("insertAccount is Fail "));
            showMessageJoinFail();
        }

    }
    private void showMessageJoinFail() {
        GlobalVar.g_AccJoin = new Account();
        Toast toast = Toast.makeText( getApplicationContext(),
                "회원가입 실패 : 입력값을 확인하세요.",
                Toast.LENGTH_SHORT);
        toast.show();

    }

    private Account getAccountFromScreen(){
        try {
            return tryGetAccountFromScreen();
        } catch (Exception e) {
            Log.v(m_TAG, "getAccountFromScreen Exception " + e);
            return new Account();
        }
    }

    private Account tryGetAccountFromScreen() {
        String strPhone = m_EditPhone.getText().toString().trim();
        String strName = m_EditName.getText().toString().trim();
        String strBirthDay = m_EditBirthDay.getText().toString().trim();
        String strGender = "";

        if (m_ToggleMale.isChecked()) {
            strGender = TConst.STR_GENDER_MALE;
        } else {
            strGender = TConst.STR_GENDER_FEMALE;
        }
        String strPhoneId = strPhone.replace("-", "");
        String strPassword = strBirthDay.replace("-", "");

        Log.v(m_TAG,
                String.format("getAccountFromScreen Name:%s, Phone:%s, Birth:%s, Gender:%s, PhoneId:%s, Pwd:%s",
                        strName, strPhone, strBirthDay, strGender, strPhoneId, strPassword));
        Account accGet = new Account(0, strPhoneId, strPhone, strPassword,
                strName, strGender, strBirthDay);

        return accGet;
    }
}



