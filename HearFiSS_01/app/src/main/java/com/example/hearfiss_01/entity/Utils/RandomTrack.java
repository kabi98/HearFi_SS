package com.example.hearfiss_01.entity.Utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.sql.SQLiteControl;
import com.example.hearfiss_01.db.sql.SQLiteHelper;

import java.util.ArrayList;

public class RandomTrack {
    SQLiteControl m_sqlcon = null;
    SQLiteHelper m_helper = null;
    ArrayList<AmTrack> m_alTrack = null;
    String m_Type = "";

    public RandomTrack(@Nullable Context context) {
        Log.v("RandomTrack", "RandomTrack()");

        m_helper = new SQLiteHelper(context,  TConst.DB_FILE, null, TConst.DB_VER);
        m_sqlcon = new SQLiteControl(m_helper);
        m_alTrack = new ArrayList<AmTrack>();
    }

    public String getM_Type() {
        Log.v("RandomTrack-getM_Type", m_Type);
        return m_Type;
    }

    public void setM_Type(String m_Type) {
        Log.v("RandomTrack-setM_Type", m_Type);

        this.m_Type = m_Type;
        filling();
    }

    public void filling(){

        ArrayList<AmTrack> alTemp = m_sqlcon.selectTrackFromType(m_Type);

        RandomBag rbTemp = new RandomBag();
        rbTemp.setiLimit(alTemp.size());

        m_alTrack.clear();

        for(int i=0; i < alTemp.size(); i++) {
            m_alTrack.add(alTemp.get(rbTemp.getNext()));
        }

        printTrackContent();
    }

    public AmTrack getNext(){
        if(m_alTrack.size() <= 0)
            filling();
        return m_alTrack.remove(0);
    }

    public int size(){
        return m_alTrack.size();
    }



    public void printTrackContent() {

        Log.v("RandomTrack - printTrack", "printTrackContent" );
        StringBuilder sbTemp = new StringBuilder();
        Log.v("RandomTrack", "printTrackContent() -----------------------------------------");
        if(GlobalVar.g_MenuType.equals("SRS")){
            for(int i=0; i<m_alTrack.size(); i++){
                sbTemp.append( String.format(" %s\n,", m_alTrack.get(i).getAt_content()) ) ;
            }
        }else{
            for(int i=0; i<m_alTrack.size(); i++){
                sbTemp.append( String.format(" %s,", m_alTrack.get(i).getAt_content()) ) ;
            }
        }

        Log.v("RandomTrack", sbTemp.toString());
        Log.v("RandomTrack", "printTrackContent() -----------------------------------------");
    }


}
