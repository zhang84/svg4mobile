package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class BEllipse extends Figure{
	
	private Paint paint;
	private RectF rectElement;
	private Paint paintBorder;
	private Transformations tr;
	
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
	public BEllipse (float cx, float cy, float rx, float ry, String rgb, String brgb, float bwidth, Transformations tr){
		
		this.tr = tr;
		this.rectElement = new RectF(cx-rx, cy-ry, 2*rx, 2*ry);
		
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
	
	/*public BEllipse (float rx, float ry, String rgb, String brgb, float bwidth, Transformations tr) {
		//this.BEllipse(0f,0f,rx,ry,rgb,brgb,bwidth,tr);
	}*/
	
	/**
	 * 
	 */
	public void draw (Canvas canvas){
		canvas.save();
		this.tr.applyTransformations(canvas);
		canvas.drawOval(rectElement, paint);
		canvas.drawOval(rectElement, paintBorder);
		canvas.restore();
	}
	
}