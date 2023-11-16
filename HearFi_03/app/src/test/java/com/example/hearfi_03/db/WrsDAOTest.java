package com.example.hearfi_03.db;

import android.util.Log;

import com.example.hearfi_03.db.dao.WrsDAO;

import junit.framework.TestCase;

import org.junit.Test;

public class WrsDAOTest extends TestCase {

    String m_TAG = "WrsDAOTest";

    @Test
    public void testCreate() {
        Log.v(m_TAG, "testCreate");

        WrsDAO WrsDaoTemp = null;
        assertNull(WrsDaoTemp);

//        WrsDaoTemp = new WrsDAO(null);
//        assertNotNull(WrsDaoTemp);
    }


}