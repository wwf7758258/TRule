package com.jkt.rule.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jkt.rule.R;
import com.jkt.trule.TRule;

public class CustomSizeActivity extends AppCompatActivity implements TRule.OnRulerChangeListener {

    private TRule mTRL;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_size);
        mTRL = (TRule) findViewById(R.id.main_TRule);
        mTRL.setOnRulerChangeListener(this);
        mTV = ((TextView) findViewById(R.id.tv));


    }

    @Override
    public void onRuleChanged(int position) {
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}

