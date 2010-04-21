package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
	
	private float[] auxpoints;
	private float antX;
	private float antY;
	private RectF oval;

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
	
	public BPath (SubPath[] subPath, String rgb, String brgb, float bwidth, Transformations tr)
	{
		this.tr = tr;
		this.subPath = new SubPath[subPath.length];
		
		for(int i=0; i<subPath.length;i++)
			this.subPath[i] = new SubPath(subPath[i].getType(), subPath[i].getPoints());
			
		this.paintBorder = new Paint();
		if (bwidth>0) {
			this.paintBorder.setStyle(Paint.Style.STROKE);
			this.paintBorder.setStrokeWidth(bwidth);
			this.paintBorder.setColor(Color.parseColor(brgb));
			this.paintBorder.setAntiAlias(true);
		}
		
		this.Z = false;
		
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
		
		int last_z = 0;
		
		this.tr.applyTransformations(canvas);
		for(int i=0; i<subPath.length; i++)
		{
			type = subPath[i].getType();
			points = subPath[i].getPoints();
			
			switch(type){
			case 'm':
				/**
				 * If a relative moveto (m) appears as the first element of the path,
				 * then it is treated as a pair of absolute coordinates.
				 */
				if (i==0) {
					path.moveTo(points[0], points[1]);
				}
					
				else 
					path.rMoveTo(points[0], points[1]);
				/**
				 *  If a moveto is followed by multiple pairs of coordinates, 
				 *  the subsequent pairs are treated as implicit lineto commands.
				 */
				if (points.length > 2) {
					for (int j=2; j<points.length; j+=2){
						path.rLineTo(points[j], points[j+1]);
					}
				}
				
				break;
			case 'M':
				path.moveTo(points[0], points[1]);
				if (points.length > 2) {
					for (int j=2; j<points.length; j+=2){
						path.lineTo(points[j], points[j+1]);
					}
				}
				break;
			case 'l':
				for (int j=0; j<points.length; j+=2)
					path.rLineTo(points[j], points[j+1]);
				break;
			case 'L':
				for (int j=0; j<points.length; j+=2)
					path.lineTo(points[j], points[j+1]);
				break;
			case 'h':
				antX = 0;
				antY = 0;
				
				if (i > 0){
				 auxpoints = subPath[i-1].getPoints();
				 antX = auxpoints[auxpoints.length - 2];
				 antY = auxpoints[auxpoints.length - 1];
				}
				for (int j=0; j<points.length; j++)
				{
					path.rLineTo(points[j], antY);
				}
				break;
			case 'H':
				antX = 0;
				antY = 0;
				
				if (i > 0){
				 auxpoints = subPath[i-1].getPoints();
				 antX = auxpoints[auxpoints.length - 2];
				 antY = auxpoints[auxpoints.length - 1];
				}
				for (int j=0; j<points.length; j++)
				{
					path.lineTo(points[j], antY);
				}
				break;

			case 'v':
				antX = 0;
				antY = 0;
				
				if (i > 0){
				 auxpoints = subPath[i-1].getPoints();
				 antX = auxpoints[auxpoints.length - 2];
				 antY = auxpoints[auxpoints.length - 1];
				}
				for (int j=0; j<points.length; j++)
				{
					path.rLineTo(antX, points[j]);
				}
				break;

			case 'V':
				antX = 0;
				antY = 0;
				
				if (i > 0){
				 auxpoints = subPath[i-1].getPoints();
				 antX = auxpoints[auxpoints.length - 2];
				 antY = auxpoints[auxpoints.length - 1];
				}
				for (int j=0; j<points.length; j++)
				{
					path.lineTo(antX, points[j]);
				}
				break;
			case 'c':
				for(int j=0; j<points.length; j+=6)
					path.rCubicTo(points[j], points[j+1],points[j+2],points[j+3],points[j+4],points[j+5]);
				break;
			case 'C':
				for(int j=0; j<points.length; j+=6)
					path.cubicTo(points[j], points[j+1],points[j+2],points[j+3],points[j+4],points[j+5]);
				break;
			case 's':
				antX = 0;
				antY = 0;
				
				if (i > 0 && (String.valueOf(subPath[i-1].getType()).matches("[CcsS]"))){
				 auxpoints = subPath[i-1].getPoints();
				 antX =  2 * auxpoints[auxpoints.length - 2] - auxpoints[auxpoints.length - 4];
				 antY = 2 *  auxpoints[auxpoints.length - 1] - auxpoints[auxpoints.length - 3];
				}
				
				for(int j=0; j<points.length; j+=4){
					path.rCubicTo(antX, antY, points[j], points[j+1],points[j+2],points[j+3]);
					antX = points[j+2];
					antY= points[j+3];	
				}
				break;
			case 'S':
				antX = 0;
				antY = 0;
				
				if (i > 0 && (String.valueOf(subPath[i-1].getType()).matches("[CcsS]"))){
				 auxpoints = subPath[i-1].getPoints();
				 antX =  2 * auxpoints[auxpoints.length - 2] - auxpoints[auxpoints.length - 4];
				 antY = 2 *  auxpoints[auxpoints.length - 1] - auxpoints[auxpoints.length - 3];
				}
				
				for(int j=0; j<points.length; j+=4){
					path.cubicTo(antX, antY, points[j], points[j+1],points[j+2],points[j+3]);
					antX = points[j+2];
					antY= points[j+3];	
				}
				break;
			case 'q':
				for(int j=0; j<points.length; j+=4)
					path.rQuadTo(points[j], points[j+1],points[j+2],points[j+3]);
				break;
			case 'Q':
				for(int j=0; j<points.length; j+=4)
					path.quadTo(points[j], points[j+1],points[j+2],points[j+3]);
				break;
			case 't':
				antX = 0;
				antY = 0;
				
				if (i > 0 && (String.valueOf(subPath[i-1].getType()).matches("[QqTt]"))){
				 auxpoints = subPath[i-1].getPoints();
				 antX =  2 * auxpoints[auxpoints.length - 2] - auxpoints[auxpoints.length - 4];
				 antY = 2 *  auxpoints[auxpoints.length - 1] - auxpoints[auxpoints.length - 3];
				}
				
				for(int j=0; j<points.length; j+=2){
					path.rQuadTo(antX, antY, points[j], points[j+1]);
					antX = points[j];
					antY= points[j+1];	
				}
				break;
			case 'T':
				antX = 0;
				antY = 0;
				
				if (i > 0 && (String.valueOf(subPath[i-1].getType()).matches("[QqTt]"))){
				 auxpoints = subPath[i-1].getPoints();
				 antX =  2 * auxpoints[auxpoints.length - 2] - auxpoints[auxpoints.length - 4];
				 antY = 2 *  auxpoints[auxpoints.length - 1] - auxpoints[auxpoints.length - 3];
				}
				
				for(int j=0; j<points.length; j+=2){
					path.quadTo(antX, antY, points[j], points[j+1]);
					antX = points[j];
					antY= points[j+1];	
				}
				break;
			case 'a':
				antX = 0;
				antY = 0;
				if(i>0){
					auxpoints = subPath[i-1].getPoints();
					antX = auxpoints[auxpoints.length - 2];
					antY = auxpoints[auxpoints.length - 1];
				}
				for(int j=0; j<points.length; j+=6){
					oval = new RectF(points[j] + antX, points[j+1] + antY, points[j+2] + antX, points[j+3] + antY); 
					path.addArc(oval, points[j+4], points[j+5]);
				}
				break;
			case 'A':
				for(int j=0; j<points.length; j+=6){
					oval = new RectF(points[j], points[j+1], points[j+2], points[j+3]); 
					path.addArc(oval, points[j+4], points[j+5]);
				}
				break;	
			case 'Z':
			case 'z':
				
				if (last_z == 0 || subPath[last_z].getType() == 'M'){
					points = subPath[last_z].getPoints();
					path.lineTo(points[0], points[1]);
					last_z = i+1;
				}else{
					if((i<subPath.length - 1 && subPath[i+1].getType() != 'm') || i==subPath.length-1){
						points = subPath[last_z].getPoints();
						path.rMoveTo(points[0], points[1]);
						path.lineTo(points[0], points[1]);
						last_z = i+1;
					}
						
				}
				
				
				
				break;
				//TODO
			}
			
		}
		
		if(Z){
			points = subPath[0].getPoints();
			path.lineTo(points[0], points[1]);
		}
		canvas.drawPath(path, paintBorder);
		canvas.drawPath(path, paint);
		
		canvas.restore();
	}
	
}
