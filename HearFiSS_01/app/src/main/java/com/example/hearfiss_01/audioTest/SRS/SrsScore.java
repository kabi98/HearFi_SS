package com.example.hearfiss_01.audioTest.SRS;

import android.util.Log;

import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.db.dao.SrsDAO;
import com.example.hearfiss_01.global.GlobalVar;

import java.util.ArrayList;
import java.util.Arrays;

public class SrsScore {

    String m_TAG = "SrsScore";
    ArrayList<HrTestUnit> m_allUnit = null;
    String question;
    String answer;
    ArrayList<StWord> m_alWord = null;

    ArrayList<String> u_A;

    ArrayList<String> c_Q;


    AmTrack m_atCur = null;
    SrsDAO srsDAO;

    public SrsScore(){
        m_allUnit = new ArrayList<HrTestUnit>();
        question = "";
        answer = "";
        m_alWord = new ArrayList<StWord>();
    }

    public ArrayList<HrTestUnit> getM_allUnit() {
        return m_allUnit;
    }

    public void setM_allUnit(ArrayList<HrTestUnit> m_allUnit) {
        this.m_allUnit = m_allUnit;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<StWord> getM_alWord() {
        return m_alWord;
    }

    public void setM_alWord(ArrayList<StWord> m_alWord) {
        this.m_alWord = m_alWord;
    }

    public int scoring(){
       /*
        // 단어 기준
        int wResult = (int)(((float)(cWord) / (float)(tWord)*100));
        int sResult = (int)(((float) (cSentence) / (float) (10)*100));

        Log.v("SaveAnswer", "단어 정답 개수 : " + cWord + "개");
        Log.v("SaveAnswer", "단어 기준 : " + wResult + "%");

        Log.v("SaveAnswer", "문장 정답 개수 : " + cSentence + "개");
        Log.v("SaveAnswer", "문장 기준 : " + sResult + "%");
        HrTestSet hrtestSet = insertSet(wResult,sResult);
        if(hrtestSet != null){
            insertUnit(unitList, hrtestSet);
        }
        if(GlobalVar.g_MenuSide.equals("RIGHT")){
            GlobalVar.g_RightResult = Integer.toString(sResult) +"%";
        }else{
            GlobalVar.g_leftResult = Integer.toString(sResult) +"%";
        }
        return sResult;
        */
        // 임시 return
        return 0;
    }

    public ArrayList<String> sameSize(String strAnswer){
        ArrayList<StWord> alword = srsDAO.selectWordFromId(m_atCur.getAt_id());
        String question = m_atCur.getAt_content();
        // AmTrack - question 배열
        c_Q = split_Answer(question);
        String answer = strAnswer.trim();
        int iLen = -1;
        while(iLen != answer.length()){
            iLen = answer.length();
            answer = answer.replace("  ", " ");
        }
        // 사용자 - Answer 배열
        u_A = split_Answer(answer);
        Log.v(m_TAG,"createList : " + c_Q.size());
        Log.v(m_TAG,"createList : " + u_A.size());
        if(u_A.size() < c_Q.size()){
            int gap = c_Q.size()-u_A.size();
            for(int i=0; i<gap; i++){
                u_A.add("");
            }
            Log.v(m_TAG,"createList : " + u_A.size());
            Log.v(m_TAG,"createList : " + u_A.toString());
        }
        return u_A;
    }

    public ArrayList<String> split_Answer(String answer) {
        String[] u_temp = answer.split(" ");
        ArrayList<String> u_A = new ArrayList<>(Arrays.asList(u_temp));
        // 사용자 응답 (스페이스 공백 기준 나누기)
        for (int i = 0; i < u_A.size(); i++) {
            if (!u_A.get(i).isEmpty()) {
                Log.v(m_TAG, "array shape[" + i + "] : " + u_A.get(i));
            }
        }
        return u_A;
    }
}



