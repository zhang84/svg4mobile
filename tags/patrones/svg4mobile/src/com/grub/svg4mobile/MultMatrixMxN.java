package com.grub.svg4mobile;

public class MultMatrixMxN {
	/**
	 * Multiplica dos matrices
	 * @param m1 Matriz M1, un array bidimensional
	 * @param m2 Matriz M2, un array bidimensional
	 * @return Devuelve un array bidimensional con la matriz resultante de M1xM2
	 */
	public static float[][] multiplyMatrix(float[][] m1, float[][] m2) {
		int m1rows = m1.length;
		int m1cols = m1[0].length;
		int m2rows = m2.length;
		int m2cols = m2[0].length;
		if (m1cols != m2rows)
			throw new IllegalArgumentException("matrices don't match: " + m1cols + " != " + m2rows);
		float[][] result = new float[m1rows][m2cols];

		for (int i=0; i<m1rows; i++)
			for (int j=0; j<m2cols; j++)
				for (int k=0; k<m1cols; k++)
					result[i][j] += m1[i][k] * m2[k][j];
		    
		return result;
	}
}
