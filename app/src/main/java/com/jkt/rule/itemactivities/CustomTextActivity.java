package com.jkt.rule.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jkt.rule.R;
import com.jkt.trule.TRule;

public class CustomTextActivity extends AppCompatActivity implements TRule.OnRulerChangeListener {
    private TRule mTRL;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_text);
        //属性xml中定义,当然也可以set方法
        //设定下标文本以及中间文本(如果大刻度为偶数个且show_center_text属性为true,中间文本生效)
        mTRL = (TRule) findViewById(R.id.trule);
        mTV = ((TextView) findViewById(R.id.tv));
        mTRL.setOnRulerChangeListener(this);


    }

    @Override
    public void onRuleChanged(Object object,int position) {
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}

