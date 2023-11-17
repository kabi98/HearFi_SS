package com.example.hearfiss_01.global;

public final class TConst {
    public static final String DB_FILE = "AudioMetry.db";
    public static final int DB_VER = 1;

    public static final String COPY_FILE = "HearFi.db";

    public static final String DEFAULT_PHONE = "A24";
    public static final String DEFAULT_EARPHONE = "BUDS2";
//    public static final String DEFAULT_HEADPHONE = "MOMENTUM3";
    public static final String DEFAULT_HEADPHONE = "W800BT";

    public static final int PTT_PRE_HZ = 1000;
    public static final int PTT_PRE_DBHL = 40;

    public static final int HEADSET = 101;
    public static final int EARPHONE = 102;

    public static final  int NO_HEARING = 0;
    public static final  int HEARING = 1;

    public static final int T_RIGHT = 11;
    public static final int T_LEFT = 12;

    public static final int HZ_ORDER [] = { 1000, 2000, 4000, 8000, 500, 250 };
    public static final int MIN_DBHL = 0;
    public static final int MAX_DBHL = 100;

    public static final int HEARING_LOSS_PTA []     = {20,      40,         55,         70,         90,         100};
    public static final String HEARING_LOSS_STR []  = {"정상",   "경도난청",  "중도난청",  "중고도난청", "고도난청",  "심도난청"};

    public static final int WRS_SCORE_BOUNDARY []   = {96,          86,     80,     70,     50,     0};
    public static final String WRS_SCORE_STR []     = {"매우 우수",  "우수",  "좋음",  "보통", "저조",  "매우 저조"};

    public static final int T_PTT = 21;
    public static final int T_WRS = 22;

    public static final String STR_PTT_TYPE = "PTA";
    public static final String STR_WRS_TYPE = "WRS";

    public static final String STR_LEFT_SIDE = "LEFT";
    public static final String STR_RIGHT_SIDE = "RIGHT";

    public static final String STR_GENDER_MALE = "남";
    public static final String STR_GENDER_FEMALE = "여";

    public static final int APP_FINISH_DELAY = 2000; // milli_seconds
    public static final int PTT_NO_HEARING_DELAY = 1000; // milli_seconds


}
