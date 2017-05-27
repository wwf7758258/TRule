package com.jkt.rule;

import com.jkt.rule.itemactivities.CustomColorActivity;
import com.jkt.rule.itemactivities.IndexActivity;
import com.jkt.rule.itemactivities.CustomNumActivity;
import com.jkt.rule.itemactivities.CustomSenSpaceActivity;
import com.jkt.rule.itemactivities.CustomSizeActivity;
import com.jkt.rule.itemactivities.CustomTextActivity;
import com.jkt.rule.itemactivities.SetLocationActivity;
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
        list.add(new TypeBean("下标起始(CustomIndexActivity.class)", IndexActivity.class));
        list.add(new TypeBean("设置选中位置(SetLocationActivity.class)", SetLocationActivity.class));
        list.add(new TypeBean("文本类型(CustomTextActivity.class)", CustomTextActivity.class));
        list.add(new TypeBean("大小刻度数目(CustomNumActivity.class)", CustomNumActivity.class));
        list.add(new TypeBean("灵敏度和小刻度间隔宽(CustomNumActivity.class)", CustomSenSpaceActivity.class));
        list.add(new TypeBean("颜色(CustomColorActivity.class)", CustomColorActivity.class));
        list.add(new TypeBean("尺寸(CustomSizeActivity.class)", CustomSizeActivity.class));
        return list;
    }

}
