package com.jkt.rule;

import com.jkt.rule.itemactivities.CustomIndexActivity;
import com.jkt.rule.itemactivities.StandardActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    //直接查看对应Activity即可,各种属性含义见attrs
    @Override
    public List<TypeBean> getList() {
        return getData();
    }

    private List<TypeBean> getData() {
        List<TypeBean> list = new ArrayList<>();
        list.add(new TypeBean("标准样式(StandardActivity.class)", StandardActivity.class));
        list.add(new TypeBean("没有时钟点(NoPointActivity.class)", CustomIndexActivity.class));
        return list;
    }

}
