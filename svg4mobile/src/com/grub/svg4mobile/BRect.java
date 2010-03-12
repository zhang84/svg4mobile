package com.grub.svg4mobile;

import javax.microedition.khronos.opengles.GL10;

public class BRect extends Figure {

	private Rect shape;
	private Boolean hasWidth = false;
	private Line borderTop;
	private Line borderDown;
	private Line borderLeft;
	private Line borderRight;
	public BRect(float x, float y, float w, float h, String rgb, String brgb, float bwidth) { 
		this.shape = new Rect(x,y,w,h,rgb);
		this.hasWidth = ( bwidth > 0);
		if (this.hasWidth) {
			this.borderTop = new Line( x, y+h, x+w, y+h, brgb, bwidth );
			this.borderDown = new Line( x, y, x+w, y, brgb, bwidth );
			this.borderLeft = new Line( x, y+h, x, y, brgb, bwidth );
			this.borderRight = new Line( x+w, y, x+w, y+h, brgb, bwidth );
		}
	}
	
	public void draw(GL10 gl) {
		this.shape.draw(gl);
		if (this.hasWidth) {
			this.borderTop.draw(gl);
			this.borderDown.draw(gl);
			this.borderLeft.draw(gl);
			this.borderRight.draw(gl);
		}
	}
	
}
