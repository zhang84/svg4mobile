package com.grub.svg4mobile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Color;

public class Line extends Figure{
	
	private float line[] = {
			0, 0,  // punto 1
			1f, 1f // punto 2
	};
	private String colorString; 
	private FloatBuffer vertexBuffer;
	private float w = 0;
	public Line(float x1, float y1, float x2, float y2, String brgb, float bwidth){
		this.line[0] = x1;
		this.line[1] = y1;

		this.line[2] = x2;
		this.line[3] = y2;
		
		this.w = bwidth;
		this.colorString = brgb;
		
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(line.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(line);
		vertexBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		int c = Color.parseColor(this.colorString);
		gl.glColor4f(Color.red(c)/255, Color.green(c)/255, Color.blue(c)/255, 1.0f);

		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(this.w);
		//gl.glPointSize(100f);

		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		//gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
	}
}
