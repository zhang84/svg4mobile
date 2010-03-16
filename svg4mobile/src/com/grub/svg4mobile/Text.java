package com.grub.svg4mobile;

import javax.microedition.khronos.opengles.GL10;

import com.example.android.apis.graphics.spritetext.LabelMaker;

import android.graphics.Color;
import android.graphics.Paint;

public class Text extends Figure {

	private String colorString;
    private Paint mLabelPaint;
    private LabelMaker mLabels;
    private int mLabel;
    private float x,y;
    private boolean cosa = false;
    
	public Text(float x, float y, int size, String rgb) {
		this.colorString = rgb;
		this.x = x;
		this.y = y;
        mLabelPaint = new Paint();
        mLabelPaint.setTextSize(size);
        mLabelPaint.setAntiAlias(true);
        int c = Color.parseColor(this.colorString);
        mLabelPaint.setARGB(255, Color.red(c), Color.green(c), Color.blue(c));

	}
	/**
	 * Función que pinta la figura
	 * @param gl
	 */
	public void draw(GL10 gl) {
		//Asignamos color
		
	        if (mLabels != null) {
	            mLabels.shutdown(gl);
	        } else {
	            mLabels = new LabelMaker(true, 256, 64);
	        }
	        mLabels.initialize(gl);
	        mLabels.beginAdding(gl);
	        mLabel = mLabels.add(gl, "Texto de prueba", mLabelPaint);
	        mLabels.endAdding(gl);
	        cosa = true;
        
        
        mLabels.beginDrawing(gl, 0, 0);
        mLabels.draw(gl, this.x, this.y, mLabel);
        mLabels.endDrawing(gl);
	
	}
}
