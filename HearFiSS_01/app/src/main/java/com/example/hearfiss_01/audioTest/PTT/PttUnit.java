package com.example.hearfiss_01.audioTest.PTT;

public class PttUnit {
    int     m_Hz;
    int     m_DBHL;
    int     m_IsHearing;
    public PttUnit() {
        m_Hz = 0;
        m_DBHL = 0;
        m_IsHearing = 0;
    }

    public PttUnit(int _Hz, int _DBHL, int _IsHearing) {
        this.m_Hz = _Hz;
        this.m_DBHL = _DBHL;
        this.m_IsHearing = _IsHearing;
    }

    public int get_Hz() {
        return m_Hz;
    }

    public void set_Hz(int m_Hz) {
        this.m_Hz = m_Hz;
    }

    public int get_DBHL() {
        return m_DBHL;
    }

    public void set_DBHL(int m_DBHL) {
        this.m_DBHL = m_DBHL;
    }

    public int get_IsHearing() {
        return m_IsHearing;
    }

    public void set_IsHearing(int m_IsHearing) {
        this.m_IsHearing = m_IsHearing;
    }

    @Override
    public String toString() {
        return "PttUnit{" +
                "_Hz=" + m_Hz +
                ", _CurDBHL=" + m_DBHL +
                ", _IsHearing=" + m_IsHearing +
                '}';
    }
}
