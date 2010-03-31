package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class myPath extends Figure {
	
	private float[] points;
	private Paint paint;
	private Paint paintBorder;
	private char type;
	private Path path;
	private boolean Z;
	private SubPath[] subPath;
	private Transformations tr;
	
	/**
	 * Crea un camino entre puntos
	 * @param isRelative indica si la posición de los primeros puntos es relativa o absoluta
	 * @param x1 Coordenada x del punto 1
	 * @param y1 Coordenada y del punto 1
	 * @param type Tipo de camino (1 L, 2 H, 3 V, etc...)
	 * @param points Array de puntos para dibujar el camino.
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF
	 * @param bwidth Grosor del segmento.
	 */
	public myPath (SubPath[] subPath, boolean isZ, String rgb, String brgb, float bwidth, Transformations tr)
	{
		this.tr = tr;
		this.subPath = new SubPath[subPath.length];
		
		for(int i=0; i<subPath.length;i++)
			this.subPath[i] = new SubPath(subPath[i].getType(), subPath[i].getPoints());
		
		this.Z = isZ;
		
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
		
		this.path = new Path();
	}
	
	public void draw (Canvas canvas){
		canvas.save();
		this.tr.applyTransformations(canvas);
		for(int i=0; i<subPath.length; i++)
		{
			type = subPath[i].getType();
			points = subPath[i].getPoints();
			
			switch(type){
			case 'm':
				path.rMoveTo(points[0], points[1]);
				break;
			case 'M':
				path.moveTo(points[0], points[1]);
				break;
			case 'L':
				for (int j=0; j<points.length; j+=2)
					path.lineTo(points[j], points[j+1]);
				break;
			case 'C':
				for(int j=0; j<points.length; j+=6)
					path.cubicTo(points[j], points[j+1],points[j+2],points[j+3],points[j+4],points[j+5]);
				break;
			case 'Q':
				for(int j=0; j<points.length; j+=4)
					path.quadTo(points[j], points[j+1],points[j+2],points[j+3]);
				break;
			}
				
		}
		
		
		if(Z){
			points = subPath[0].getPoints();
			path.lineTo(points[0], points[1]);
		}
		canvas.drawPath(path, paint);
		canvas.drawPath(path, paintBorder);
		canvas.restore();
	}
	
}
