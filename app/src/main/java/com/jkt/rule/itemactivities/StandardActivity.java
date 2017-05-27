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
        //当大刻度为偶数个时候,默认添加中间文本,在大刻度中央(奇数个没有中央文本显示)
        //对应下标回调为-1,如果不想使其显示,设置下面属性,同样可以xml设置
//        mTRL.setShowCentText(false);

        mTV = ((TextView) findViewById(R.id.tv));
        mTRL.setOnRulerChangeListener(this);


    }

    //动画尺子位置状态实时改变
    //回调只在Up事件触发的时候才有
    //position参数即为当前位置(起始值由index_start属性设定,默认为0)
    @Override
    public void onRuleChanged(Object object,int position) {
        //可以通过object区分是哪个TRule的回调
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
}
