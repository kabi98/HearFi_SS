package com.example.hearfiss_01.audioTest.SRT;

public class SrtThreshold implements Comparable<SrtThreshold>{

    int m_DBHL;

    public SrtThreshold(){

    }
    public SrtThreshold(int _DBHL){
        this.m_DBHL = _DBHL;
    }

    public int get_DBHL(){
        return m_DBHL;
    }

    public void set_DBHL(int _DBHL){
        this.m_DBHL = _DBHL;
    }

    @Override
    public String toString(){
        return "SrtThreshold{" +
                "m_DBHL=" + m_DBHL +
                "}";
    }

    @Override
    public int compareTo(SrtThreshold srtThreshold) {
        if(this.m_DBHL > srtThreshold.get_DBHL()){
            return 1;
        } else if(this.m_DBHL < srtThreshold.get_DBHL()){
            return -1;
        } else {
            return 0;
        }
    }
}
