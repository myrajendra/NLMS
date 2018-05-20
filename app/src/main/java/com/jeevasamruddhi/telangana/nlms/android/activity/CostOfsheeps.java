package com.jeevasamruddhi.telangana.nlms.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.customview.CustomButton;

/**
 * Created by jayalakshmi on 3/20/2018.
 */

public class CostOfsheeps extends AppCompatActivity {
    CustomButton mcontinue ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_of_sheep);

        mcontinue = (CustomButton)findViewById(R.id.continue_button);

        mcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CostOfsheeps.this,Transportation_Cost.class));
            }
        });
    }
}
