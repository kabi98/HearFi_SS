package com.example.hearfi_03.audioTest.PTT;

public class PttThreshold implements Comparable<PttThreshold>{
    int     m_Hz;
    int     m_DBHL;

    public PttThreshold() {

    }

    public PttThreshold(int _Hz, int _DBHL) {
        this.m_Hz = _Hz;
        this.m_DBHL = _DBHL;
    }

    public int get_Hz() {
        return m_Hz;
    }

    public void set_Hz(int _Hz) {
        this.m_Hz = _Hz;
    }

    public int get_DBHL() {
        return m_DBHL;
    }

    public void set_DBHL(int _DBHL) {
        this.m_DBHL = _DBHL;
    }

    @Override
    public String toString() {
        return "PttThreshold{" +
                "m_Hz=" + m_Hz +
                ", m_DBHL=" + m_DBHL +
                '}';
    }

    @Override
    public int compareTo(PttThreshold pttThreshold) {
        if(this.m_Hz > pttThreshold.get_Hz()){
            return 1;
        } else if(this.m_Hz < pttThreshold.get_Hz()){
            return -1;
        } else {
            return 0;
        }
    }
}
