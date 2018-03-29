package com.jeevasamruddhi.telangana.nlms.android.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jaganmohan on 16/12/16.
 */

public class SessionManager
{
    public static void setPreferences(Context context, String key, String value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("TS-NLMS", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }



    public static String getPreferences(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences("TS-NLMS",	Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}
