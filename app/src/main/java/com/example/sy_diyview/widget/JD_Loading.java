package com.example.sy_diyview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by SY
 * date: 2021-10-24
 */

public class JD_Loading extends View {
    private float start,stop;
    //用于标记是否开始进行旋转和小狗路径绘制
    private Boolean isOneFinish = false;
    //用于保存小狗图标的路径
    private Path path_dst_dog,dst_yellow,dst_red;
    //俩小球平移值
    private float range;
    //目前属性动画的进行值
    private float mCurAnimValue;
    //小球的半径
    private float radius;
    //整个控件的大小
    private int width,height;
    //所有view的画笔
    private Paint paint_red,paint_yellow,paint_gray,paint_dog,paint_path_yellow,paint_path_red;
    //测量路径的变量
    private PathMeasure pathMeasure_yellow,pathMeasure_red,pathMeasure_dog;

    public JD_Loading(Context context) {
        super(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JD_Loading(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //初始化画笔
        paint_red = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_red.setStyle(Paint.Style.FILL_AND_STROKE);
        paint_red.setColor(Color.RED);
        paint_red.setStrokeWidth(5);
        paint_red.setAntiAlias(true);
        paint_yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_yellow.setStyle(Paint.Style.FILL_AND_STROKE);
        paint_yellow.setColor(Color.parseColor("#FFAE21"));
        paint_yellow.setStrokeWidth(5);
        paint_yellow.setAntiAlias(true);

        paint_path_red = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_path_red.setStrokeWidth(7);
        paint_path_red.setStyle(Paint.Style.STROKE);
        paint_path_red.setColor(Color.RED);
        paint_path_red.setAntiAlias(true);
        paint_path_yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_path_yellow.setStrokeWidth(7);
        paint_path_yellow.setStyle(Paint.Style.STROKE);
        paint_path_yellow.setColor(Color.parseColor("#FFAE21"));
        paint_path_yellow.setAntiAlias(true);

        paint_gray = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_gray.setColor(Color.parseColor("#EFEFEF"));
        paint_gray.setStyle(Paint.Style.FILL_AND_STROKE);
        paint_gray.setAntiAlias(true);
        paint_dog = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_dog.setColor(Color.RED);
        paint_dog.setStyle(Paint.Style.STROKE);
        paint_dog.setStrokeWidth(2);
        paint_dog.setAntiAlias(true);

        //初始化属性动画
        ValueAnimator animator = ValueAnimator.ofFloat(0,3);
        animator.setDuration(1800);
        animator.setInterpolator(new DecelerateInterpolator());
        //设置重复播放及其模式
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //设置监听0-3不同值进行对应的操作
                mCurAnimValue=(float)animation.getAnimatedValue();
                if ( 1-mCurAnimValue > 0.01) {
                    stop = 0;
                    start = 0;
                    //中间小球开始变大
                    path_dst_dog = new Path();

                    isOneFinish = false;
                    range = 0;
                    radius = mCurAnimValue*7;
                    invalidate();
                }
                if(mCurAnimValue-1>0.01&2-mCurAnimValue>0.01){
                    //分出俩小球开始平移
                    range =((float) animation.getAnimatedValue()-1) * (width/2-radius);
                    invalidate();
                }
                if (mCurAnimValue-2>0.01&3-mCurAnimValue>0.01){
                    dst_red = new Path();
                    dst_yellow = new Path();
                    //俩小球开始旋转并开始绘制小狗路径
                    isOneFinish = true;
                    stop = (mCurAnimValue-2)*pathMeasure_yellow.getLength();
                    if(mCurAnimValue<2.5){
                        start = stop-14;
                    }
                    else {
                        start = stop*5/6;
                    }

//                    pathMeasure_yellow.getPosTan(length,pos_yellow,null);
//                    pathMeasure_red.getPosTan(length,pos_red,null);
                    pathMeasure_yellow.getSegment(start,stop,dst_yellow,true);
                    pathMeasure_red.getSegment(start,stop,dst_red,true);
                    invalidate();
                }
            }
        });
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取控件高和宽
        width = w;
        height = h;
//        左边黄色小球路径
        Path path_yellow = new Path();
        path_yellow.addArc(7,7,width-7,height-7,-180,360);
//        右边红色小球路径
        Path path_red = new Path();
        path_red.addArc(7,7,width-7,height-7,0,360);
//        为测量值绑定对应的path
        pathMeasure_yellow = new PathMeasure(path_yellow,false);
        pathMeasure_red = new PathMeasure(path_red,false);
//        绘制小狗图标的路径,这里经过数学计算以及后期调试确定的，适配等比例缩放
        Path path_dog = new Path();
        path_dog.moveTo(width/2-width/14,height/2);
        path_dog.quadTo(width/3,height/2-height/40,7*width/24,9*height/24);
        path_dog.quadTo(7*width/72+7*width/48,10*height/24,width/6,9*height/24);
        path_dog.lineTo(7*width/24-width/30,height/4);
        path_dog.lineTo(7*width/24,7*height/24);
        path_dog.quadTo(width/2,height/6,17*width/24,7*height/24);
        path_dog.lineTo(17*width/24+width/30,height/4);
        path_dog.lineTo(5*width/6,9*height/24);
        path_dog.quadTo(width-7*width/72-7*width/48,10*height/24,17*width/24,9*height/24);
        path_dog.quadTo(2*width/3,height/2-height/40,width/2+width/14,height/2);
        path_dog.quadTo(width/2+width/6,2*height/3-height/20,width/2+width/8,2*height/3);
        path_dog.lineTo(width/2+width/10,2*height/3);
        path_dog.lineTo(width/2+width/10,2*height/3+height/30);
        path_dog.lineTo(width/2+width/100,2*height/3+height/30);
        path_dog.lineTo(width/2+width/100,2*height/3);
        path_dog.lineTo(width/2-width/100,2*height/3);
        path_dog.lineTo(width/2-width/100,2*height/3+height/30);
        path_dog.lineTo(width/2-width/10,2*height/3+height/30);
        path_dog.lineTo(width/2-width/10,2*height/3);
        path_dog.lineTo(width/2-width/8,2*height/3);
        path_dog.quadTo(width/2-width/6,2*height/3-height/20,width/2-width/14,height/2);
        pathMeasure_dog = new PathMeasure(path_dog,false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制中心灰色圆圈
        canvas.drawCircle(width/2,height/2,range,paint_gray);
        //根据不同的标记及当前属性动画值进行不同的绘制操作
        if (isOneFinish){
            //俩球旋转及小狗路径的绘制
            //设定透明度随动画进行而改变(可选)
            paint_dog.setAlpha((int)((1-mCurAnimValue/3)*255)+60);
            paint_path_red.setAlpha((int)((1-mCurAnimValue/3)*255)+20);
            paint_path_yellow.setAlpha((int)((1-mCurAnimValue/3)*255)+20);
            paint_gray.setAlpha((int)((1-mCurAnimValue/3)*255)+40);

            canvas.drawPath(dst_red,paint_path_red);
            canvas.drawPath(dst_yellow,paint_path_yellow);

//            canvas.drawCircle(pos_yellow[0],pos_yellow[1],radius,paint_yellow);
//            canvas.drawCircle(pos_red[0],pos_red[1],radius,paint_red);

            pathMeasure_dog.getSegment(0,(mCurAnimValue-2)*pathMeasure_dog.getLength(),path_dst_dog,true);
            canvas.drawPath(path_dst_dog,paint_dog);
        }
        else {
            if(1-mCurAnimValue > 0.01){
//                中间小球放大操作
//                可选（如果前面改变透明度后这里需要恢复）
                paint_dog.setAlpha(120);
                paint_red.setAlpha(255);
                paint_yellow.setAlpha(255);
                paint_gray.setAlpha(255);
                canvas.drawCircle(width/2,height/2,radius,paint_red);
            }
            if(mCurAnimValue-1>0.01&1.99-mCurAnimValue>0.01){
//                俩球平移操作
                canvas.drawCircle(width/2-range,height/2,radius,paint_yellow);
                canvas.drawCircle(width/2+range,height/2,radius,paint_red);
            }
        }
    }
}
