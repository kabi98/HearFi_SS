package com.example.hearfi_03.audioTest.PTT;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfi_03.db.dao.PttDAO;
import com.example.hearfi_03.db.DTO.PureToneTrack;
import com.example.hearfi_03.global.TConst;

public class PureTonePlayer implements MediaPlayer.OnCompletionListener {
    String m_TAG = "PureTonePlayer";

    Context         m_Context = null;
    MediaPlayer     m_Player = null;
    AudioManager    m_AudioMan= null;

    PttDAO          m_PttDAO = null;

    int             m_iVolumeSide = 0;


    public PureTonePlayer(@Nullable Context context) {
        Log.v("PureTonePlayer", "PureTonePlayer");
        m_Context = context;

        m_PttDAO = new PttDAO(m_Context);
//        m_PttDAO.setStrDevice();
//        m_PttDAO.setStrPhone();

        m_AudioMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    }

    public int closeAll() {
        try {
            stopPlayer();
            if(m_PttDAO != null)
            {
                m_PttDAO.releaseAndClose();
                m_PttDAO = null;
            }

            return 1;

        } catch (Exception e) {
            Log.v(m_TAG, "closeAll Exception " + e);
            return 0;
        }
    }

    public void setPhoneAndDevice(String strPhone, String strDevice){
        m_PttDAO.setStrPhone(strPhone);
        m_PttDAO.setStrDevice(strDevice);
    }

    public int playPureToneFromHzDBHL(int iHz, int iDBHL){
        try {
            Log.v(m_TAG,
                    String.format("playPureToneFromHzDBHL Hz:%d, DBHL:%d", iHz, iDBHL));

            if( replayPause() ){
                return 1;
            }

            stopPlayer();
            int idFind = findIdFromHzDBHL(iHz, iDBHL);
            playFromId(idFind);

            return 1;
        } catch (Exception e) {
            Log.v(m_TAG, "playPureToneFromHzDBHL Exception " + e);
            return 0;
        }
    }

    public int playPureToneFromName(String strName){
        try {
            Log.v(m_TAG, String.format("playPre"));
            if( replayPause() ){
                return 1;
            }

            stopPlayer();
            int idFind = findIdFromName(strName);
            playFromId(idFind);
            return 1;

        } catch (Exception e) {
            Log.v(m_TAG, "PureTonePlayer Exception " + e);
            return 0;

        }
    }

    public int stopPlayer(){
        Log.v(m_TAG, "release");
        try {
            if (m_Player != null) {
                m_Player.stop();
                m_Player.release();
                m_Player = null;
            }
            return 1;

        } catch (Exception e) {
            Log.v(m_TAG, "stopPlayer Exception " + e);
            return 0;
        }
    }

    public boolean replayPause(){
        try {
            if (m_Player == null) {
                return false;
            }

            return tryReplayPause();
        } catch (Exception e) {
            Log.v(m_TAG, "replayPause Exception " + e);
            return false;
        }
    }

    private boolean tryReplayPause(){
        if(m_Player.isPlaying()
                || m_Player.getCurrentPosition() <= 0){
            return false;
        }

        setVolumeSideCheck();
        m_Player.setLooping(true);
        m_Player.start();
        return true;
    }


    public int pausePlay(){
        try {
            if (m_Player != null) {
                if(m_Player.isPlaying()){
                    m_Player.pause();
                }
            }

            return 1;
        } catch (Exception e) {
            Log.v(m_TAG, "pausePlay Exception " + e);
            return 0;
        }
    }
    private boolean playFromId(int idRes){
        m_Player = MediaPlayer.create(m_Context, idRes);
        m_Player.setOnCompletionListener(this);

        setVolumeSideCheck();
        m_Player.setLooping(true);
        m_Player.start();

        return true;
    }

    private void setVolumeSideCheck(){
        Log.v(m_TAG, String.format("playFromId - side:%d", m_iVolumeSide));
        if(m_iVolumeSide == TConst.T_RIGHT){
            m_Player.setVolume(0.0f, 1.0f); // Right Only

        } else if(m_iVolumeSide == TConst.T_LEFT) {
            m_Player.setVolume(1.0f, 0.0f); // Left Only
        }

    }


    private int findIdFromHzDBHL(int iHz, int iDBHL) {
        PureToneTrack ptTrackGet = m_PttDAO.selectTrackFromHzDBHL(iHz, iDBHL);
        String strName = ptTrackGet.getPt_file_name();

        int idFind = findIdFromName(strName);
        Log.v(m_TAG,
                String.format("findIdFromHzDBHL - Hz:%d, DBHL:%d, name:%s, idFind : %d",
                        iHz, iDBHL, strName, idFind));

        return idFind;
    }

    private int findIdFromName(String strName) {
        int idFind = m_Context.getResources().getIdentifier(strName, "raw", m_Context.getPackageName());
        Log.v(m_TAG, String.format("findIdFromName - name:%s, idFind : %d", strName, idFind));
        return idFind;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(m_TAG, "onCompletion ");

    }

    public int setVolumeMax(){
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.v(m_TAG, String.format("setVolumeMax - MaxVolume : %d", MaxVol));
        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, MaxVol,0);
        return 1;
    }

    public int setVolumeMedium(){
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int MinVol = m_AudioMan.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        int NewVol = (MaxVol + MinVol) / 2;
        Log.v(m_TAG, String.format("setVolumeMax - MediumVolume : %d", NewVol));
        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, NewVol,0);
        return 1;
    }

    public int setVolumeSide(int iSide){
        m_iVolumeSide = iSide;
        return 1;
    }

}
