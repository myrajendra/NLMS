package com.jeevasamruddhi.telangana.nlms.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.common.PermissionUtil;
import com.jeevasamruddhi.telangana.nlms.android.common.SessionManager;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class SplashActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =100;

    protected static final String TAG = SplashActivity.class.getName();
    Context context;

    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                    //recreate();

                    /****** Create Thread *************/
                    Thread background = new Thread()
                    {
                        public void run()
                        {

                            try {
                                // Thread will sleep for 2 seconds
                                sleep(3*1000);

                                if(Util.isNetworkAvailable(getApplicationContext())) {
                                    new AppVersionAsync(getApplicationContext()).execute();
                                }else {

                                    Toast.makeText(context, "Sorry, no internet connectivity detected. Please reconnect and try again.", Toast.LENGTH_LONG).show();
                                    //Util.showAlertDialg(get, "Sorry, no internet connectivity detected. Please reconnect and try again.", "No Internet Connection", true);
                                }

                                //Remove activity
                                //finish();

                            } catch (Exception e) {
                                Log.d(TAG + "- Exception", e.getMessage());
                            }
                        }
                    };

                    // start thread
                    background.start();

                } else {
                    Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public class AppVersionAsync extends AsyncTask<String, Void, Response>
    {

        Context context;
        Response response;
        ExceptionMessage exceptionMessage;
        String version;

        //String virtualAddress, logUser, localAppVersion;

        public AppVersionAsync(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            this.response = new Response();
            this.response.setSuccessful(false);
            exceptionMessage = new ExceptionMessage(false, "", "");
            //version = SessionManager.getPreferences(getApplicationContext(), "version");
            version = context.getString(R.string.appVersion);
        }

        @Override
        protected Response doInBackground(String... params) {

            //final String url = getString(R.string.base_uri) + "/srdh/upi/registration";
            final String url = context.getString(R.string.base_url) + "/srdh/emp/appVersion";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, url);
                this.response = restTemplate.getForObject(url, Response.class);
            } catch (HttpClientErrorException e) {
                Log.d(TAG, "HttpClientErrorException");
                Log.e(TAG, e.getLocalizedMessage(), e);
                this.response.setErrorMessage(e.getStatusText() + " - " + e.getLocalizedMessage());
            } catch (ResourceAccessException e) {
                Log.d(TAG, "ResourceAccessException");
                Log.e(TAG, e.getLocalizedMessage(), e);
                this.response.setErrorMessage(e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            //displayResponse(response);
            Log.i("response : ", String.valueOf(response));
            if(exceptionMessage.getStatus()){
                Util.showAlertMsg(context, exceptionMessage.getMessage(), exceptionMessage.getTitle());
            }else {
                if (response.isSuccessful()) {
                    Log.i("responseObject : ", String.valueOf(response.getResponseObject()));
                    String appVersion = (String) response.getResponseObject();
                    if(appVersion != null && !appVersion.isEmpty())
                    {
                        version = context.getString(R.string.appVersion);

                        String logUser = SessionManager.getPreferences(SplashActivity.this, "logUser");

                        if(version !=null && !version.isEmpty())
                        {
                            if(!appVersion.equalsIgnoreCase(version))
                            {
                                Toast.makeText(getApplicationContext(), "outdated App Version", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(SplashActivity.this, UpdateActivity.class);
                                i.putExtra("version", appVersion);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                //SessionManager.setPreferences(SplashActivity.this, "version", appVersion);

                                if(logUser!=null && !logUser.isEmpty() && logUser.length() > 3)
                                {
                                    // Creating Bundle object
                                    Bundle bundle = new Bundle();
                                    bundle.putString("logUser", logUser);

                                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                                    i.putExtras(bundle);
                                    startActivity(i);
                                    finish();
                                }else {
                                    Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }
                        else
                        {
                            //SessionManager.setPreferences(SplashActivity.this, "version", appVersion);

                            if(logUser!=null && !logUser.isEmpty() && logUser.length() > 3)
                            {
                                // Creating Bundle object
                                Bundle bundle = new Bundle();
                                bundle.putString("logUser", logUser);

                                Intent i=new Intent(SplashActivity.this, MainActivity.class);
                                i.putExtras(bundle);
                                startActivity(i);
                                finish();
                            }else{
                                Intent i=new Intent(SplashActivity.this, SignInActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                    Log.i("appVersion : ", String.valueOf(appVersion));
                }else {
                    Toast.makeText(context, "response Status - " + response.isSuccessful() + " - " + response.getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
