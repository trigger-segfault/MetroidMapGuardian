package geometry;

import java.text.DecimalFormat;

/**
 * A class that represents a 2D point with an x and y coordinate.
 * Vectors are used in trigonometric functions and collision physics.
 * This is the core point class used for all shapes.
 * 
 * @author	Robert Jordan
 * @author	David Jordan
 * 
 * @see
 * {@linkplain Shape},
 * {@linkplain GMath}
 */
public class Vector {

	// ====================== Constants =======================
	
	/** The origin point of (0, 0). */
	public static final Vector ORIGIN = new Vector(0.0, 0.0);

	// ======================= Members ========================
	
	/** The x coordinate of the vector. */
	public double	x;
	/** The y coordinate of the vector. */
	public double	y;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a vector positioned at the origin.
	 * 
	 * @return	Returns a vector at the point (0, 0).
	 */
	public Vector() {
		this.x = 0.0;
		this.y = 0.0;
	}
	/**
	 * Constructs a vector positioned at the specified x and y coordinate.
	 * 
	 * @param	x - The x coordinate.
	 * @param	y - The y coordinate.
	 * @return	Returns a vector at the point (x, y).
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructs a vector positioned at the specified polar coordinates.
	 * 
	 * @param	length - The length or x coordinate.
	 * @param	theta - The angle or y coordinate.
	 * @param	polar - Set to true to use polar coordinates, otherwise xy
	 * coordinates are used.
	 * @return	Returns a vector with the length and angle or at the point (x, y).
	 */
	public Vector(double length, double theta, boolean polar) {
		if (polar) {
			this.x	= length * Math.cos(theta);
			this.y	= length * Math.sin(theta);
		}
		else {
			this.x	= length;
			this.y	= theta;
		}
	}
	/**
	 * Constructs a vector positioned at the specified vector position.
	 * 
	 * @param	v - The vector position.
	 * @return	Returns a vector at the same point as the specified vector.
	 */
	public Vector(Vector v) {
		this.x	= v.x;
		this.y	= v.y;
	}
	/**
	 * Constructs a vector of the distance from v1 to v2.
	 * 
	 * @param	v1 - The first vector.
	 * @param	v2 - The second vector.
	 * @return	Returns a vector of the distance from v1 to v2.
	 */
	public Vector(Vector v1, Vector v2) {
		this.x	= v2.x - v1.x;
		this.y	= v2.y - v1.y;
	}

	// ======================= General ========================
	
	/**
	 * Returns the vector in the form of a string as (x, y).
	 * 
	 * @return	Returns a string representing the vector's x and y value.
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	/**
	 * Returns the vector in the form of a string with the specified format
	 * for the double values.
	 * 
	 * @param	format - The format to display the x and y values in.
	 * @return	Returns a string representing the vector's x and y value
	 * with the specified format.
	 */
	public String toString(DecimalFormat format) {
		return "(" + format.format(x) + ", " + format.format(y) + ")";
	}
	/**
	 * Tests whether the vector is equal to the specified vector.
	 * Returns false if the specified vector is null.
	 * 
	 * @param	obj - The vector to compare.
	 * @return	Returns true if the vectors have the same x and y values.
	 */
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		return (this.x == ((Vector)obj).x) && (this.y == ((Vector)obj).y);
	}
	/**
	 * Tests whether the vector is greater than the specified vector.
	 * Returns false if the specified vector is null.
	 * 
	 * @param	v - The vector to compare.
	 * @return	Returns true if the vector's values are greater than
	 * the other vector's values.
	 */
	public boolean greaterThan(Vector v) {
		if (v == null)
			return false;
		
		return (this.x > v.x) && (this.y > v.y);
	}
	/**
	 * Tests whether the vector is greater than or equal to the specified vector.
	 * Returns false if the specified vector is null.
	 * 
	 * @param	v - The vector to compare.
	 * @return	Returns true if the vector's values are greater than or equal
	 * to the other vector's values.
	 */
	public boolean greaterThanOrEqual(Vector v) {
		if (v == null)
			return false;
		
		return (this.x >= v.x) && (this.y >= v.y);
	}
	/**
	 * Tests whether the vector is less than the specified vector.
	 * Returns false if the specified vector is null.
	 * 
	 * @param	v - The vector to compare.
	 * @return	Returns true if the vector's values are less than the other
	 * vector's values.
	 */
	public boolean lessThan(Vector v) {
		if (v == null)
			return false;
		
		return (this.x < v.x) && (this.y < v.y);
	}
	/**
	 * Tests whether the vector is less than or equal to the specified vector.
	 * Returns false if the specified vector is null.
	 * 
	 * @param	v - The vector to compare.
	 * @return	Returns true if the vector's values are less than or equal to
	 * the other vector's values.
	 */
	public boolean lessThanOrEqual(Vector v) {
		if (v == null)
			return false;
		
		return (this.x <= v.x) && (this.y <= v.y);
	}
	
	// ====================== Arithmetic ======================
	
	/**
	 * Returns the vector plus the x and y distance.
	 * 
	 * @param	x - The x distance to add.
	 * @param	y - The y distance to add.
	 * @return	Returns a vector with the added distance.
	 */
	public Vector plus(double x, double y) {
		return new Vector(this.x + x, this.y + y);
	}
	/**
	 * Returns the vector plus the vector distance.
	 * 
	 * @param	v - The vector of the distance to add.
	 * @return	Returns a vector with the added distance.
	 */
	public Vector plus(Vector v) {
		return new Vector(this.x + v.x, this.y + v.y);
	}
	/**
	 * Returns the vector minus the x and y distance.
	 * 
	 * @param	x - The x distance to subtract.
	 * @param	y - The y distance to subtract.
	 * @return	Returns a vector with the subtracted distance.
	 */
	public Vector minus(double x, double y) {
		return new Vector(this.x - x, this.y - y);
	}
	/**
	 * Returns the vector minus the vector distance.
	 * 
	 * @param	v - The vector of the distance to subtract.
	 * @return	Returns a vector with the subtracted distance.
	 */
	public Vector minus(Vector v) {
		return new Vector(this.x - v.x, this.y - v.y);
	}
	/**
	 * Returns the vector multiplied by the scale.
	 * 
	 * @param	scale - The scale to multiply the vector by.
	 * @return	Returns a vector with the values multiplied by the scale.
	 */
	public Vector scaledBy(double scale) {
		return new Vector(this.x * scale, this.y * scale);
	}
	/**
	 * Returns the inverse of the vector.
	 * 
	 * @return	Returns a vector with the inverse values.
	 */
	public Vector inverse() {
		return new Vector(-this.x, -this.y);
	}
	/**
	 * Returns the length of the vector from the origin.
	 * 
	 * @return	Returns the vector's length.
	 */
	public double length() {
		return Math.sqrt((x * x) + (y * y));
	}
	/**
	 * Returns the direction of the vector with an angle of zero being (1, 0).
	 * 
	 * @return	Returns the vector's direction.
	 */
	public double direction() {
		if (x == 0.0 && y == 0.0)
			return 0.0;
		
		return Math.atan2(y, x);
	}
	/**
	 * Returns the distance from this vector to the specified vector.
	 * 
	 * @param	v - The vector to get the distance to.
	 * @return	Returns the distance to the specified vector.
	 */
	public double distanceTo(Vector v) {
		return v.minus(this).length();
	}
	/**
	 * Returns the angle between this vector and another.
	 * 
	 * @param	v - The vector to get the distance to.
	 * @return	Returns the angle between the specified vector's direction.
	 */
	public double angleBetween(Vector v) {
		return direction() - v.direction();
	}
	/**
	 * Returns the dot product of this vector and another.
	 * 
	 * @param	v - The vector to multiply by.
	 * @return	Returns the dot product of the two vectors.
	 */
	public double dot(Vector v) {
		return (this.x * v.x) + (this.y * v.y);
	}
	/**
	 * Returns the scalar projection on this vector with an angle.
	 * 
	 * @param	theta - The angle used.
	 * @return	Returns the scalar projection on this vector and an angle.
	 */
	public double scalarProjection(double theta) {
		return (length() * Math.cos(direction() - theta));
	}
	/**
	 * Returns the scalar projection on this vector with another.
	 * 
	 * @param	v - The vector whose angle is used.
	 * @return	Returns the scalar projection on this vector with another's angle.
	 */
	public double scalarProjection(Vector v) {
		return scalarProjection(v.direction());
	}
	/**
	 * Returns the normalized vector.
	 * 
	 * @return	Returns the normalized vector.
	 */
	public Vector normalized() {
		double len = length();
		if (len > 0) {
    		return new Vector(x / len, y / len);
		}
		return new Vector();
	}
	/**
	 * Returns a vector with the same direction but a set length.
	 * 
	 * @param	length - The new length to use.
	 * @return	Returns the vector with a new length.
	 */
	public Vector lengthVector(double length) {
		return new Vector(this).setLength(length);
	}
	/**
	 * Returns a vector with the same length but a set direction.
	 * 
	 * @param	theta - The new angle to use.
	 * @return	Returns the vector with a new direction.
	 */
	public Vector directionVector(double theta) {
		return new Vector(this).setDirection(theta);
	}
	/**
	 * Returns the projection of this vector on the angle.
	 * 
	 * @param	theta - The angle used.
	 * @return	Returns the vector's projection with the specified angle.
	 */
	public Vector projectionOn(double theta) {
		return new Vector(scalarProjection(theta), theta, true);
	}
	/**
	 * Returns the projection of this vector on the specified vector.
	 * 
	 * @param	v - The vector whose angle is used.
	 * @return	Returns the vector's projection with the specified vector's angle.
	 */
	public Vector projectionOn(Vector v) {
		return v.lengthVector(scalarProjection(v));
	}
	/**
	 * Returns the rejection of this vector on the specified angle.
	 * 
	 * @param	theta - The angle used.
	 * @return	Returns the vector's rejection with the specified angle.
	 */
	public Vector rejectionOn(double theta) {
		return new Vector(projectionOn(theta), this);
	}
	/**
	 * Returns the rejection of this vector on the specified vector.
	 * 
	 * @param	v - The vector whose angle is used.
	 * @return	Returns the vector's rejection with the specified vector's angle.
	 */
	public Vector rejectionOn(Vector v) {
		return new Vector(projectionOn(v), this);
	}
	
	// ================= Modified Arithmetic ==================
	
	/**
	 * Adds the x and y distance to the vector.
	 * 
	 * @param	x - The x distance to add.
	 * @param	y - The y distance to add.
	 * @return	Returns the vector with the added distance.
	 */
	public Vector add(double x, double y) {
		this.x	+= x;
		this.y	+= y;
		return this;
	}
	/**
	 * Adds the vector distance to the vector.
	 * 
	 * @param	v - The vector of the distance to add.
	 * @return	Returns the vector with the added distance.
	 */
	public Vector add(Vector v) {
		this.x	+= v.x;
		this.y	+= v.y;
		return this;
	}
	/**
	 * Subtracts the x and y distance from the vector.
	 * 
	 * @param	x - The x distance to subtract.
	 * @param	y - The y distance to subtract.
	 * @return	Returns the vector with the subtracted distance.
	 */
	public Vector sub(double x, double y) {
		this.x	-= x;
		this.y	-= y;
		return this;
	}
	/**
	 * Subtracts the vector distance from the vector.
	 * 
	 * @param	v - The vector of the distance to subtract.
	 * @return	Returns the vector with the subtracted distance.
	 */
	public Vector sub(Vector v) {
		this.x	-= v.x;
		this.y	-= v.y;
		return this;
	}
	/**
	 * Multiplied the vector by the scale.
	 * 
	 * @param	scale - The scale to multiply the vector by.
	 * @return	Returns a new scaled vector.
	 */
	public Vector scale(double scale) {
		this.x *= scale;
		this.y *= scale;
		return this;
	}
	/**
	 * Negates the vector.
	 * 
	 * @return	Returns the new negated vector.
	 */
	public Vector negate() {
		this.x = -x;
		this.y = -y;
		return this;
	}
	/**
	 * Sets the vector to (0, 0).
	 * 
	 * @return	Returns the new vector at the position (0, 0).
	 */
	public Vector zero() {
		this.x	= 0.0;
		this.y	= 0.0;
		return this;
	}
	/**
	 * Normalizes the vector.
	 * 
	 * @return	Returns the new normalized vector.
	 */
	public Vector normalize() {
		double len = length();
		if (len > 0) {
    		this.x /= len;
    		this.y /= len;
		}
		return this;
	}
	/**
	 * Sets the vector to the new position.
	 * 
	 * @param	x - The new x coordinate.
	 * @param	y - The new y coordinate.
	 * @return	Returns the new vector with the modified position.
	 */
	public Vector set(double x, double y) {
		this.x	= x;
		this.y	= y;
		return this;
	}
	/**
	 * Sets the vector to the new position.
	 * 
	 * @param	v - The new vector value.
	 * @return	Returns the new vector with the modified position.
	 */
	public Vector set(Vector v) {
		this.x	= v.x;
		this.y	= v.y;
		return this;
	}
	/**
	 * Sets the vector to the new length and direction.
	 * 
	 * @param	length - The new length of the vector.
	 * @param	theta - The new angle of the vector.
	 * @return	Returns the new vector with the modified length and direction.
	 */
	public Vector setPolar(double length, double theta) {
		this.x = length * Math.cos(theta);
		this.y = length * Math.sin(theta);
		return this;
	}
	/**
	 * Sets the vector to have the new length but same direction.
	 * 
	 * @param	length - The new length of the vector.
	 * @return	Returns the new vector with the modified length.
	 */
	public Vector setLength(double length) {
		double direction = direction();
		this.x = length * Math.cos(direction);
		this.y = length * Math.sin(direction);
		return this;
	}
	/**
	 * Sets the vector to have the new direction but same length.
	 * 
	 * @param	theta - The new angle of the vector.
	 * @return	Returns the new vector with the modified direction.
	 */
	public Vector setDirection(double theta) {
		double length = length();
		this.x = length * Math.cos(theta);
		this.y = length * Math.sin(theta);
		return this;
	}
	

	/**
	 * Returns the minimum x and y coordinate from the two vectors.
	 * 
	 * @param	v1 - The first vector.
	 * @param	v2 - The second vector.
	 * @return	Returns minimum vector coordinates.
	 */
	public static Vector min(Vector v1, Vector v2) {
		return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
	}
	/**
	 * Returns the maximum x and y coordinate from the two vectors.
	 * 
	 * @param	v1 - The first vector.
	 * @param	v2 - The second vector.
	 * @return	Returns maximum vector coordinates.
	 */
	public static Vector max(Vector v1, Vector v2) {
		return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y));
	}
}