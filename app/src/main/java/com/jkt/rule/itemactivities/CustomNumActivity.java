package com.jkt.rule.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jkt.rule.R;
import com.jkt.trule.TRule;

public class CustomNumActivity extends AppCompatActivity implements TRule.OnRulerChangeListener {
    private TRule mTRL;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_num);
        //属性在xml中定义,当然也可以set方法
        //设置大刻度小刻度的数量
        mTRL = (TRule) findViewById(R.id.trule);
        mTV = ((TextView) findViewById(R.id.tv));
        mTRL.setOnRulerChangeListener(this);


    }

    @Override
    public void onRuleChanged(int position) {
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}

