package com.jeevasamruddhi.telangana.nlms.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.common.SessionManager;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.Login;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;
import com.jeevasamruddhi.telangana.nlms.android.model.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jaganmohan on 27-02-2018.
 */

public class SignInActivity extends Activity {

    protected static final String TAG = LoginActivity.class.getName();

    static ProgressDialog dlgCustom;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    private void login()
    {
        if (_emailText.getText().toString().isEmpty() || _passwordText.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter value User ID & password field", Toast.LENGTH_SHORT).show();
        } else
        {
            if(Util.isNetworkAvailable(getApplicationContext())) {
                new FetchSecuredResourceTask().execute("login");
            }else {
                Util.showAlertMsg(this, "Sorry, no internet connectivity detected. Please reconnect and try again.", "No Internet Connection");
            }
        }
    }

    private class FetchSecuredResourceTask extends AsyncTask<String, Void, Response>
    {
        String email;
        String password;

        Login login;
        Response response;
        ExceptionMessage exceptionMessage;

        @Override
        protected void onPreExecute()
        {
            //progressDialog(LoginActivity.this, "Please wait .....");

            this.response = new Response();
            this.response.setSuccessful(false);


            this.email = _emailText.getText().toString().trim();
            this.password = _passwordText.getText().toString().trim();

            this.login = new Login(email, password);
            login.setUserName(email);
            response.setRequestObject(this.login);
            exceptionMessage = new ExceptionMessage(false, "", "");
        }

        @Override
        protected Response doInBackground(String... params)
        {
            if(params[0] == "login")
            {
                //final String url = getString(R.string.uri) + "/flp/auth/trainerLogin";
                final String url = getString(R.string.base_url) + "/nlms/auth/login";

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                try {
                    // Make the network request
                    Log.d(TAG, url);
                    this.response = restTemplate.postForObject(url, login, Response.class);
                    //ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Message.class);
                    // return response.getBody();
                } catch (HttpClientErrorException e) {
                    Log.d(TAG, "HttpClientErrorException");
                    Log.e(TAG, e.getLocalizedMessage(), e);
                    this.response.setErrorMessage(e.getStatusText() + " - " + e.getLocalizedMessage());
                    exceptionMessage = new ExceptionMessage(true, "HttpClient Error Exception", e.getStatusText() + " - " + e.getLocalizedMessage());
                    //return new Message(0, e.getStatusText(), e.getLocalizedMessage());
                } catch (ResourceAccessException e) {
                    Log.d(TAG, "ResourceAccessException");
                    Log.e(TAG, e.getLocalizedMessage(), e);
                    this.response.setErrorMessage(e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
                    exceptionMessage = new ExceptionMessage(true, "Resource Access Exception", e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
                    //return new Message(0, e.getClass().getSimpleName(), e.getLocalizedMessage());
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            //dlgCustom.dismiss();
            //displayResponse(response);
            Log.i("response : ", String.valueOf(response));
            if(exceptionMessage.getStatus()){
                Util.showAlertDialg(SignInActivity.this, exceptionMessage.getMessage(), exceptionMessage.getTitle(),  true );
            }else {
                if (response.isSuccessful()) {
                    Log.i("responseObject : ", String.valueOf(response.getResponseObject()));
                    Map map = (Map) response.getResponseObject();
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    User user = gson.fromJson(jsonElement, User.class);
                    //User user = (User) response.getResponseObject();
                    /*Gson gson = new Gson();
                    Type type = new TypeToken<User>(){}.getType();
                    LinkedHashMap linkedHashMap = (LinkedHashMap) response.getResponseObject();
                    String json = gson.toJson(linkedHashMap, type);
                    User user = gson.fromJson(json, type);*/

                    SessionManager.setPreferences(SignInActivity.this, "logUser", user.getUserName());
                    SessionManager.setPreferences(SignInActivity.this, "mandal", user.getMandal());
                    String logUser = SessionManager.getPreferences(SignInActivity.this, "logUser");
                    if (logUser!=null && !logUser.isEmpty() && logUser.length() > 3) {
                        String mandal = SessionManager.getPreferences(getApplicationContext(), "mandal");
                        // Creating Bundle object
                        Bundle bundle = new Bundle();
                        bundle.putString("mandal", mandal);
                        bundle.putString("logUser", logUser);
                        reset();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "response Status - " + response.isSuccessful() + " - " + response.getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    public void reset()
    {
        _emailText.setText("");
        _passwordText.setText("");
    }

    /* This performs Progress dialog box to show the progress of operation */
    public static void progressDialog(Context context, String msg) {
        dlgCustom = new ProgressDialog(context);
        dlgCustom.setMessage(msg);
        dlgCustom.setIndeterminate(true);
        dlgCustom.setCancelable(false);
        dlgCustom.show();
    }
}
