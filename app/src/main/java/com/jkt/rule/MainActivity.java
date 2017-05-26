package com.jkt.rule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jkt.trule.TRule;

public class MainActivity extends AppCompatActivity {

    private TRule mTRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTRL = (TRule) findViewById(R.id.main_TRule);
        mTRL.setCurrentItem(30);

    }
}
