package com.jkt.rule.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jkt.rule.R;
import com.jkt.trule.TRule;

public class CustomIndexActivity extends AppCompatActivity implements TRule.OnRulerChangeListener {
    private TRule mTRL;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_index);
        //属性xml中定义,当然也可以set方法
        //可以设定刻度尺的起始下标,对应回调也按指定下标回调
        mTRL = (TRule) findViewById(R.id.trule);
        mTRL.setOnRulerChangeListener(this);
        mTV = ((TextView) findViewById(R.id.tv));


    }

    @Override
    public void onRuleChanged(int position) {
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}
