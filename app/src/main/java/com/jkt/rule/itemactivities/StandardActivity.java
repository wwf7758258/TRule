package com.jkt.rule.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jkt.rule.R;
import com.jkt.trule.TRule;

public class StandardActivity extends AppCompatActivity implements TRule.OnRulerChangeListener {
    private TRule mTRL;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        mTRL = (TRule) findViewById(R.id.trule);
        mTV = ((TextView) findViewById(R.id.tv));
        mTRL.setOnRulerChangeListener(this);


    }
    //动画尺子位置状态实时改变
    //回调只在Up事件触发的时候才有
    //position参数即为当前位置(起始值由index_start属性设定,默认为0)
    @Override
    public void onRuleChanged(int position) {
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}
