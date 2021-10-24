package com.example.sy_diyview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by SY
 * date: 2021-10-24
 */

public class Bilibli_Loading extends View {
    private float radius = 7;
    private float mCurValue;
    private Path path,dstPath;
    private int width,height;
    private Paint paint,paint_circle_1,paint_circle_2,paint_circle_3;
    private PathMeasure pathMeasure;
    public Bilibli_Loading(Context context) {
        super(context);
    }

    public Bilibli_Loading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#fa729a"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        paint_circle_1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_circle_1.setColor(Color.parseColor("#fa729a"));
        paint_circle_1.setStyle(Paint.Style.FILL);
        paint_circle_1.setStrokeWidth(5);

        paint_circle_2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_circle_2.setColor(Color.parseColor("#fa729a"));
        paint_circle_2.setStyle(Paint.Style.FILL);
        paint_circle_2.setStrokeWidth(5);

        paint_circle_3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_circle_3.setColor(Color.parseColor("#fa729a"));
        paint_circle_3.setStyle(Paint.Style.FILL);
        paint_circle_3.setStrokeWidth(5);

        ValueAnimator animator = ValueAnimator.ofFloat(0,3);
        animator.setDuration(4000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurValue = (float) animation.getAnimatedValue();
                if (1-mCurValue>0.01){
                    dstPath = new Path();
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
        path = new Path();
        path.moveTo(width/3,height/6);
        path.lineTo(width/2,height/3);
        path.lineTo(width/4,height/3);
        path.quadTo(width/8,height/3,width/8,5*height/12);
        path.lineTo(width/8,3*height/4);
        path.quadTo(width/8,5*height/6,width/4,5*height/6);
        path.lineTo(7*width/8-5*height/6+3*height/4,5*height/6);
        path.quadTo(7*width/8,5*height/6,7*width/8,3*height/4);
        path.lineTo(7*width/8,5*height/12);
        path.quadTo(7*width/8,height/3,3*width/4,height/3);
        path.lineTo(width/2,height/3);
        path.lineTo(2*width/3,height/6);
        pathMeasure = new PathMeasure(path,false);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurValue<1.01){
            pathMeasure.getSegment(0,mCurValue*pathMeasure.getLength(),dstPath,true);
        }
        canvas.drawPath(dstPath,paint);
        if (mCurValue-1>0.01&2-mCurValue>0.01){
            float value = mCurValue-1;
            if (value<0.6){
                paint_circle_1.setAlpha(255-(int) (255/0.6*value));
                canvas.drawCircle(3*width/8,7*height/12,radius,paint_circle_1);
            }
            if(value<0.8&value>0.2){
                paint_circle_2.setAlpha(255-(int) (255/0.6*(value-0.2)));
                canvas.drawCircle(4*width/8,7*height/12,radius,paint_circle_2);
            }
            if(value>0.4){
                paint_circle_3.setAlpha(255-(int) (255/0.6*(value-0.4)));
                canvas.drawCircle(5*width/8,7*height/12,radius,paint_circle_3);
            }
        }
        if (mCurValue-2>0.01&3-mCurValue>0.01){
            float value = mCurValue-2;
            if (value<0.6){
                paint_circle_1.setAlpha(255-(int) (255/0.6*value));
                canvas.drawCircle(3*width/8,7*height/12,radius,paint_circle_1);
            }
            if(value<0.8&value>0.2){
                paint_circle_2.setAlpha(255-(int) (255/0.6*(value-0.2)));
                canvas.drawCircle(4*width/8,7*height/12,radius,paint_circle_2);
            }
            if(value>0.4){
                paint_circle_3.setAlpha(255-(int) (255/0.6*(value-0.4)));
                canvas.drawCircle(5*width/8,7*height/12,radius,paint_circle_3);
            }
        }

    }
}
