package ie.scifest.pacman.util;

public class Mth {
	public static double mod(double numerator, double denominator) {
		return (((numerator % denominator) + denominator) % denominator);
	}
	public static float mod(float numerator, float denominator) {
		return (float)mod((double)numerator, (double)denominator);//(((numerator % denominator) + denominator) % denominator);
	}
	public static int mod(int numerator, int denominator) {
		return (int)mod((double)numerator, (double)denominator);//(((numerator % denominator) + denominator) % denominator);
	}
	public static int dist(int x1, int y1, int x2, int y2) {
		double _x = Math.pow(x1-x2, 2);
		double _y = Math.pow(y1-y2, 2);
		return (int)Math.round(Math.sqrt(_x+_y));
	}
	public static double clamp(double n, double min, double max) {
		return n < min ? min : (n > max ? max : n);
	}
	public static float clamp(float n, float min, float max) {
		return (float)clamp((double)n,(double)min,(double)max);
	}
	public static int clamp(int n, int min, int max) {
		return (int)clamp((double)n,(double)min,(double)max);
	}
}
