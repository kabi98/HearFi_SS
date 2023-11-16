package com.example.hearfi_03.audioTest;

import android.util.Log;

import com.example.hearfi_03.audioTest.PTT.PureTonePlayer;

import junit.framework.TestCase;

import org.junit.Test;

public class PureTonePlayerTest extends TestCase {

//    private Context context = ApplicationProvider.getApplicationContext();
    @Test
    public void testCreate() {
        Log.v("PureTonePlayerTest", "testPttCreate");

        PureTonePlayer ptpTemp = null;
        assertNull(ptpTemp);

//        ptpTemp = new PureTonePlayer(context);
//        assertNotNull(ptpTemp);
    }


}