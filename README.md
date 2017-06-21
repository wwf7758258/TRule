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
  
###  样式
 
 <div>
 <img width="500"  src="https://github.com/HoldMyOwn/TRule/blob/master/preview/a.png"/>
  <img width="500"  src="https://github.com/HoldMyOwn/TRule/blob/master/preview/b.png"/>
 </div>
 
 大、小刻度数目、颜色、高、宽</br>
 小刻度间隔大小、底部线到控件底部距离、文本底部到中间线顶部距离</br>
 是否显示中间文本、手势感应灵敏度等各种样式</br>
 详见项目attrs文件</br>
 
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
