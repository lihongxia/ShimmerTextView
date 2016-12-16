package com.example.gradientdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lihongxia on 16/12/16.
 * 通过平移动画设置文字色值渐变
 * 缺陷：不能对textview的drawable做渐变
 */
public class ShimmerTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;
    private int orientation = 0;

    private boolean mAnimating = true;

    public ShimmerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShimmerTextView, 0, 0);
        orientation = ta.getInt(R.styleable.ShimmerTextView_orientation, 0);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mTranslate = mViewWidth;
                if (orientation == 0){
                    mPaint = getPaint();
                    mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                            new int[] { 0x33ffffff, 0xffffffff, 0x33ffffff },
                            new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
                    mPaint.setShader(mLinearGradient);
                    mGradientMatrix = new Matrix();
                }else if (orientation == 1){
                    mPaint = getPaint();
                    mLinearGradient = new LinearGradient(mViewWidth, 0, 0, 0,
                            new int[] { 0x33ffffff, 0xffffffff, 0x33ffffff },
                            new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
                    mPaint.setShader(mLinearGradient);
                    mGradientMatrix = new Matrix();
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimating && mGradientMatrix != null) {
            if (orientation == 0){
                mTranslate += mViewWidth / 10;
                if (mTranslate > 2 * mViewWidth) {
                    mTranslate = -mViewWidth;
                }
                mGradientMatrix.setTranslate(mTranslate, 0);
                mLinearGradient.setLocalMatrix(mGradientMatrix);
                postInvalidateDelayed(50);
            }else if (orientation == 1){
                mTranslate -= mViewWidth / 10;
                if (mTranslate < -mViewWidth - mViewWidth / 10) {
                    mTranslate = mViewWidth;
                }
                mGradientMatrix.setTranslate(mTranslate, 0);
                mLinearGradient.setLocalMatrix(mGradientMatrix);
                postInvalidateDelayed(50);
            }
        }
    }

    public void setAnim(boolean anim){
        mAnimating = anim;
        invalidate();
    }

}
