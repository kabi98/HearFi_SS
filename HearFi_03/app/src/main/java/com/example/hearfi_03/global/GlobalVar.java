package com.example.hearfi_03.global;

import com.example.hearfi_03.audioTest.PTT.PttThreshold;
import com.example.hearfi_03.audioTest.WRS.WordUnit;
import com.example.hearfi_03.db.DTO.HrTestGroup;
import com.example.hearfi_03.db.DTO.Account;

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

    public static int g_wrsNumber = 0;

    public static Account g_AccLogin = new Account();
    public static Account g_AccJoin = new Account();

    public static int g_wrsUserVolume = 0;
}

