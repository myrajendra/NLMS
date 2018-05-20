package com.jeevasamruddhi.telangana.nlms.android.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.jeevasamruddhi.telangana.nlms.android.R;

/**
 * Created by jayalakshmi on 3/19/2018.
 */

public class BenificiaryDetails  extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiary_details);

    }
}
