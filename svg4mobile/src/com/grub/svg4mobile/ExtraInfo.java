package com.grub.svg4mobile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.content.Context;
import android.app.Instrumentation; 

public class ExtraInfo {

	private BRect frame;
	private Text title;
	private Paint paint=new Paint();
	private Paint paint2=new Paint();
	private RectF drawable_area;  
	private Bitmap cached_dummy = BitmapFactory.decodeFile("/sdcard/1.png");
	private float x;
	private float y;
	private float w;
	private float h;
	

	public ExtraInfo(float x, float y, float w, float h, String title, String description, String image, String notes, String rgb, String[] tags){
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		//this.title = title;
		cached_dummy = BitmapFactory.decodeFile(image);
		//BitmapFactory.
		drawable_area = new RectF(0,0,w,h);
		frame = new BRect(x-10, y-10, w+10, h+30, rgb, "", 0, new Transformations());
	}
	/**
	 * Pinta la figura
	 * @param gl
	 */
	public void draw(Canvas canvas) {
		//Se aplican las transformaciones a la figura
		frame.draw(canvas);
		canvas.save();
		paint.setStyle(Paint.Style.FILL);  
        paint.setColor(Color.TRANSPARENT); 
        canvas.drawPaint(paint);  
        paint.setColor(Color.WHITE);  
		canvas.translate(this.x, this.y);
		canvas.drawBitmap(cached_dummy,null,drawable_area,paint);
		
        this.paint2.setARGB(255, 255, 60, 10);
		paint.setStyle(Paint.Style.FILL);  
        this.paint2.setTextSize(50);
        this.paint2.setFakeBoldText(true);

        
		canvas.drawText("itIsPizzaTime", 16, 110, this.paint2); 
		canvas.restore();
	}
	
	
}
