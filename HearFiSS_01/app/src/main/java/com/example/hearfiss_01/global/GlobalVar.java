package com.example.hearfiss_01.global;

import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.audioTest.SRS.SRS;
import com.example.hearfiss_01.audioTest.SRT.SrtUnit;
import com.example.hearfiss_01.audioTest.WRS.WordUnit;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.Account;

import java.util.ArrayList;

public class GlobalVar {

    public static String g_PttStrDevice = "";
    public static String g_PttStrPhone = "";

    public static int g_TestType = 0;
    public static int g_TestSide = 0;
    public static int g_PttRightDBHL = 0;
    public static int g_PttLeftDBHL = 0;


    public static ArrayList<PttThreshold> g_alPttRightThreshold = new ArrayList<>();
    public static ArrayList<PttThreshold> g_alPttLeftThreshold = new ArrayList<>();

    public static HrTestGroup g_TestGroup = new HrTestGroup();


    public static ArrayList<WordUnit> g_alWrsRight = new ArrayList<>();
    public static ArrayList<WordUnit> g_alWrsLeft = new ArrayList<>();

    public static ArrayList<SrtUnit> g_alSrtLeft = new ArrayList<>();
    public static ArrayList<SrtUnit> g_alSrtRight = new ArrayList<>();

    public static ArrayList<SRS> g_alSrsLeft = new ArrayList<>();

    public static ArrayList<SRS> g_alSrsRight = new ArrayList<>();

    public static int g_wrsNumber = 0;

    public static int g_srsNumber = 0;

    public static int g_srtNumber = 0;


    public static Account g_AccLogin = new Account();
    public static Account g_AccJoin = new Account();

    public static int g_wrsUserVolume = 0;
    public static int g_srtUserVolume = 0;

    public static int g_srsUserVolume = 0;

    public static String g_RightResult = "";
    public static String g_leftResult = "";

}

