package com.grub.svg4mobile;

public class MultMatrixAdapter implements MultMatrix{
	/**
	 * Adaptador de multiplicación de matrices en formato array a formato bidimensional. Implementación del patrón adapter. 
	 * @param m1
	 * @param m2
	 * @return
	 */	
	public static float[] multiplyMatrix(float[] m1, float[] m2) {
		return matrix2array(MultMatrixMxN.multiplyMatrix(array2matrix(m1),array2matrix(m2)));
	}
	
	/**
	 * Convierte una Matriz de 2 dimensiones en un array.
	 * @param m
	 * @return 
	 */
	private static float[] matrix2array(float[][] m) {
		int mrows = m.length;
		int mcols = m[0].length;
		int k=0;
		float[] result = new float[mrows*mcols];
		for (int i=0; i<mrows; i++)
			for (int j=0; j<mcols; j++) 
				result[k++]=m[i][j];

		return result;
	}
	
	/**
	 * Convierte un array de 9 elementos a una Matriz 3x3.
	 * @param m Array de 9 elementos
	 * @return Devuelve una matriz 3x3
	 */
	private static float[][] array2matrix(float[] m) {
		int k=0;
		float[][] result = new float[3][3];
		for (int i=0; i<3; i++)
			for (int j=0; j<3; j++) 
				result[i][j]=m[k++];

		return result;
	}
}
