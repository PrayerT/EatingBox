package com.example.eatingbox;

import android.util.Log;

public class Util {

    private static Boolean isTestMode = true;

    public static void testlog(String str){
        if (isTestMode){
            Log.i("testmode",str);
        }
    }

}
