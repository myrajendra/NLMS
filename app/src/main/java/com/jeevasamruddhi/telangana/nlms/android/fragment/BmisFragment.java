package com.jeevasamruddhi.telangana.nlms.android.fragment;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.adapter.BasicCardAdapter;
import com.jeevasamruddhi.telangana.nlms.android.adapter.MandalSpinnerAdapter;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.common.Verhoeff;
import com.jeevasamruddhi.telangana.nlms.android.customview.CustomEditText;
import com.jeevasamruddhi.telangana.nlms.android.customview.CustomTextView;
import com.jeevasamruddhi.telangana.nlms.android.model.BaseModel;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicCard;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.MessageDTO;
import com.jeevasamruddhi.telangana.nlms.android.model.MessageDTOFILTER;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class BmisFragment extends Fragment {

    protected static final String TAG = BmisFragment.class.getSimpleName();
    String responseText;
    StringBuffer response;
    Context context;
    View mview;
    static ProgressDialog dlgCustom;
    AppCompatActivity mActivity;

    ListView listView;
    CustomEditText editsearch;

    BasicCardAdapter adapter;
    BasicCard basicCard;
    List<BasicCard> cardlist;

    public BmisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_bmis, container, false);
        editsearch = (CustomEditText) mview.findViewById(R.id.search);
        mActivity = (AppCompatActivity)getActivity();
        listView = (ListView) mview.findViewById(R.id.bmisListView);
        CustomTextView filter =(CustomTextView)mview.findViewById(R.id.filter);
        filter.setText("Nalgonda");
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPicSelectDialog(getActivity(),view);
            }
        });
        context = getContext();
        bmis();
        return mview;
    }

    void bmis(){
        if(Util.isNetworkAvailable(getContext())) {
            new BmisAsyn().execute();
        }else {

            Toast.makeText(context, "Sorry, no internet connectivity detected. Please reconnect and try again.", Toast.LENGTH_SHORT).show();
            //Util.showAlertDialg(get, "Sorry, no internet connectivity detected. Please reconnect and try again.", "No Internet Connection", true);
        }
    }

    public void FilterOperation(String value) {
        if(Util.isNetworkAvailable(getContext())) {
            new BmisAsynOperation(value).execute();
        }else {

            Toast.makeText(context, "Sorry, no internet connectivity detected. Please reconnect and try again.", Toast.LENGTH_SHORT).show();
            //Util.showAlertDialg(get, "Sorry, no internet connectivity detected. Please reconnect and try again.", "No Internet Connection", true);
        }
    }

    private class BmisAsynOperation extends AsyncTask<String, Void, Response> {

        Response response;
        ExceptionMessage exceptionMessage;
        String value;
        private ProgressDialog dialog;
        public BmisAsynOperation(String value) {
            this.value = value;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(mActivity,R.style.AppCompatAlertDialogStyle);
            dialog.setMessage("Please wait...");
            dialog.show();
            //progressDialog(context, "Please wait ...");
            this.response = new Response();
            this.response.setSuccessful(false);
            exceptionMessage = new ExceptionMessage(false, "", "");
            super.onPreExecute();
        }

        @Override
        protected Response doInBackground(String... strings) {
            //final String url = getString(R.string.base_url) + "/nlms/basic/cards";
            //final String url ="http://34.230.40.212:8080/nlms/beneficiaries/NALGONDA/";
            final String url =Util.Ipaddress+"beneficiaries/NALGONDA/"+value;
            getWebServiceResponseData(url);
            // Create a new RestTemplate instance
            /*RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, url);
                String mandal = SessionManager.getPreferences(getContext(), "mandal");
                BasicCard basicCard = new BasicCard();
                basicCard.setMandal("Adavidevulapalli");
                this.response = restTemplate.getForObject(url, Response.class);

            } catch (HttpClientErrorException e) {
                Log.d(TAG, "HttpClientErrorException");
                Log.e(TAG, e.getLocalizedMessage(), e);
                this.response.setErrorMessage(e.getStatusText() + " - " + e.getLocalizedMessage());
                exceptionMessage = new ExceptionMessage(true, "HttpClient Error Exception", e.getStatusText() + " - " + e.getLocalizedMessage());
            } catch (ResourceAccessException e) {
                Log.d(TAG, "ResourceAccessException");
                Log.e(TAG, e.getLocalizedMessage(), e);
                this.response.setErrorMessage(e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
                exceptionMessage = new ExceptionMessage(true, "Resource Access Exception", e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
            }
*/
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (cardlist != null && cardlist.size() > 0) {
                adapter = new BasicCardAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cardlist);
                listView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "No Items", Toast.LENGTH_LONG).show();

            }
            // Capture Text in EditText
            editsearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(getContext(), (CharSequence) myObjects.get(position), Toast.LENGTH_SHORT).show();
                    basicCard = cardlist.get(position);
                    if (basicCard.getAadhaar() != null && !basicCard.getAadhaar().isEmpty() && Verhoeff.validateVerhoeff(basicCard.getAadhaar())) {
                        //new CaptureFingerAsyn().execute();
                        Toast.makeText(getContext(), basicCard.getAadhaar(), Toast.LENGTH_LONG).show();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("basicCard", (Serializable) basicCard);

                        Fragment fragment = new BenificiaryFragment();
                        fragment.setArguments(bundle);
                        //replacing the fragment
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                        }
                    } else {
                        Toast.makeText(getContext(), "update Aadhaar in Portal", Toast.LENGTH_LONG).show();
                    }

                }
            });



        }
    }
    private class BmisAsyn extends AsyncTask<String, Void, Response> {

        Response response;
        ExceptionMessage exceptionMessage;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            //progressDialog(context, "Please wait ...");
            dialog = new ProgressDialog(mActivity,R.style.AppCompatAlertDialogStyle);
            dialog.setMessage("Please wait...");
            dialog.show();
            this.response = new Response();
            this.response.setSuccessful(false);
            exceptionMessage = new ExceptionMessage(false, "", "");
            super.onPreExecute();
        }

        @Override
        protected Response doInBackground(String... strings) {
            //final String url = getString(R.string.base_url) + "/nlms/basic/cards";
            //final String url ="http://34.230.40.212:8080/nlms/beneficiaries/NALGONDA/";
            final String url =Util.Ipaddress+"beneficiaries";
            getWebServiceResponseData(url);
            // Create a new RestTemplate instance
            /*RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, url);
                String mandal = SessionManager.getPreferences(getContext(), "mandal");
                BasicCard basicCard = new BasicCard();
                basicCard.setMandal("Adavidevulapalli");
                this.response = restTemplate.getForObject(url, Response.class);

            } catch (HttpClientErrorException e) {
                Log.d(TAG, "HttpClientErrorException");
                Log.e(TAG, e.getLocalizedMessage(), e);
                this.response.setErrorMessage(e.getStatusText() + " - " + e.getLocalizedMessage());
                exceptionMessage = new ExceptionMessage(true, "HttpClient Error Exception", e.getStatusText() + " - " + e.getLocalizedMessage());
            } catch (ResourceAccessException e) {
                Log.d(TAG, "ResourceAccessException");
                Log.e(TAG, e.getLocalizedMessage(), e);
                this.response.setErrorMessage(e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
                exceptionMessage = new ExceptionMessage(true, "Resource Access Exception", e.getClass().getSimpleName() + " - " + e.getLocalizedMessage());
            }
*/
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (cardlist != null && cardlist.size() > 0) {
                adapter = new BasicCardAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cardlist);
                listView.setAdapter(adapter);
            }else{
                Toast.makeText(getContext(), "No Items", Toast.LENGTH_LONG).show();

            }
            // Capture Text in EditText
            editsearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(getContext(), (CharSequence) myObjects.get(position), Toast.LENGTH_SHORT).show();
                    basicCard = cardlist.get(position);
                    if (basicCard.getAadhaar() != null && !basicCard.getAadhaar().isEmpty() && Verhoeff.validateVerhoeff(basicCard.getAadhaar())) {
                        //new CaptureFingerAsyn().execute();
                        Toast.makeText(getContext(), basicCard.getAadhaar(), Toast.LENGTH_LONG).show();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("basicCard", (Serializable) basicCard);

                        Fragment fragment = new BenificiaryFragment();
                        fragment.setArguments(bundle);
                        //replacing the fragment
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                        }
                    } else {
                        Toast.makeText(getContext(), "update Aadhaar in Portal", Toast.LENGTH_LONG).show();
                    }

                }
            });



        }
    }

    protected Void getWebServiceResponseData(String path) {

        if(cardlist!=null){
            cardlist.clear();
        }
        try {

            URL url = new URL(path);
            Log.d(TAG, "ServerData: " + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            Log.d(TAG, "Response code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Reading response from input Stream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String output;
                response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
            }
            responseText = response.toString();
            Gson gsonObj = new Gson();

            if(responseText.contains("content")) {
                MessageDTO messageDTO;
                Type listTypeObj = new TypeToken<MessageDTO>() {
                }.getType();
                messageDTO = gsonObj.fromJson(responseText, listTypeObj);
                cardlist = messageDTO.getBasicCards();
            }else{
                MessageDTOFILTER messageDTOFILTER;
                Type listTypeObj = new TypeToken<MessageDTOFILTER>() {
                }.getType();
                messageDTOFILTER = gsonObj.fromJson(responseText, listTypeObj);
                cardlist = messageDTOFILTER.getBasicCards();
            }
            Log.d(TAG, "data:" + responseText);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return null;
    }
    private void showPicSelectDialog(final Context context, View anchor) {
        View view = LayoutInflater.from(context).inflate(R.layout.mandal_categ, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_recycler_view2);

        PopupWindow mProfilePicDialog = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mProfilePicDialog.setContentView(view);
        mProfilePicDialog.setTouchable(true);
        mProfilePicDialog.setOutsideTouchable(true);
        mProfilePicDialog.showAsDropDown(anchor);

        MandalSpinnerAdapter recyclerViewAdapter = new MandalSpinnerAdapter(mActivity, mProfilePicDialog,mview,BmisFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        mProfilePicDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim((ViewGroup) getActivity().getWindow().getDecorView().getRootView());
            }
        });
        applyDim((ViewGroup) getActivity().getWindow().getDecorView().getRootView(), 0.5f);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void applyDim(@NonNull ViewGroup parent, float dimAmount) {
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
}
