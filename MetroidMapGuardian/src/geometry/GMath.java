package geometry;

import java.util.Random;

/**
 * A class that encapsulates many improved versions of static functions from Math.
 * These functions are made for use in game calculations.
 * 
 * @author	Robert Jordan
 * @author	David Jordan
 *
 * @see
 * {@linkplain Vector}
 */
public class GMath {
	
	// ====================== Constants =======================

	/** Euler's constant. */
	public static final double E					= Math.E;
	/** The constant variable used in radian and circle calculations. */
	public static final double PI					= Math.PI;
	/** The value of PI times 2. */
	public static final double TWO_PI				= 2.0 * PI;
	/** The inverse value of PI, or 1 divided by PI. */
	public static final double INV_PI				= 1.0 / PI;
	/** The value of PI divided by 2. */
	public static final double HALF_PI				= PI / 2.0;
	/** The value of PI divided by 4. */
	public static final double QUARTER_PI			= PI / 4.0;
	/** The value of PI times 3/2. */
	public static final double THREE_HALVES_PI		= TWO_PI - HALF_PI;
	/** The value of the square root of PI. */
	public static final double ROOT_PI				= Math.sqrt(PI);
	/**
	 * The epsilon value used in double floating precision calculations.
	 * Values are considered equal if they are in range of each other
	 * within this epsilon.
	 * @see
	 *{@linkplain #inRange(double a, double b, double epsilon)}
	 */
	public static final double EPSILON				= 1.1920928955078125E-7;

	/** 
	 * This variable is true if the angles should be in degrees. Otherwise
	 * angles will be in radians.
	 * @see
	 * {@linkplain #FULL_ANGLE},
	 * {@linkplain #THREE_FOURTHS_ANGLE},
	 * {@linkplain #HALF_ANGLE},
	 * {@linkplain #QUARTER_ANGLE}
	 */
	public static final boolean USE_DEGREES			= true;
	
	/**
	 * The full angle value based on the USE_DEGREES constant.
	 * @see
	 * {@linkplain #USE_DEGREES}
	 */
	public static final double FULL_ANGLE			= (USE_DEGREES ? 360.0 : TWO_PI);
	/**
	 * The three fourths angle value based on the USE_DEGREES constant.
	 * @see
	 * {@linkplain #USE_DEGREES}
	 */
	public static final double THREE_FOURTHS_ANGLE	= (USE_DEGREES ? 270.0 : THREE_HALVES_PI);
	/**
	 * The half angle value based on the USE_DEGREES constant.
	 * @see
	 * {@linkplain #USE_DEGREES}
	 */
	public static final double HALF_ANGLE			= (USE_DEGREES ? 180.0 : PI);
	/**
	 * The quarter angle value based on the USE_DEGREES constant.
	 * @see
	 * {@linkplain #USE_DEGREES}
	 */
	public static final double QUARTER_ANGLE		= (USE_DEGREES ?  90.0 : HALF_PI);
	
	// ====================== Variables =======================
	
	/** The class used to generate random values. */
	public static Random random = new Random();

	// ======================= Doubles ========================
	
	/**
	 * Finds the minimum from the list of values.
	 * 
	 * @param	args - The list of values.
	 * @return	Returns the minimum value from the list.
	 */
	public static double min(double... args) {
		double min = args[0];
		for (int i = 1; i < args.length; i++) {
			if (args[i] < min)
				min = args[i];
		}
		return min;
	}
	/**
	 * Finds the maximum from the list of values.
	 * 
	 * @param	args - The list of values.
	 * @return	Returns the maximum value from the list.
	 */
	public static double max(double... args) {
		double max = args[0];
		for (int i = 1; i < args.length; i++) {
			if (args[i] > max)
				max = args[i];
		}
		return max;
	}
	/**
	 * Tests whether the two values are within epsilon range of each other.
	 * 
	 * @param	a - The first value to compare with.
	 * @param	b - The second value to compare with.
	 * @param	epsilon - The max allowed range between a and b.
	 * @return	Returns true if the two values are within epsilon range of
	 * each other.
	 */
	public static boolean inRange(double a, double b, double epsilon) {
		return Math.abs(b - a) <= epsilon;
	}
	/**
	 * Returns the positive modulus of the value.
	 * 
	 * @param	x - The value to get the modulus of.
	 * @param	mod - The modulus.
	 * @return	Returns the positive modulus of the value.
	 */
	public static double absmod(double x, double mod) {
		double val = x % mod;
		if (val < 0)
			return val + mod;
		return val;
	}
	
	// ======================= Vectors ========================
	
	/**
	 * Finds the minimum x and y values from the given vectors.
	 * 
	 * @param	args - The list of vectors.
	 * @return	Returns the minimum vector coordinates.
	 */
	public static Vector min(Vector... args) {
		Vector min = new Vector(args[0]);
		for (int i = 1; i < args.length; i++) {
			if (args[i].x < min.x)
				min.x = args[i].x;
			if (args[i].y < min.y)
				min.y = args[i].y;
		}
		return min;
	}
	/**
	 * Finds the maximum x and y values from the given vectors.
	 * 
	 * @param	args - The list of vectors.
	 * @return	Returns the maximum vector coordinates.
	 */
	public static Vector max(Vector... args) {
		Vector max = new Vector(args[0]);
		for (int i = 1; i < args.length; i++) {
			if (args[i].x > max.x)
				max.x = args[i].x;
			if (args[i].y > max.y)
				max.y = args[i].y;
		}
		return max;
	}
	
	public static Vector abs(Vector v) {
		return new Vector(Math.abs(v.x), Math.abs(v.y));
	}

	// ===================== Trigonometry =====================
	
	/**
	 * Returns the angle of the value in the range [0, 360).
	 * 
	 * @param	theta - The value to get the proper angle of.
	 * @return	Returns the angle of the value in the range [0, 360).
	 */
	public static double absdir(double theta) {
		return GMath.absmod(theta, FULL_ANGLE);
	}
	/**
	 * Returns the change in direction from the source to the destination.
	 * 
	 * @param	src - The current direction.
	 * @param	dst - The direction to the destination.
	 * @return	Returns the change in direction to the destination.
	 */
	public static double deltaDirection(double src, double dst) {
		double dir = GMath.absdir(dst - src);
		if (dir > HALF_ANGLE)
			dir -= HALF_ANGLE;
		return dir;
	}
	
	/**
	 * Converts the given angle into degrees if USE_DEGREES is true.
	 * 
	 * @param	theta - The angle to convert.
	 * @return	Returns the angle in degrees if USE_DEGREES is true, otherwise
	 * 			in radians.
	 */
	public static double convertRadians(double theta) {
		if (USE_DEGREES)
			return Math.toDegrees(theta);
		else
			return theta;
	}
	/**
	 * Converts the given angle into radians if USE_DEGREES is false.
	 * 
	 * @param	theta - The angle to convert.
	 * @return	Returns the angle in radians if USE_DEGREES is false, otherwise
	 * 			in degrees.
	 */
	public static double convertDegrees(double theta) {
		if (USE_DEGREES)
			return theta;
		else
			return Math.toRadians(theta);
	}
	
}


