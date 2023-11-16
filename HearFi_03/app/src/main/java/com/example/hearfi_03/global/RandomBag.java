package com.example.hearfi_03.global;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class RandomBag {
    ArrayList<Integer> IntBag;
    int iLimit;

    public RandomBag() {
        Log.v("RandomBag", "RandomBag()");
        iLimit = 0;
        IntBag = new ArrayList<Integer>();
    }

    public int getiLimit() {
        Log.v("RandomBag", "getiLimit()");
        return iLimit;
    }

    public void setiLimit(int iLimit) {
        Log.v("RandomBag", "setiLimit()");
        this.iLimit = iLimit;
        filling();
    }

    public void filling(){
        IntBag.clear();

        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        rand.nextInt();

        int iRand;
        for(int i =0; i < iLimit; i++){
            iRand = rand.nextInt(iLimit);
            while (IntBag.contains(iRand)) {
                iRand = rand.nextInt(iLimit);
            }

            IntBag.add(iRand);
        }
    }

    public int getNext(){
        if(IntBag.size() <= 0)
            filling();
        return IntBag.remove(0);
    }

    public void printBag() {
        Log.v("RandomBag", "printBag() -----------------------------------------");

        StringBuilder sbTemp = new StringBuilder();

        for(int i=0; i<IntBag.size(); i++){
            sbTemp.append( String.format(" %02d,", IntBag.get(i)) ) ;
        }

        Log.v("RandomBag", "printBag() - size = " + IntBag.size());
        Log.v("RandomBag", sbTemp.toString() );

        Log.v("RandomBag", "printBag() -----------------------------------------");
    }

}
