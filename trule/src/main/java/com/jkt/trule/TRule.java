package com.jkt.trule;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 天哥哥 at 2017/5/25 10:29
 */
public class TRule extends View {
    private Paint mTextPoint;
    private int TotalValue;
    //刻度超过屏幕横向中间标记线颜色
    private int mTextColor;
    //刻度未超屏幕横向中间标记线颜色
    private int mTextColorChoose;
    //标尺开始位置
    private int mCurrentIndex = 0;

    //视图高度
    private float mHeight;
    //刻度宽度
    private int mWidth;
    //刻度高度
    private float mSmallScaleHeight;
    // 手势识别
    private GestureDetector gestureDetector;
    //滚动偏移量
    private int mScrollingOffset;
    private Context mContext;
    private OnRulerChangeListener onRulerChangeListener;
    private int mPos;
    private int mOnceTouchEventOffset;
    private float mMiddleLineHeight;
    private int mBigScaleNum;
    private int mSmallScaleNum;
    private int mSmallScaleSpace;
    private int mInitLocation;
    private float mBigScaleHeight;
    private float mTextSize;
    private float mTextSizeChoose;
    private int mBottomLineColor;
    private float mBottomLineHeight;
    private float mMiddleLineWidth;
    private float mBigScaleWidth;
    private float mSmallScaleWidth;
    private int mMiddleLineColor;
    private int mBigScaleColor;
    private int msmallScaleColor;
    private Paint mBottomPaint;
    private Paint mMiddlePaint;
    private Paint mBigScalePaint;
    private Paint mSmallScalePaint;
    private float mToBottomHeight;
    private float mToLineTop;
    private float mSensitiveness;
    private String mCentText;
    private boolean mShowCentText;
    private String mIndexText;
    private int mIndexStart;


    public TRule(Context context) {
        this(context, null, 0);
    }

    public TRule(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        gestureDetector = new GestureDetector(context, gestureListener);
        initPaint();
        initAttr(attrs, defStyleAttr);
    }

    private void initPaint() {
        mMiddlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TRule, defStyleAttr, 0);
        //标尺开始显示位置(以小刻度为单位)
        mCurrentIndex = typedArray.getInteger(R.styleable.TRule_init_location, 0);
        //大刻度数、一个大刻度包含小刻度数、小刻度间隔宽度
        mBigScaleNum = typedArray.getInteger(R.styleable.TRule_big_scale_num, 12);
        mSmallScaleNum = typedArray.getInteger(R.styleable.TRule_small_scale_num, 10);
        mSmallScaleSpace = (int) typedArray.getDimension(R.styleable.TRule_small_scale_space, DensityUtil.dp2px(mContext, 10));
        //中间线、大、小刻度高度、宽度
        mMiddleLineHeight = typedArray.getDimension(R.styleable.TRule_middle_line_height, DensityUtil.dp2px(mContext, 40));
        mMiddleLineWidth = typedArray.getDimension(R.styleable.TRule_middle_line_width, DensityUtil.dp2px(mContext, (float) 0.5));
        mBigScaleHeight = typedArray.getDimension(R.styleable.TRule_big_scale_height, DensityUtil.dp2px(mContext, 20));
        mBigScaleWidth = typedArray.getDimension(R.styleable.TRule_big_scale_height, DensityUtil.dp2px(mContext, (float) 0.5));
        mSmallScaleHeight = typedArray.getDimension(R.styleable.TRule_small_scale_height, DensityUtil.dp2px(mContext, 10));
        mSmallScaleWidth = typedArray.getDimension(R.styleable.TRule_small_scale_width, DensityUtil.dp2px(mContext, (float) 0.5));
        //中间线、大、小刻度颜色
        mMiddleLineColor = typedArray.getColor(R.styleable.TRule_middle_line_color, mContext.getResources().getColor(R.color.middle_line_color));
        mBigScaleColor = typedArray.getColor(R.styleable.TRule_big_scale_color, mContext.getResources().getColor(R.color.big_scale_color));
        msmallScaleColor = typedArray.getColor(R.styleable.TRule_small_scale_color, mContext.getResources().getColor(R.color.small_scale_color));
        //刻度字体大小以及选中大小
        mTextSize = typedArray.getDimension(R.styleable.TRule_text_size, DensityUtil.sp2px(mContext, 12));
        mTextSizeChoose = typedArray.getDimension(R.styleable.TRule_text_size_choose, DensityUtil.sp2px(mContext, 15));
        //刻度字体颜色、以及选中颜色
        mTextColor = typedArray.getColor(R.styleable.TRule_text_color, mContext.getResources().getColor(R.color.text_color));
        mTextColorChoose = typedArray.getColor(R.styleable.TRule_text_color_choose, mContext.getResources().getColor(R.color.text_color_choose));
        //底部线颜色、高、底部线距离控件底部距离
        mBottomLineColor = typedArray.getColor(R.styleable.TRule_bottom_color, mContext.getResources().getColor(R.color.bottom_line_color));
        mBottomLineHeight = typedArray.getDimension(R.styleable.TRule_bottom_line_height, DensityUtil.dp2px(mContext, (float) 0.5));
        mToBottomHeight = typedArray.getDimension(R.styleable.TRule_to_bottom_height, DensityUtil.dp2px(mContext, 2));
        //文本底部到中间线顶部距离
        mToLineTop = typedArray.getDimension(R.styleable.TRule_to_line_top, DensityUtil.dp2px(mContext, 30));
        //灵敏度(以倍数为记,默认为1,类型为float)
        mSensitiveness = typedArray.getFloat(R.styleable.TRule_sensitiveness, 1);
        //各个下标对应文本(如1月,2月则string为"月",如1天、2天则为"天",前缀Index自动添加)
        mIndexText = typedArray.getString(R.styleable.TRule_index_text);
        //中间标记文本、是否显示(只有大刻度数目为偶数个时候生效)
        mCentText = typedArray.getString(R.styleable.TRule_center_text);
        mShowCentText = typedArray.getBoolean(R.styleable.TRule_show_center_text, true);
        //下标索引开始位置,默认为零
        mIndexStart = typedArray.getInteger(R.styleable.TRule_index_start, 0);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpec.getSize(widthMeasureSpec);
        MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();
        drawBottomLine(canvas);
        drawScale(canvas);
        drawMiddleLine(canvas);
    }


    private void drawBottomLine(Canvas canvas) {
        mBottomPaint.setStrokeWidth(mBottomLineHeight);
        mBottomPaint.setColor(mBottomLineColor);
        canvas.drawLine(0, mHeight - mToBottomHeight, mWidth, mHeight - mToBottomHeight, mBottomPaint);
    }

    private void drawMiddleLine(Canvas canvas) {
        mMiddlePaint.setStrokeWidth(mMiddleLineWidth);
        mMiddlePaint.setColor(mMiddleLineColor);
        canvas.drawLine(mWidth / 2, mHeight - DensityUtil.dp2px(mContext, 2) - mMiddleLineHeight, mWidth / 2, mHeight - DensityUtil.dp2px(mContext, 2), mMiddlePaint);
    }

    private void drawScale(Canvas canvas) {
        mBigScalePaint.setColor(mBigScaleColor);
        mBigScalePaint.setStrokeWidth(mBigScaleWidth);
        mSmallScalePaint.setColor(msmallScaleColor);
        mSmallScalePaint.setStrokeWidth(mSmallScaleWidth);
        float startLocation = (mWidth / 2) - mSmallScaleSpace * mCurrentIndex;
        for (int i = 0; i <= mSmallScaleNum * mBigScaleNum; i++) {
            float location = startLocation + i * mSmallScaleSpace;
            if (i % mSmallScaleNum == 0) {
                //大刻度
                canvas.drawLine(location, mHeight - mToBottomHeight, location, mHeight - mToBottomHeight - mBigScaleHeight, mBigScalePaint);
                String drawStr = null;
                if (mBigScaleNum % 2 == 0 && mShowCentText) {
                    drawStr = bigNumIsEven(i);
                } else {
                    drawStr = bigNumIsOdd(i);
                }
                Rect bounds = new Rect();
                if (i == mCurrentIndex) {
                    mTextPoint.setColor(mTextColorChoose);
                    mTextPoint.setTextSize(DensityUtil.sp2px(mContext, (float) 18
                    ));
                } else {
                    mTextPoint.setColor(mTextColor);
                    mTextPoint.setTextSize(DensityUtil.sp2px(mContext, (float) 15.5));
                }
                mTextPoint.getTextBounds(drawStr, 0, drawStr.length(), bounds);
                //添加刻度文字
                canvas.drawText(drawStr, location - bounds.width() / 2, mHeight - mToBottomHeight - mMiddleLineHeight - mToLineTop, mTextPoint);
            } else {
                //小刻度
                canvas.drawLine(location, mHeight - mToBottomHeight, location, mHeight - mToBottomHeight - mSmallScaleHeight, mSmallScalePaint);
            }

        }
    }

    @NonNull
    private String bigNumIsOdd(int i) {
        String drawStr;
        if (mIndexStart > 0) {
            int j = i / mSmallScaleNum + mIndexStart;
            drawStr = j + mIndexText;
        } else {
            drawStr = i / mSmallScaleNum + mIndexText;
        }
        return drawStr;
    }

    @NonNull
    private String bigNumIsEven(int i) {
        String drawStr;
        if (i / mSmallScaleNum < mBigScaleNum / 2) {
            int j = i / mSmallScaleNum;
            if (mIndexStart > 0) {
                j = j + mIndexStart;
            }
            drawStr = j + mIndexText;
        } else if (i / mSmallScaleNum == mBigScaleNum / 2) {
            drawStr = mCentText;
        } else {
            int j = i / mSmallScaleNum - 1;
            if (mIndexStart > 0) {
                j = j + mIndexStart;
            }
            drawStr = j + mIndexText;
        }
        return drawStr;
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
                if (Math.abs(mOnceTouchEventOffset) > mSmallScaleSpace * mSmallScaleNum) {
                    round = Math.round(mPos / (float) mSmallScaleNum);
                } else if (mOnceTouchEventOffset < 0) {
                    double ceil = Math.ceil(mPos / (float) mSmallScaleNum);
                    round = (int) ceil;
                } else {
                    double floor = Math.floor(mPos / (float) mSmallScaleNum);
                    round = (int) floor;
                }
                innerSetCurrentIndex(round * mSmallScaleNum, true, true);
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
        mOnceTouchEventOffset += delta * mSensitiveness;
        //偏移量叠加
        mScrollingOffset += delta * mSensitiveness;
        //总共滚动了多少个Item
        int mCount = mScrollingOffset / mSmallScaleSpace;
        //当前刻度位置
        mPos = mCurrentIndex - mCount;
        if (mPos < 0) {
            mPos = 0;
        } else if (mPos >= mBigScaleNum * mSmallScaleNum) {
            mPos = mBigScaleNum * mSmallScaleNum;
        }
        if (mPos != mCurrentIndex) {
            innerSetCurrentIndex(mPos, true, false);
        }
        mScrollingOffset = mScrollingOffset - mCount * mSmallScaleSpace;
    }

    int position;

    public void setCurrentIndex(int index) {
        innerSetCurrentIndex(index, false, true);
    }

    public void innerSetCurrentIndex(int index, boolean inner, boolean callBack) {
        if (mCurrentIndex < 0 || mCurrentIndex > mBigScaleNum * mSmallScaleNum) {
            return;
        }
        if (!inner) {
            mCurrentIndex = Math.round(index / mSmallScaleNum) * mSmallScaleNum;
        } else {
            mCurrentIndex = index;
        }
        invalidate();
        if (mCurrentIndex / mSmallScaleNum <= mSmallScaleNum / 2) {
            position = mCurrentIndex / mSmallScaleNum + 1;
        } else {
            position = mCurrentIndex / mSmallScaleNum;
        }
        if (onRulerChangeListener != null && callBack) {
            switch (mBigScaleNum % 2) {
                case 0:
                    if (mShowCentText) evenCallBack(position - 1);
                    else oddCallBack(position-1);
                    break;
                case 1:
                    oddCallBack(position - 1);
                    break;
            }
        }
    }


    private void evenCallBack(int position) {
        if (mIndexStart > 0) {
            if (position < mBigScaleNum / 2)
                onRulerChangeListener.onRuleChanged(position + mIndexStart);
            else if (position == mBigScaleNum / 2)
                onRulerChangeListener.onRuleChanged(-1);
            else onRulerChangeListener.onRuleChanged(position + mIndexStart - 1);
        } else {
            if (position < mBigScaleNum / 2)
                onRulerChangeListener.onRuleChanged(position);
            else if (position == mBigScaleNum / 2)
                onRulerChangeListener.onRuleChanged(-1);
            else onRulerChangeListener.onRuleChanged(position - 1);
        }
    }

    private void oddCallBack(int position) {
        if (mIndexStart > 0) {
            onRulerChangeListener.onRuleChanged(position + mIndexStart);
        } else {
            onRulerChangeListener.onRuleChanged(position);
        }

    }


    public interface OnRulerChangeListener {
        void onRuleChanged(int position);

    }

    public void setOnRulerChangeListener(OnRulerChangeListener onRulerChangeListener) {
        this.onRulerChangeListener = onRulerChangeListener;
    }
}
