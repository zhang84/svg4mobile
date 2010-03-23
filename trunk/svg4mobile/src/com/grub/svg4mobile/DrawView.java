package com.grub.svg4mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class DrawView extends View{

	private Line myLine;
    private Rect myRect;
    
    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
         
        myLine = new Line(10, 20, 30, 40, Color.WHITE);
        
        myRect = new Rect(100, 100, 200, 200, Color.CYAN);
        
    }

    @Override
    public void onDraw(Canvas canvas) {
    	myLine.Draw(canvas);
    	myRect.Draw(canvas);
    	
    }
    
}