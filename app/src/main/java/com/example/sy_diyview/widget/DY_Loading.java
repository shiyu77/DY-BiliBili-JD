package com.example.sy_diyview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by SY
 * date: 2021-10-24
 */

public class DY_Loading extends View {
    private float radius_mid;
    private float range;
    private float mCurValue;
    private int width,height;
    private Paint paint_green,paint_red,paint_black;
    public DY_Loading(Context context) {
        super(context);
    }

    public DY_Loading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);

        paint_green = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_green.setColor(Color.parseColor("#25f4ee"));
        paint_green.setStyle(Paint.Style.FILL);

        paint_red = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_red.setStyle(Paint.Style.FILL);
        paint_red.setColor(Color.parseColor("#fe2b54"));

        paint_black = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_black.setStyle(Paint.Style.FILL);
        paint_black.setColor(Color.BLACK);

        ValueAnimator animator = ValueAnimator.ofFloat(0,2);
        animator.setDuration(800);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurValue = (float) animation.getAnimatedValue();
                if(mCurValue<1){
                    if(mCurValue<0.5){
                        radius_mid = width/8*mCurValue*2;
                    }
                    else {
                        radius_mid = width/8*(1-mCurValue)*2;
                    }
                    range = width/2*mCurValue;
                }
                else {
                    if(mCurValue<1.5){
                        radius_mid = width/8*(mCurValue-1)*2;
                    }
                    else {
                        radius_mid = width/8*(2-mCurValue)*2;
                    }
                    range = width/2*(mCurValue-1);
                }
                invalidate();

            }
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mCurValue<1){
            canvas.drawCircle(width/4+range,height/2,width/4,paint_green);
            canvas.drawCircle(3*width/4-range,height/2,width/4,paint_red);
            canvas.drawCircle(width/2,height/2,radius_mid,paint_black);
        }
        else {
            canvas.drawCircle(width/4+range,height/2,width/4,paint_red);
            canvas.drawCircle(3*width/4-range,height/2,width/4,paint_green);
            canvas.drawCircle(width/2,height/2,radius_mid,paint_black);
        }

    }
}
