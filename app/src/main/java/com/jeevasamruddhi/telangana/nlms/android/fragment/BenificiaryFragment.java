package com.jeevasamruddhi.telangana.nlms.android.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicBenificiary;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicCard;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class BenificiaryFragment extends Fragment {

    protected static final String TAG = BenificiaryFragment.class.getSimpleName();
    BasicCard basicCard;
    String responseText;
    StringBuffer response;
    Context context;
    static ProgressDialog dlgCustom;

    List<BasicBenificiary> cardlist;
    TextView basicBenificiaryName;
    TextView basicBenificiaryAadhaar;
    TextView basicBenificiaryFarmerId;
    TextView basicBenificiaryFatherOrHusbandName;
    TextView basicBenificiaryVillage;
    TextView basicBenificiaryMandal;
    TextView basicBenificiaryDistrict;
    TextView basicBenificiarySanctionOderId;
    TextView basicBenificiaryDepartment;
    TextView basicBenificiaryScheme;
    TextView basicBenificiaryUnitCost;
    TextView basicBenificiarySubsidy;
    TextView basicBenificiaryBenificiaryContrib;
    TextView basicBenificiaryYear;
    //TextView basicBenificiaryProceedingNo;
    TextView basicSiNoOfTheProceeding;
    TextView basicBenificiaryDateOfGrounding;
    TextView basicBenificiaryplaceOfGrounding;
    TextView basicBenificiarySellerName;
    TextView basicSellerFatherOrHusbandName;
    TextView basicBenificiarySellerAddress;
    TextView basicBenificiarySellerAadhar;
    TextView basicBenificiarySellerAccountNumber;
    TextView basicBenificiaryAmountPaid;
    TextView basicBenificiaryChequeNoIssuedByTheDvaho;
    TextView basicBenificiaryDate;

    public BenificiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_benificiary, container, false);
        basicCard = (BasicCard) getArguments().get("basicCard");
        context = getContext();

        basicBenificiaryAadhaar = (TextView) view.findViewById(R.id.basicBenificiaryAadhaar);
        basicBenificiaryName = (TextView) view.findViewById(R.id.basicBenificiaryName);
        basicBenificiaryFarmerId = (TextView) view.findViewById(R.id.basicBenificiaryFarmerId);
        basicBenificiaryFatherOrHusbandName = (TextView) view.findViewById(R.id.basicBenificiaryFatherOrHusbandName);
        basicBenificiaryVillage = (TextView) view.findViewById(R.id.basicBenificiaryVillage);
        basicBenificiaryMandal = (TextView) view.findViewById(R.id.basicBenificiaryMandal);
        basicBenificiaryDistrict = (TextView) view.findViewById(R.id.basicBenificiaryDistrict);
        basicBenificiarySanctionOderId = (TextView) view.findViewById(R.id.basicBenificiarySanctionOderId);
        basicBenificiaryDepartment = (TextView) view.findViewById(R.id.basicBenificiaryDepartment);
        basicBenificiaryScheme = (TextView) view.findViewById(R.id.basicBenificiaryScheme);
        basicBenificiaryUnitCost = (TextView) view.findViewById(R.id.basicBenificiaryUnitCost);
        basicBenificiarySubsidy = (TextView) view.findViewById(R.id.basicBenificiarySubsidy);
        basicBenificiaryBenificiaryContrib = (TextView) view.findViewById(R.id.basicBenificiaryBenificiaryContrib);
        basicBenificiaryYear = (TextView) view.findViewById(R.id.basicBenificiaryYear);
       // basicBenificiaryProceedingNo = (TextView) view.findViewById(R.id.basicBenificiaryProceedingNo);
        basicSiNoOfTheProceeding = (TextView) view.findViewById(R.id.basicSiNoOfTheProceeding);
        basicBenificiaryDateOfGrounding = (TextView) view.findViewById(R.id.basicBenificiaryDateOfGrounding);
        basicBenificiaryplaceOfGrounding = (TextView) view.findViewById(R.id.basicBenificiaryplaceOfGrounding);
        basicBenificiarySellerName = (TextView) view.findViewById(R.id.basicBenificiarySellerName);
        basicSellerFatherOrHusbandName = (TextView) view.findViewById(R.id.basicSellerFatherOrHusbandName);
        basicBenificiarySellerAddress = (TextView) view.findViewById(R.id.basicBenificiarySellerAddress);
        basicBenificiarySellerAadhar = (TextView) view.findViewById(R.id.basicBenificiarySellerAadhar);
        basicBenificiarySellerAccountNumber = (TextView) view.findViewById(R.id.basicBenificiarySellerAccountNumber);
        basicBenificiaryAmountPaid = (TextView) view.findViewById(R.id.basicBenificiaryAmountPaid);
        basicBenificiaryChequeNoIssuedByTheDvaho = (TextView) view.findViewById(R.id.basicBenificiaryChequeNoIssuedByTheDvaho);
        basicBenificiaryDate = (TextView) view.findViewById(R.id.basicBenificiaryDate);

        if(Util.isNetworkAvailable(context)) {
            new BenificiaryAsyn().execute();
        }else {
            Toast.makeText(context, "Sorry, no internet connectivity detected. Please reconnect and try again.", Toast.LENGTH_SHORT).show();
            //Util.showAlertDialg(this, "Sorry, no internet connectivity detected. Please reconnect and try again.", "No Internet Connection", true);
        }

        return view;
    }

    /* This performs Progress dialog box to show the progress of operation */
    public static void progressDialog(Context context, String msg) {
        dlgCustom = new ProgressDialog(context);
        dlgCustom.setMessage(msg);
        dlgCustom.setIndeterminate(true);
        dlgCustom.setCancelable(false);
        dlgCustom.show();
    }

    private class BenificiaryAsyn extends AsyncTask<String, Void, Response> {

        Response response;
        ExceptionMessage exceptionMessage;

        @Override
        protected void onPreExecute() {
            progressDialog(context, "Please wait ...");
            this.response = new Response();
            this.response.setSuccessful(false);
            exceptionMessage = new ExceptionMessage(false, "", "");
            super.onPreExecute();
        }


        @Override
        protected Response doInBackground(String... strings) {
            getWebServiceResponseData(Util.Ipaddress+"beneficiaries/aadhar?aadharNo="+basicCard.getAadhaar());
         /*   final String url = getString(R.string.base_url) + "/nlms/basic/benificiary";
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, url);

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
            }catch (Exception e) {

            }*/

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {

          if(cardlist !=null && cardlist.size()>0) {
              for(int i =0; i < cardlist.size();i++) {
                  basicBenificiaryName.setText("Name: " + cardlist.get(i).getName());
                  basicBenificiaryAadhaar.setText("Aadhaar: " + cardlist.get(i).getAadhaar());
                  //basicBenificiaryName.setText(basicBenificiary);
                  basicBenificiaryFarmerId.setText("Farmer ID: -NA-" );
                  Log.i("FatherOrHusbandName : ", String.valueOf(cardlist.get(i).getFather()));
                  basicBenificiaryFatherOrHusbandName.setText("Father/Husband Name: " +cardlist.get(i).getFather());
                  basicBenificiaryVillage.setText("Village: " + cardlist.get(i).getAdress());
                  basicBenificiaryMandal.setText("Mandal: " + cardlist.get(i).getMandal());
                  basicBenificiaryDistrict.setText("District: " + cardlist.get(i).getDistrict());
                  basicBenificiarySanctionOderId.setText("Sanction Order ID: -NA-");
                  basicBenificiaryDepartment.setText("Department name : Veterinary and Animal Husbandry." );
                  basicBenificiaryScheme.setText("Scheme:SRDP(Sheep Rear Development Project )" );
                  basicBenificiaryUnitCost.setText("Unit Cost: 1,25,000" );
                  basicBenificiarySubsidy.setText("Subsidy: 93750" );
                  basicBenificiaryBenificiaryContrib.setText("Benificiary contribution : 31250" );
                  basicBenificiaryYear.setText("Year: 2017-2018" );
                 // basicBenificiaryProceedingNo.setText("Proceeding No: -NA-" );
                  basicSiNoOfTheProceeding.setText("No in The Proceeding : -NA- " );
                  basicBenificiaryDateOfGrounding.setText("Date Of Grounding: -NA-" );
                  basicBenificiaryplaceOfGrounding.setText("Place Of Grounding:  -NA-" );
                  basicBenificiarySellerName.setText("Seller Name: -NA-" );
                  basicSellerFatherOrHusbandName.setText("Seller Father/Husband Name: -NA-" );
                  basicBenificiarySellerAddress.setText("Seller Address: -NA-" );
                  basicBenificiarySellerAadhar.setText("Seller Aadhaar: -NA-" );
                  basicBenificiarySellerAccountNumber.setText("Seller Account Number: -NA-" );
                  basicBenificiaryAmountPaid.setText("Amount Paid: -NA-" );
                  basicBenificiaryChequeNoIssuedByTheDvaho.setText("Cheque No Issued By The DVAHO: -NA-" );
                  basicBenificiaryDate.setText("Date: -NA-" );
              }

          }
                dlgCustom.dismiss();

        }
    }
    protected Void getWebServiceResponseData(String path) {

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
            }}
        catch(Exception e){
            e.printStackTrace();
        }

        responseText = response.toString();
        //Call ServerData() method to call webservice and store result in response
        //  response = service.ServerData(path, postDataParams);
        Log.d(TAG, "data:" + responseText);
        try {
            JSONObject jsonObj = new JSONObject(responseText);

            // Getting JSON Array node
            JSONObject jsonValue = jsonObj.getJSONObject("message");
            cardlist = new ArrayList<>();
            if(jsonValue!=null ){

                    BasicBenificiary card= new BasicBenificiary();
                    card.setAadhaar(jsonValue.getString("aadharNo"));
                    card.setName(jsonValue.getString("name"));
                    card.setFather(jsonValue.getString("fatherOrHusbandName"));
                    card.setMobileNo(jsonValue.getString("mobileNo"));
                    card.setAdress(jsonValue.getString("address"));
                    card.setGender(jsonValue.getString("gender"));
                    card.setCaste(jsonValue.getString("caste"));
                    card.setIncome(jsonValue.getString("income"));
                    card.setdisability(jsonValue.getString("disability"));
                    card.setbankname(jsonValue.getString("bankName"));
                    card.setifscode(jsonValue.getString("ifscCode"));
                    card.setaccountNo(jsonValue.getString("accountNo"));
                    card.setappiledDate(jsonValue.getString("appliedDate"));
                    card.setMandal(jsonValue.getString("mandalName"));
                    card.setDistrict(jsonValue.getString("districtName"));
                    cardlist.add(card);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
