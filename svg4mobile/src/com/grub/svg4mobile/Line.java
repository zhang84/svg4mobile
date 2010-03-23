package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line {
	
	private float x1, x2, y1, y2;
	private Paint paint;
	
	public Line (float xIni, float yIni, float xFin, float yFin, int color){
		this.x1 = xIni;
		this.y1 = yIni;
		
		this.x2 = xFin;
		this.y2 = yFin;
		
		this.paint = new Paint();
		
		this.paint.setColor(color);
		this.paint.setAntiAlias(true);
		
	}
	
	public void Draw (Canvas canvas){
		canvas.drawLine(this.x1, this.y1, this.x2, this.y2, this.paint);
	}
	
}
