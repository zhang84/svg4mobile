package com.grub.svg4mobile;

import java.util.Enumeration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Rect extends Figure{
	
	private Paint paint;
	private RectF rectElement;
	private float ry = 0;
	private float rx = 0;
	
	public Rect (float x, float y, float w, float h, float rx, float ry, String rgb){
		this.rx = rx;
		this.ry = ry;
		this.rectElement = new RectF(x, y, w, h);
		
		this.paint = new Paint();		
		this.paint.setColor(Color.parseColor(rgb));
		this.paint.setAntiAlias(true);		
	}
	
	public void draw (Canvas canvas){
		//canvas.drawRect(this.rectElement, this.paint);
		canvas.drawRoundRect(this.rectElement, this.rx, this.ry, this.paint);
	}

	@Override
	public void addFigure(Figure f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Enumeration<Figure> getFigures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeFigure(Figure f) {
		// TODO Auto-generated method stub
		
	}
	
}
