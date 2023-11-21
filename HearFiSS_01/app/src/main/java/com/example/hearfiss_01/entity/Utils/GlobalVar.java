package com.example.hearfiss_01.entity.Utils;

import com.example.hearfiss_01.entity.HearingTest.HrTestGroup;
import com.example.hearfiss_01.entity.HearingTest.HrTestSet;
import com.example.hearfiss_01.entity.HearingTest.HrTestUnit;

public class GlobalVar {

    public static int g_MenuNum = -1;
    public static String g_MenuType = "";
    public static String g_MenuSide = "";
    public static String g_RightResult = "";
    public static String g_leftResult = "";
    public static int g_unitCount = 0;

    public static Account g_AccLogin = null;
    public static HrTestGroup g_TestGroup = null;

    public static int g_userVolume = 7;

    public static HrTestSet g_HrTestSet = null;

    public static HrTestUnit g_HrTestUnit = null;

    public static int g_PTT_Ts_id_R = 0;
    public static int g_PTT_Ts_id_L = 0;

}

