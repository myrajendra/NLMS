package com.jeevasamruddhi.telangana.nlms.android.common;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by jaganmohan on 17/12/16.
 */

public class Util {
    public static LocationManager mLocationManager;

    public static void showAlertDialg(final Activity activity, String strMsg, String title, boolean isclose) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(strMsg).setTitle(title).setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                activity.finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertMsg(final Activity activity, String strMsg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(strMsg).setTitle(title).setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertMsg(Context cntxt, String strMsg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);
        builder.setMessage(strMsg).setTitle(title).setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String getSystemIMEI(Context context) {
        try {
            TelephonyManager ex = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceIMEI = ex.getDeviceId();
            if (deviceIMEI == null) {
                deviceIMEI = Settings.Secure.getString(context.getContentResolver(), "android_id");
            } else if (deviceIMEI.equals("") || deviceIMEI.equals("null")) {
                deviceIMEI = Settings.Secure.getString(context.getContentResolver(), "android_id");
            }

            return deviceIMEI;
        } catch (Exception var3) {
            return "";
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Toast.makeText(context, "Data connection not available", Toast.LENGTH_LONG).show();
            //showAlertMsg(context, "No Internet Connection", "Sorry, no internet connectivity detected. Please reconnect and try again.");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNetworkReady(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static String geocode(Context context, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        new ArrayList();
        String str = "";

        try {
            List addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            str = ((Address) addresses.get(0)).getLocality().toString() + "," + ((Address) addresses.get(0)).getAdminArea() + "," + ((Address) addresses.get(0)).getCountryName().toString();
            if (str != null && !str.equalsIgnoreCase("") && str != "null") {
                str = ((Address) addresses.get(0)).getLocality().toString() + "," + ((Address) addresses.get(0)).getAdminArea() + "," + ((Address) addresses.get(0)).getCountryName().toString();
            } else {
                str = "India";
            }
        } catch (IOException var6) {
            var6.printStackTrace();
            str = "India";
        }

        return str;
    }

    public static Location getLastKnownLocation(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        Iterator var3 = providers.iterator();

        while (true) {
            Location l;
            do {
                do {
                    if (!var3.hasNext()) {
                        return bestLocation;
                    }

                    String provider = (String) var3.next();

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    l = mLocationManager.getLastKnownLocation(provider);
                } while(l == null);
            } while(bestLocation != null && l.getAccuracy() >= bestLocation.getAccuracy());

            bestLocation = l;
        }
    }
}
