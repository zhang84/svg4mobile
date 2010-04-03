package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class BCircle extends Figure{
	
	private Paint paint;
	private Paint paintBorder;
	private Transformations tr;
	private float cx;
	private float cy;
	private float r;
	
	/**
	 * 
	 * @param cx
	 * @param cy
	 * @param rx
	 * @param ry
	 * @param rgb
	 * @param brgb
	 * @param bwidth
	 * @param tr
	 */
	public BCircle (float cx, float cy, float r, String rgb, String brgb, float bwidth, Transformations tr){
		
		this.tr = tr;
		this.cx = cx;
		this.cy = cy;
		this.r = r;
		
		this.paintBorder = new Paint();
		if (bwidth>0) {
			this.paintBorder.setStyle(Paint.Style.STROKE);
			this.paintBorder.setStrokeWidth(bwidth);
			this.paintBorder.setColor(Color.parseColor(brgb));
			this.paintBorder.setAntiAlias(true);
		}
		
		this.paint = new Paint();
		this.paint.setColor(Color.parseColor(rgb));
		this.paint.setAntiAlias(true);	
	}
	
	/**
	 * 
	 */
	public void draw (Canvas canvas){
		canvas.save();
		this.tr.applyTransformations(canvas);
		canvas.drawCircle(this.cx, this.cy, this.r, paint);
		canvas.drawCircle(this.cx, this.cy, this.r, paintBorder);
		canvas.restore();
	}
	
}