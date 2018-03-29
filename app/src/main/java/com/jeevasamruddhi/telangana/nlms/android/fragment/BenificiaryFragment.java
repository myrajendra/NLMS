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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicBenificiary;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicCard;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;
import com.jeevasamruddhi.telangana.nlms.android.model.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BenificiaryFragment extends Fragment {

    protected static final String TAG = BenificiaryFragment.class.getSimpleName();

    static ProgressDialog dlgCustom;
    BasicCard basicCard;
    Context context;

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
    TextView basicBenificiaryProceedingNo;
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
        basicBenificiaryProceedingNo = (TextView) view.findViewById(R.id.basicBenificiaryProceedingNo);
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
            final String url = getString(R.string.base_url) + "/nlms/basic/benificiary";
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

            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {

            Log.i("response : ", String.valueOf(response));
            if (response.isSuccessful()) {
                Log.i("responseObject : ", String.valueOf(response.getResponseObject()));
                Map map = (Map) response.getResponseObject();
                Log.i("map : ", String.valueOf(map));
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(map);
                BasicBenificiary basicBenificiary = gson.fromJson(jsonElement, BasicBenificiary.class);

                basicBenificiaryName.setText("Name: " + basicBenificiary.getName());
                basicBenificiaryAadhaar.setText("Aadhaar: " + basicBenificiary.getAadhaar());
                //basicBenificiaryName.setText(basicBenificiary);
                basicBenificiaryFarmerId.setText("Farmer ID: " + basicBenificiary.getFarmerId());
                Log.i("FatherOrHusbandName : ", String.valueOf(basicBenificiary.getFatherOrHusbandName()));
                basicBenificiaryFatherOrHusbandName.setText("Father/Husband Name: " + basicBenificiary.getFatherOrHusbandName());
                basicBenificiaryVillage.setText("Village: " + basicBenificiary.getVillage());
                basicBenificiaryMandal.setText("Mandal: " + basicBenificiary.getMandal());
                basicBenificiaryDistrict.setText("District: " + basicBenificiary.getDistrict());
                basicBenificiarySanctionOderId.setText("Sanction Oder ID: " + basicBenificiary.getSanctionOderId());
                basicBenificiaryDepartment.setText("Departmaent: " + basicBenificiary.getDepartment());
                basicBenificiaryScheme.setText("Scheme: " + basicBenificiary.getSchemeName());
                basicBenificiaryUnitCost.setText("Unit Cost: " + basicBenificiary.getUnitCost());
                basicBenificiarySubsidy.setText("Subsidy: " + basicBenificiary.getSubsidy());
                basicBenificiaryBenificiaryContrib.setText("Benificiary Contrib: " + basicBenificiary.getBenificiaryContrib());
                basicBenificiaryYear.setText("Year: " + basicBenificiary.getYear());
                basicBenificiaryProceedingNo.setText("Proceeding No: " + basicBenificiary.getProceedingNo());
                basicSiNoOfTheProceeding.setText("No Of The Proceeding: " + basicBenificiary.getSiNoOfTheProceeding());
                basicBenificiaryDateOfGrounding.setText("Date Of Grounding: " + basicBenificiary.getDateOfGrounding());
                basicBenificiaryplaceOfGrounding.setText("Place Of Grounding: " + basicBenificiary.getPlaceOfGrounding());
                basicBenificiarySellerName.setText("Seller Name: " + basicBenificiary.getSellerName());
                basicSellerFatherOrHusbandName.setText("Seller Father/Husband Name: " + basicBenificiary.getSellerFatherOrHusbandName());
                basicBenificiarySellerAddress.setText("Seller Address: " + basicBenificiary.getSellerAddress());
                basicBenificiarySellerAadhar.setText("Seller Aadhaar: " + basicBenificiary.getSellerAadhar());
                basicBenificiarySellerAccountNumber.setText("Seller Account Number:" + basicBenificiary.getSellerAccountNumber());
                basicBenificiaryAmountPaid.setText("Amount Paid:" + basicBenificiary.getAmountPaid());
                basicBenificiaryChequeNoIssuedByTheDvaho.setText("Cheque No Issued By The DVAHO: " + basicBenificiary.getChequeNoIssuedByTheDvaho());
                basicBenificiaryDate.setText("Date: " + basicBenificiary.getDate());


                dlgCustom.dismiss();
            }else {
                dlgCustom.dismiss();
                Toast.makeText(getContext(), "response Status - " + response.isSuccessful() + " - " + response.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
