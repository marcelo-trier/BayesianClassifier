package br.bayclass.util;

public class ConversorCores {

	public static float getMax(float f1, float f2, float f3) {
		float valor = 0;
		if (f1 > f2 && f1 > f3)
			valor = f1;
		if (f2 > f1 && f2 > f3)
			valor = f2;
		if (f3 > f2 && f3 > f1)
			valor = f3;
		return valor;
	}

	public static float getMin(float f1, float f2, float f3) {
		float valor = 0;
		if (f1 < f2 && f1 < f3)
			valor = f1;
		if (f2 < f3 && f2 < f1)
			valor = f2;
		if (f3 < f2 && f3 < f1)
			valor = f3;
		return valor;
	}
	
	public static float[] rgb2hsl( int[] rgb, float[] result ) {
		if( result == null )
			result = new float[ rgb.length ];

		float r, g, b;
		r = (float)rgb[0] / 255f;
		g = (float)rgb[1] / 255f;
		b = (float)rgb[2] / 255f;
		float max = getMax(r, g, b);
		float min = getMin(r, g, b);
		result[0] = result[1] = result[2] = 0;
		result[2] = (max + min) / 2;
		float d = max - min;

		if (d == 0)
			return result;

		if ( result[2] > 0.5f)
			result[1] = d / (2 - max - min);
		else
			result[1] = d / (max + min);

		float tmp = 0;
		if( max == r )
			 tmp = (g - b) / d + (g < b ? 6 : 0);
		if( max == g )
			tmp = (b - r) / d + 2;
		if( max == b )
			result[0] = (r - g) / d + 4;

		tmp /= 6;
		result[0] = tmp;

		return result;
	}

	public int[] hsv2rgb( int[] rgb ) {
		return null;
	}
	
}
