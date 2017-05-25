package com.jkt.trule;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 天哥哥 at 2017/5/25 10:29
 */
public class TRule extends View {
    //刻度所占总的dimension
    private int TotalValue;
    //刻度超过屏幕横向中间标记线颜色
    private int scaleSelectColor;
    //刻度未超屏幕横向中间标记线颜色
    private int scaleUnSelectColor;
    //标尺开始位置
    private int currLocation = 60;
    //一屏显示Item个数
    private int showItemNumber;
    //一个刻度的dimension
    private int oneItemValue;
    private Paint paint;
    private Paint mPaintText;
    //视图高度
    private float viewHeight;
    //刻度宽度
    private int mViewWidth;
    //刻度宽度
    private int mScaleWidth;
    //刻度高度
    private float mScaleHeight;
    // 手势识别
    private GestureDetector gestureDetector;
    //滚动偏移量
    private int mScrollingOffset;
    private Context mContext;
    private OnRulerChangeListener onRulerChangeListener;
    private int mPos;
    private int mOnceTouchEventOffset;
    private float mMiddleLineHeight;


    public void setOnRulerChangeListener(OnRulerChangeListener onRulerChangeListener) {
        this.onRulerChangeListener = onRulerChangeListener;
    }

    public void setCurrLocation(int currLocation) {
        this.currLocation = currLocation;
    }

    public TRule(Context context) {
        this(context,null,0);
    }

    public TRule(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RulerView);
        mMiddleLineHeight = DensityUtil.dp2px(mContext, 40);
        mScaleHeight = DensityUtil.dp2px(mContext, 20);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        TotalValue = typedArray.getDimensionPixelOffset(R.styleable.RulerView_max_value, 120000);
        scaleSelectColor = typedArray.getColor(R.styleable.RulerView_scale_select_color, 0xff50f1bf);
        scaleUnSelectColor = typedArray.getColor(R.styleable.RulerView_scale_unselect_color, 0xaa86a7e8);
        if (currLocation == 0) {
            currLocation = typedArray.getDimensionPixelOffset(R.styleable.RulerView_start_location, 0);
        }
        showItemNumber = typedArray.getInteger(R.styleable.RulerView_show_item_size, 50);
        oneItemValue = typedArray.getInteger(R.styleable.RulerView_show_item_size, 1000);
        typedArray.recycle();
        //手势解析器
        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBottomLine(canvas);
        drawScale(canvas);
        drawMiddleLine(canvas);
    }


    private void drawBottomLine(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(scaleUnSelectColor);
        canvas.drawLine(0, viewHeight - 10, getWidth(), viewHeight - 10, paint);
    }

    //画中间的长线
    private void drawMiddleLine(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(0xff50f1bf);
        canvas.drawLine(mViewWidth / 2, viewHeight - 10 - mMiddleLineHeight, mViewWidth / 2, viewHeight - 10, paint);
    }

    private void drawScale(Canvas canvas) {
        mScaleWidth = mViewWidth / showItemNumber;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        //计算游标开始绘制的位置
        float startLocation = (mViewWidth / 2) - ((mScaleWidth * (currLocation / oneItemValue)));
        for (int i = 0; i <= TotalValue / oneItemValue; i++) {
            //判断当前刻度是否小于当前刻度
            if (i * oneItemValue == currLocation) {
                paint.setColor(scaleSelectColor);
            } else {
                paint.setColor(scaleUnSelectColor);
            }
            float location = startLocation + i * mScaleWidth;
            if (i % 10 == 0) {
                //大刻度
                canvas.drawLine(location, viewHeight - mScaleHeight - 10, location, viewHeight - 10, paint);
                String drawStr = null;
                if (i / 10 <= 5) {
                    drawStr = i / 10 + 1 + "个月";
                } else if (i / 10 == 6) {
                    drawStr = "全部";
                } else {
                    drawStr = i / 10 + "个月";
                }
                Rect bounds = new Rect();
                if (i * oneItemValue == currLocation) {
                    mPaintText.setColor(scaleSelectColor);
                    mPaintText.setTextSize(DensityUtil.dp2px(mContext, (float) 17.5));
                } else {
                    mPaintText.setColor(scaleUnSelectColor);
                    mPaintText.setTextSize(DensityUtil.dp2px(mContext, (float) 14.5));
                }
                mPaintText.getTextBounds(drawStr, 0, drawStr.length(), bounds);
                //添加刻度文字
                canvas.drawText(drawStr, location - bounds.width() / 2, 65, mPaintText);
            } else {
                //小刻度
                canvas.drawLine(location, viewHeight - mScaleHeight / 2 - 10, location, viewHeight - 10, paint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOnceTouchEventOffset = 0;
            case MotionEvent.ACTION_MOVE:
                gestureDetector.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                int round = 0;
                if (Math.abs(mOnceTouchEventOffset) > 100) {
                    round = Math.round(mPos / (float) 10);
                } else if (mOnceTouchEventOffset < 0) {
                    double ceil = Math.ceil(mPos / (float) 10);
                    round = (int) ceil;
                } else {
                    double floor = Math.floor(mPos / (float) 10);
                    round = (int) floor;
                }
                setCurrentItem(round * 10);
                if (onRulerChangeListener != null) {
                    onRulerChangeListener.onRuleChanged(str);
                }
        }
        return true;
    }

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            doScroll((int) -distanceX);
            invalidate();
            return true;
        }
    };

    private void doScroll(int delta) {
        //每次onTouch完成之后要考虑具体偏移设计,需要参照单次onTouch总的偏移量(手势的onScroll方法会多次执行,所以需要做累加).
        mOnceTouchEventOffset += delta;
        //偏移量叠加
        mScrollingOffset += delta;
        //总共滚动了多少个Item
        int mCount = mScrollingOffset / mScaleWidth;
        //当前刻度位置
        mPos = currLocation / oneItemValue - mCount;
        //限制滚到范围,小于0刻度或者超过最大刻度
        if (mPos < 0) {
            mPos = 0;
        } else if (mPos >= TotalValue / oneItemValue) {
            mPos = TotalValue / oneItemValue;
        }
        //移动了一个Item的距离，就更新页面
        if (mPos != currLocation / oneItemValue) {
            setCurrentItem(mPos);
        }
        // 重新更新一下偏移量(这点不懂可以来问我)
        mScrollingOffset = mScrollingOffset - mCount * mScaleWidth;
    }

    int str;

    public void setCurrentItem(int index) {
        currLocation = index * oneItemValue;
        invalidate();
        if (currLocation / 10000 <= 5) {
            str = currLocation / 10000 + 1;
        } else if (currLocation / 10000 == 6) {
            str = 0;
        } else {
            str = currLocation / 10000;
        }
        if (onRulerChangeListener != null) {
            onRulerChangeListener.onRuleChanged(str);
        }
    }

    public interface OnRulerChangeListener {
        void onRuleChanged(int num);
    }
}
