package com.example.hearfi_03.views.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.hearfi_03.R;

@SuppressLint("AppCompatCustomView")
public class ImageProgress extends ImageView {
    final String STR_PRGS = "progress";
    final String STR_TYPE = "drawable";
    final int INT_MIN = 0;
    final int INT_MAX = 7;

    int m_iCurProgress = 0;


    Context m_Context = null;
    Resources m_Res = null;
    String m_strPack = "";


    public ImageProgress(Context context) {
        super(context);
        m_Context = context;
        Init();
    }

    public ImageProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        m_Context = context;
        Init();
    }

    private void Init(){
        m_Res = m_Context.getResources();
        m_strPack = m_Context.getPackageName();
        setProgress(0);
    }

    private int checkProgressNum(int iNum) {
        if(iNum < INT_MIN) {
            iNum = INT_MIN;
        } else if (INT_MAX < iNum) {
            iNum = INT_MAX;
        }
        return iNum;
    }

    private int getResIdFromInt(int iNum) {

        Log.v("ImageProgress", String.format("getResIdFromInt Start - Num : %d", iNum));

        String strName = String.format("%s%02d", STR_PRGS, iNum);
        int idRes = m_Res.getIdentifier(strName, STR_TYPE, m_strPack);

        Log.v("ImageProgress",
                String.format( "getResIdFromInt End - Num : %d, idRes : %d, strName : %s",
                        iNum, idRes, strName));
        return idRes;
    }

    public void setProgress(int iProgress) {
        Log.v("ImageProgress", "setProgress");
        m_iCurProgress = checkProgressNum(iProgress);
        int idRes = getResIdFromInt(m_iCurProgress);
        setImageResource(idRes);
    }

    public int getProgress() {
        Log.v("ImageProgress", "getProgress");
        return m_iCurProgress;
    }


}
