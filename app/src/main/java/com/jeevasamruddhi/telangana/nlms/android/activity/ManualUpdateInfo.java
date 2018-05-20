package com.jeevasamruddhi.telangana.nlms.android.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.common.Util;
import com.jeevasamruddhi.telangana.nlms.android.customview.CustomButton;
import com.jeevasamruddhi.telangana.nlms.android.customview.CustomEditText;
import com.jeevasamruddhi.telangana.nlms.android.model.ExceptionMessage;
import com.jeevasamruddhi.telangana.nlms.android.model.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ManualUpdateInfo extends AppCompatActivity {
    CustomEditText adhaar_edit,dofgrounding_edit,place_grounding_edit,seller_name,seller_addres,seller_addhar,bank_account,bank_name,ifsc_number,amount_paid,dateofpayment;
    boolean validation = false;
    CustomButton Submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_form);
        adhaar_edit = (CustomEditText)findViewById(R.id.edit_addhar);
        dofgrounding_edit = (CustomEditText)findViewById(R.id.edit_date_of_grounding);
        place_grounding_edit = (CustomEditText)findViewById(R.id.edit_place_of_grounding);
        seller_name = (CustomEditText)findViewById(R.id.edit_sellername);
        seller_addres = (CustomEditText)findViewById(R.id.edit_seller_address);
        seller_addhar = (CustomEditText)findViewById(R.id.edit_seller_addhar_no);
        bank_name = (CustomEditText)findViewById(R.id.edit_bank_name);
        bank_account = (CustomEditText)findViewById(R.id.edit_account_num);
        ifsc_number= (CustomEditText)findViewById(R.id.edit_ifcs);
        amount_paid= (CustomEditText)findViewById(R.id.edit_account_pay);
        dateofpayment= (CustomEditText)findViewById(R.id.edit_payment_date);


        Submit = (CustomButton)findViewById(R.id.submit_button);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(adhaar_edit.getText().toString())&&!TextUtils.isEmpty(dofgrounding_edit.getText().toString())&&!TextUtils.isEmpty(place_grounding_edit.getText().toString())
                        &&!TextUtils.isEmpty(seller_name.getText().toString())&&!TextUtils.isEmpty(seller_addres.getText().toString())&&!TextUtils.isEmpty(seller_addhar.getText().toString())&&
                        !TextUtils.isEmpty(bank_name.getText().toString())&&!TextUtils.isEmpty(bank_account.getText().toString())&&!TextUtils.isEmpty(ifsc_number.getText().toString())&&
                        !TextUtils.isEmpty(amount_paid.getText().toString())&&!TextUtils.isEmpty(dateofpayment.getText().toString())){
                    validation =true;
                }
                if(validation){
                   // Servicecall("http://34.230.40.212:8080/nlms/grounding");

                    new UpdateAsync().execute();

                }else{
                    Toast.makeText(ManualUpdateInfo.this,"Missing Some Feilds",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private class UpdateAsync extends AsyncTask<String, Void, Response> {

        Response response;
        String finalresult;
        ExceptionMessage exceptionMessage;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            //progressDialog(context, "Please wait ...");
            dialog = new ProgressDialog(ManualUpdateInfo.this, R.style.AppCompatAlertDialogStyle);
            dialog.setMessage("Please wait...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Response doInBackground(String... strings) {
            //final String url = getString(R.string.base_url) + "/nlms/basic/cards";
            //final String url ="http://34.230.40.212:8080/nlms/beneficiaries/NALGONDA/";
            finalresult = Servicecall(Util.Ipaddress+"grounding");
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(ManualUpdateInfo.this,finalresult,Toast.LENGTH_LONG).show();
            if(finalresult.contains("id")){
                clearedit();
            }
        }
    }

    private void clearedit() {
        adhaar_edit.setText("");
        dofgrounding_edit.setText("");
        place_grounding_edit.setText("");
        seller_name.setText("");
        seller_addres.setText("");
        seller_addhar.setText("");
        bank_name.setText("");
        bank_account.setText("");
        ifsc_number.setText("");
        amount_paid.setText("");
        dateofpayment.setText("");
    }


    public String Servicecall(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("baadhaar",adhaar_edit.getText().toString());
            jsonObject.accumulate("dateOfGrounding",dofgrounding_edit.getText().toString());
            jsonObject.accumulate("place",place_grounding_edit.getText().toString());
            jsonObject.accumulate("sellerName",seller_name.getText().toString());
            jsonObject.accumulate("sellerAddress",seller_addres.getText().toString());
            jsonObject.accumulate("saadhaar",seller_addhar.getText().toString());
            jsonObject.accumulate("bankAccount",bank_account.getText().toString());
            jsonObject.accumulate("bankName",bank_name.getText().toString());
            jsonObject.accumulate("ifsc",ifsc_number.getText().toString());
            jsonObject.accumulate("amountPaid",amount_paid.getText().toString());
            jsonObject.accumulate("dateOfPayment",dateofpayment.getText().toString());



            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private  String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
