package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.imranaliafzal.seattlemission.seattlemissionapp.R;


public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView mQueryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mQueryView = findViewById(R.id.et_query);

        Button mSearchButton = findViewById(R.id.bt_search);
        mSearchButton.setOnClickListener(view -> {
            if (mQueryView.getText().length() > 0) {
                Intent i = MainActivity.newIntent(
                        this, mQueryView.getText().toString());
                startActivity(i);
            }
        });


    }

}

