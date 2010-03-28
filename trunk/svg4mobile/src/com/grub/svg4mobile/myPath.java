package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class myPath extends Figure {
	
	private float[] points;
	private Paint paint;
	private boolean relative;
	private char type;
	private Path myPath;
	private boolean Z;
	
	/**
	 * Crea un camino entre puntos
	 * @param isRelative indica si la posición de los primeros puntos es relativa o absoluta
	 * @param x1 Coordenada x del punto 1
	 * @param y1 Coordenada y del punto 1
	 * @param type Tipo de camino (1 L, 2 H, 3 V, etc...)
	 * @param points Array de puntos para dibujar el camino.
	 * @param brgb CÃ³digo de color hexadecimal de la forma #FFFFFF
	 * @param bwidth Grosor del segmento.
	 */
	public myPath (char m, float m_x1, float m_y1, char type, float[] points, boolean isZ, String brgb, float bwidth)
	{
		
		if(m == 'M')
			this.relative = false;
		else this.relative = true;
		
		this.Z = isZ;
		
		this.points = new float[points.length + 2];
		
		this.points[0] = m_x1;
		this.points[1] = m_y1;
		
		this.type = type;
		
		for(int i = 0; i < points.length; i++){
			this.points[i+2] = points[i];
		}
			
		this.paint = new Paint();
		this.paint.setStrokeWidth(bwidth);
		this.paint.setColor(Color.parseColor(brgb));
		this.paint.setAntiAlias(true);
		
		this.myPath = new Path();

	}
	
	public void draw (Canvas canvas){
		
		if(this.relative)
			myPath.rMoveTo(this.points[0], this.points[1]);
		else
			myPath.moveTo(this.points[0], this.points[1]);
		
		switch (this.type) {
		case 'L':
			for(int i=2; i<this.points.length; i+=2)
				myPath.lineTo(this.points[i], this.points[i+1]);
			break;
		case 'C':
			for(int i=2; i<this.points.length; i+=6)
				myPath.cubicTo(this.points[i], this.points[i+1],this.points[i+2],this.points[i+3],this.points[i+4],this.points[i+5]);
			break;
		case 'Q':
			for(int i=2; i<this.points.length; i+=4)
				myPath.quadTo(this.points[i], this.points[i+1],this.points[i+2],this.points[i+3]);
			break;
		}
		
		if(Z)
			myPath.lineTo(this.points[0], this.points[1]);
			
		canvas.drawPath(myPath, paint);
	}
	
}
