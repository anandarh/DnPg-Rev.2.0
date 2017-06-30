package com.anandarherdianto.dinas.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ananda R. Herdianto on 26/04/2017.
 */

public class SessionManager {

    private static final String TAG = "Session";

    // Shared Preferences
    SharedPreferences pref, pref2, pref3;

    SharedPreferences.Editor editor, editor2, editor3;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AppLogin";
    private static final String PREF_NAME_2 = "AppWeather";
    private static final String PREF_NAME_3 = "UploadStatus";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_UPDATE_TEMP = "updateTemp";
    private static final String KEY_IS_UPLOADED = "isUploaded";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        pref2 = _context.getSharedPreferences(PREF_NAME_2, PRIVATE_MODE);
        editor2 = pref2.edit();

        pref3 = _context.getSharedPreferences(PREF_NAME_3, PRIVATE_MODE);
        editor3 = pref3.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setTemp(String temp){
        editor2.putString(KEY_UPDATE_TEMP, temp);

        // commit changes
        editor2.commit();

        Log.d(TAG, "Temperature Updated!");
    }

    public String getTemp(){
        return pref2.getString(KEY_UPDATE_TEMP, "0");
    }

    public void setUpload(boolean isUploaded) {
        editor3.putBoolean(KEY_IS_UPLOADED, isUploaded);

        // commit changes
        editor3.commit();

        Log.d(TAG, "Upload session modified!");
    }

    public boolean isUploaded(){
        return pref3.getBoolean(KEY_IS_UPLOADED, false);
    }
}
