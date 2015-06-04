package com.slanissue.drawcircle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyCircleProgress extends ImageView{

	private Context context;
	//画笔
	private Paint mPaint;
	//进度条的值，实际上是角度
	public int n = 0;
	public MyCircleProgress(Context context) {
		super(context);
		this.context = context;
	}
	public MyCircleProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		mPaint = new Paint();//创建画笔
		//设置画笔
		mPaint.setAntiAlias(true);//消除锯齿
		mPaint.setStyle(Style.STROKE);//绘制空心的圆形，或者矩形
		
	}
	public MyCircleProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// Canvas 画布
		super.onDraw(canvas);
		int center = getWidth()/2;//圆心的位置,布局控件的宽度
		int outCircle = Math.abs(center - getLeft());//外圆的半径
		int innerCircle = Math.abs(center - getLeft() - outCircle/2);//内圆的半径
		int ringWidth = outCircle - innerCircle;//圆弧的宽度
		
		//第一种方法绘制圆环
		//绘制内圆
		this.mPaint.setARGB(255, 255, 0, 0);//设置画笔的颜色，透明度和rgb值
		this.mPaint.setStrokeWidth(2);//设置画笔的宽度
		canvas.drawCircle(center, center, innerCircle - 1, mPaint);
		
		//绘制圆环
		this.mPaint.setARGB(255, 0, 255, 0);//设置画笔的颜色为绿色
		this.mPaint.setStrokeWidth(ringWidth);//圆环的宽度
		
		int top = (center - (innerCircle + ringWidth/2));
		int bottom = (center + (innerCircle + ringWidth/2));
		RectF oval = new RectF(top, top, bottom, bottom);//矩形
		//最上方的那个点事270度，从270度开始画
		canvas.drawArc(oval, 0, n, false, mPaint);//false 表示不连接圆弧的起点和终点生成闭合的圆弧
		
		//绘制外圆
		this.mPaint.setARGB(255, 0, 0, 255);
		this.mPaint.setStrokeWidth(2);
		canvas.drawCircle(center, center, outCircle, mPaint);
		
		//在中心绘制一个图片（正方形）
		int x = Math.abs(center - innerCircle);
		int width = center + innerCircle;
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		Rect dst = new Rect();
		
		//在中间，大小变成原来的三分之一
		dst.left = x +(center - innerCircle)/3;
		dst.top = x + (center - innerCircle)/3;
		dst.right = width - (center - innerCircle)/3;
		dst.bottom = width - (center - innerCircle)/3;
		
		canvas.drawBitmap(bm, null, dst, mPaint);
	}
	/**
	 * 根据手机的分辨率从dp的单位转成px像素
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dpValue * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从px转换成dp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	/**
	 * View视图的测量方法
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}
	
	private int measureWidth(int widthMeasureSpec){
		int result = 0;
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int SpecSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = SpecSize;
		}else {
			result = dip2px(context, 120);
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, SpecSize);
			}
		}
		return result;
	}
	private int measureHeight(int heightMeasureSpec){
		int result = 0;
		int specMode = MeasureSpec.getMode(heightMeasureSpec);
		int SpecSize = MeasureSpec.getSize(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = SpecSize;
		}else {
			result = dip2px(context, 120);
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, SpecSize);
			}
		}
		return result;
	}
	
}
