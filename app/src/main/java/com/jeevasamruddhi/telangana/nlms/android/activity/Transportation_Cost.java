package com.jeevasamruddhi.telangana.nlms.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.customview.CustomButton;

/**
 * Created by jayalakshmi on 3/20/2018.
 */

public class Transportation_Cost extends AppCompatActivity {
    CustomButton continue_button;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportation_cost);
        continue_button = (CustomButton)findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transportation_Cost.this,Medicines.class));
            }
        });
    }
}
