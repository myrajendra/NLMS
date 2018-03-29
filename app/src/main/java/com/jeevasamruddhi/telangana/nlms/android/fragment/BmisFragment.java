package com.jeevasamruddhi.telangana.nlms.android.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.adapter.BasicCardAdapter;
import com.jeevasamruddhi.telangana.nlms.android.common.SessionManager;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.common.Verhoeff;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicCard;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;

import org.json.JSONArray;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class BmisFragment extends Fragment {

    protected static final String TAG = BmisFragment.class.getSimpleName();

    Context context;
    static ProgressDialog dlgCustom;

    ListView listView;
    EditText editsearch;

    BasicCardAdapter adapter;
    BasicCard basicCard;

    public BmisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmis, container, false);
        editsearch = (EditText) view.findViewById(R.id.search);
        listView = (ListView) view.findViewById(R.id.bmisListView);
        context = getContext();
        bmis();
        return view;
    }

    void bmis(){
        if(Util.isNetworkAvailable(getContext())) {
            new BmisAsyn().execute();
        }else {

            Toast.makeText(context, "Sorry, no internet connectivity detected. Please reconnect and try again.", Toast.LENGTH_SHORT).show();
            //Util.showAlertDialg(get, "Sorry, no internet connectivity detected. Please reconnect and try again.", "No Internet Connection", true);
        }
    }

    private class BmisAsyn extends AsyncTask<String, Void, Response> {

        Response response;
        ExceptionMessage exceptionMessage;

        @Override
        protected void onPreExecute() {
            //progressDialog(context, "Please wait ...");
            this.response = new Response();
            this.response.setSuccessful(false);
            exceptionMessage = new ExceptionMessage(false, "", "");
            super.onPreExecute();
        }

        @Override
        protected Response doInBackground(String... strings) {
            final String url = getString(R.string.base_url) + "/nlms/basic/cards";
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, url);
                String mandal = SessionManager.getPreferences(getContext(), "mandal");
                BasicCard basicCard = new BasicCard();
                basicCard.setMandal(mandal);
                this.response = restTemplate.postForObject(url, basicCard, Response.class);
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

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            Log.i("response : ", String.valueOf(response));
            if (response.isSuccessful()) {
                Log.i("responseObject : ", String.valueOf(response.getResponseObject()));
                final List<BasicCard> basicCards = (List<BasicCard>) response.getResponseObject();
                Log.i("basicCards : ", String.valueOf(basicCards));

                JSONArray jsonArray = new JSONArray(basicCards);
                ObjectMapper mapper = new ObjectMapper();
                //List<Employee> myObjects = mapper.readValue(jsonArray, Employee);
                try {
                    final List<BasicCard> myObjects = mapper.readValue(jsonArray.toString(),new TypeReference<List<BasicCard>>(){});
                    adapter = new BasicCardAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, myObjects);
                    listView.setAdapter(adapter);

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

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
                            //Toast.makeText(getContext(), (CharSequence) myObjects.get(position), Toast.LENGTH_SHORT).show();
                            Log.i("employee : ", String.valueOf(myObjects.get(position)));
                            basicCard = myObjects.get(position);
                            if(basicCard.getAadhaar()!= null && !basicCard.getAadhaar().isEmpty() && Verhoeff.validateVerhoeff(basicCard.getAadhaar()))
                            {
                                //new CaptureFingerAsyn().execute();
                                Toast.makeText(getContext(), basicCard.getAadhaar(), Toast.LENGTH_LONG).show();

                                Bundle bundle=new Bundle();
                                bundle.putSerializable("basicCard", (Serializable) basicCard);

                                Fragment fragment = new BenificiaryFragment();
                                fragment.setArguments(bundle);
                                //replacing the fragment
                                if (fragment != null) {
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.content_frame, fragment);
                                    ft.commit();
                                }
                            }else {
                                Toast.makeText(getContext(), "update Aadhaar in Portal", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }else {
                //dlgCustom.dismiss();
                Toast.makeText(getContext(), "response Status - " + response.isSuccessful() + " - " + response.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
