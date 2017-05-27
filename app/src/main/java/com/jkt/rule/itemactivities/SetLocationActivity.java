package com.jkt.rule.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jkt.rule.R;
import com.jkt.trule.TRule;

public class SetLocationActivity extends AppCompatActivity implements TRule.OnRulerChangeListener {
    private TRule mTRL;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        mTRL = (TRule) findViewById(R.id.trule);
        mTV = ((TextView) findViewById(R.id.tv));
        mTRL.setOnRulerChangeListener(this);
        //属性在xml中定义,当然也可以set方法
        //设置位置,数目以小刻度为单位(第几个小刻度,与index无关)
//        mTRL.setLocation(60);


    }

    @Override
    public void onRuleChanged(Object object,int position) {
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}

