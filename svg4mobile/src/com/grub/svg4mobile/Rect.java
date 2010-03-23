package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rect {
	
	private float x1, x2, y1, y2;
	private Paint paint;
	
	public Rect (float izq, float arriba, float drch, float abajo, int color){
		this.x1 = izq;
		this.y1 = arriba;
		
		this.x2 = drch;
		this.y2 = abajo;
		
		this.paint = new Paint();
		
		this.paint.setColor(color);
		this.paint.setAntiAlias(true);
		
	}
	
	public void Draw (Canvas canvas){
		canvas.drawRect(this.x1, this.y1, this.x2, this.y2, this.paint);
	}
	
}
