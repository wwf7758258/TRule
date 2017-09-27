# TRule
###  Hello,这是一个刻度尺自定义View
  支持大刻度、小刻度数目自定义,支持手势滑动,支持事件监听回调.</br>
  另外该控件支持多达25+种属性的设置,你可以设置项目需要的样式.</br>
###  特点
   用户滑动完毕,自动选择大刻度位置(滑动过程可以滑动到小刻度,但Up时候会四舍五入选择大刻度标记,小刻度无意义)</br>
   (如果用户滑动不足一个大刻度,那么根据意图,默认+-1,而非四舍五入)
### 主要功能扩展方面
  支持设定下标起始位置、支持设定选中位置、中间标记的有无、文本自定义   
###  预览图
  <img width="350"  src="https://github.com/HoldMyOwn/TRule/blob/master/preview/all.gif"/><br>
  大概演示,详见各个Activity</br>
  
## Attributes属性（xml中指定）
 |属性|格式|描述
 |---|---|---|
 |set_location|integer|标尺开始显示位置(以小刻度为单位)
 |big_scale_num|integer|大刻度数目
 |small_scale_num|integer|小刻度数目
 |small_scale_space|dimension|小刻度间隔宽度
 |middle_line_height|dimension|中间线高度
 |middle_line_width|dimension|中间线宽度
 |big_scale_height|dimension|大刻度高度
 |big_scale_width|dimension|大刻度宽度
 |small_scale_height|dimension|小刻度高度
 |small_scale_width|dimension|小刻度宽度
 |middle_line_color|color|中间线颜色
 |big_scale_color|color|大刻度颜色
 |small_scale_color|color|小刻度颜色
 |text_size|dimension|刻度字体大小
 |text_size_choose|dimension|刻度字体选中时候大小
 |text_color|color|刻度字体颜色
 |text_color_choose|color|刻度字体选中时候颜色
 |bottom_color|color|底部线颜色
 |bottom_line_height|dimension|底部线高度
 |bottom_line_to_view_bottom|dimension|底部线距离控件底部距离
 |text_bottom_to_line_top|dimension|文本底部到中间线顶部的距离
 |sensitiveness|float|灵敏度(以倍数为记,默认为1,类型为float)
 |index_text|string|各个下标对应文本(如1月,2月则string为"月",如1天、2天则为"天",前缀Index自动添加)
 |center_text|string|中间标记文本(只有大刻度数目为偶数个时候生效)
 |show_center_text|boolean|主动控制中间标记是否显示
 |index_start|integer|下标索引开始位置,默认为零
 
###  事件监听
<pre>   
    //动画尺子位置状态实时改变
    //回调只在Up事件触发的时候才有
    @Override
    public void onRuleChanged(Object object,int position) {
        //可以通过object区分是哪个TRule的回调
        //position参数即为当前位置(起始值由index_start属性设定,默认为1)
        //如果显示中间文本,那么中间位置的position回调为-1
        mTV.setText(position + "");
        Log.i("onRuleChanged", position + "------------");
    }
</pre>    
###   具体细节用法,下载查看Demo</br>
###   模板依赖:&nbsp;&nbsp;项目里面的TRule模板(可更加灵活扩展)</br>
###   gradle依赖:&nbsp;&nbsp;&nbsp;compile&nbsp;'com.jkt:trule:1.0.5'</br>
