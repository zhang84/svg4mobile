package com.grub.svg4mobile;

public class SubPath {
	
	private float[] points;
	private char type;
	
	public SubPath(char type, float[] points)
	{
		this.points = new float[points.length];
		
		for(int i =0; i<points.length; i++)
			this.points[i] = points[i];
		this.type = type;
	}

	public float[] getPoints() {
		return points;
	}

	public char getType() {
		return type;
	}
	

}
