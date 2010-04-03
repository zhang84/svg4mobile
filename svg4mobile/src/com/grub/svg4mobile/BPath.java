package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/**
 * Pinta figuras de tipo Path
 * @see http://www.w3.org/TR/SVG11/paths.html
 */
public class BPath extends Figure {
	
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
	 * @param subPath
	 * @param isZ
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BPath (SubPath[] subPath, boolean isZ, String rgb, String brgb, float bwidth, Transformations tr)
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
	
	/**
	 * Dibuja la figura
	 * @param canvas
	 */
	public void draw (Canvas canvas){
		canvas.save();
		this.tr.applyTransformations(canvas);
		for(int i=0; i<subPath.length; i++)
		{
			type = subPath[i].getType();
			points = subPath[i].getPoints();
			
			//Log.d("svg4mobile", " Pintnado Path tipo: " + type );
			
			switch(type){
			case 'm':
				if (points.length > 2)
				{
					path.moveTo(points[0], points[1]);
					for (int j=2; j<points.length; j+=2){
						path.rLineTo(points[j], points[j+1]);
						//Log.d("svg4mobile", " m: x " + points[j] +" y " +  points[j+1]);
					}
				}
				else
					path.moveTo(points[0], points[1]);
				break;
			case 'M':
				path.moveTo(points[0], points[1]);
				break;
			case 'l':
				for (int j=0; j<points.length; j+=2)
					path.rLineTo(points[j], points[j+1]);
				break;
			case 'L':
				for (int j=0; j<points.length; j+=2)
					path.lineTo(points[j], points[j+1]);
				break;
			case 'c':
				for(int j=0; j<points.length; j+=6)
					path.rCubicTo(points[j], points[j+1],points[j+2],points[j+3],points[j+4],points[j+5]);
				break;
			case 'C':
				for(int j=0; j<points.length; j+=6)
					path.cubicTo(points[j], points[j+1],points[j+2],points[j+3],points[j+4],points[j+5]);
				break;
			case 'q':
				for(int j=0; j<points.length; j+=4)
					path.rQuadTo(points[j], points[j+1],points[j+2],points[j+3]);
				break;
			case 'Q':
				for(int j=0; j<points.length; j+=4)
					path.quadTo(points[j], points[j+1],points[j+2],points[j+3]);
				break;
			case 's':
			case 'S':
			case 't':
			case 'T':
			case 'a':
			case 'A':
			case 'h':
			case 'H':
			case 'v':
			case 'V':
				//TODO
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
