package com.example.hearfi_03.global;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

public class ShareUtil {
    static String m_TAG = "ShareUtil";

    public static void startActivityAndFinish(Class<?> clsStart, Activity view_activity) {
        Log.v(m_TAG, "startActivityAndFinish-start class" + clsStart);
        Intent intent = new Intent(view_activity.getApplicationContext(), clsStart);
        view_activity.startActivity(intent);
        view_activity.finish();
    }


    public static void changeTextColorFromStartToEnd(int idRes, String strColor, int iStart, int iEnd, Activity view_activity){
        Log.v(m_TAG, String.format("changeTextColorFromStartToEnd-start") );
        TextView tvText = view_activity.findViewById(idRes);
        String strText = tvText.getText().toString();
        SpannableStringBuilder ssbText = new SpannableStringBuilder(strText);
        ssbText.setSpan(new ForegroundColorSpan(Color.parseColor(strColor)),iStart,iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvText.setText(ssbText);

    }

}
