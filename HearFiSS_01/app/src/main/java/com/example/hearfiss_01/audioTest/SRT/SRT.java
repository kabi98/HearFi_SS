package com.example.hearfiss_01.audioTest.SRT;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.audioTest.PTT.PttScore;
import com.example.hearfiss_01.audioTest.PTT.PureTonePlayer;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

import java.util.ArrayList;

public class SRT {
    String m_TAG = "SRT";
    Context     m_Context = null;
//    String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음"};



    public SRT(@Nullable Context context) {
        this.m_Context = context;
        startTest();
    }

    public void startTest() {
        Log.v(m_TAG, "startTest");
    }


}
