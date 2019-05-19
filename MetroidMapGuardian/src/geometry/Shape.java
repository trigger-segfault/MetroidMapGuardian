package geometry;

/**
 * The interface used to implement geometric figures that use
 * advanced collision detection and response, as well as drawing.
 * 
 * @author	Robert Jordan
 * 
 * @see
 * {@linkplain Vector},
 * {@linkplain Line},
 * {@linkplain Rectangle},
 * {@linkplain java.awt.Shape}
 */
public interface Shape {

	// ==================== Shape Details =====================
	
	/**
	 * Calculates and returns the center of the shape.
	 * 
	 * @return	Returns the position of the center of the shape.
	 */
	public Vector getCenter();
	/**
	 * Calculates and returns the bounding box of the shape.
	 * 
	 * @return	Returns the rectangle of the bounding box of the shape.
	 */
	public Rectangle getBounds();

	// ================== Shape Modification ==================
	
	/**
	 * Returns the shape translated by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns a translated shape.
	 */
	public Shape getTranslation(Vector v);
	/**
	 * Translates the shape by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns the shape after it has been translated.
	 */
	public Shape translate(Vector v);
	/**
	 * Returns the scaled shape.
	 * 
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Shape getScaled(double scale);
	/**
	 * Returns the shape scaled around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Shape getScaled(Vector anchor, double scale);
	/**
	 * Scales the shape by the specified size.
	 * 
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Shape scale(double scale);
	/**
	 * Scales the shape by the specified size around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Shape scale(Vector anchor, double scale);
	/**
	 * Returns the rotated shape.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The changed rotation of the shape
	 * @return	Returns a rotated shape.
	 */
	public Shape getRotation(Vector anchor, double theta);
	/**
	 * Rotates the shape by the specified angle around the anchor.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The new rotation of the shape
	 * @return	Returns the shape after it has been rotated.
	 */
	public Shape rotate(Vector anchor, double theta);

	// ==================== Contains Shape ====================

	/**
	 * Tests if the specified point is inside the boundary of the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is inside the boundary of the shape.
	 */
	public boolean contains(Vector v);
	/**
	 * Tests if the specified line is inside the boundary of the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line is inside the boundary of the shape.
	 */
	public boolean contains(Line l);
	/**
	 * Tests if the specified rectangle is inside the boundary of the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle is inside the boundary of the shape.
	 */
	public boolean contains(Rectangle r);

	// =================== Intersects Shape ===================

	/**
	 * Tests if the specified point is on the boundary of the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is on the boundary of the shape.
	 */
	public boolean intersects(Vector v);
	/**
	 * Tests if the specified line intersects the boundary of the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line intersects the boundary of the shape.
	 */
	public boolean intersects(Line l);
	/**
	 * Tests if the specified rectangle intersects the boundary of the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle intersects the boundary of the shape.
	 */
	public boolean intersects(Rectangle r);
	
	// ==================== Touching Shape ====================

	/**
	 * Tests if the specified point is colliding with the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is colliding with the shape.
	 */
	public boolean colliding(Vector v);
	/**
	 * Tests if the specified line is colliding with the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line is colliding with the shape.
	 */
	public boolean colliding(Line l);
	/**
	 * Tests if the specified rectangle is colliding with the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle is colliding with the shape.
	 */
	public boolean colliding(Rectangle r);
	
	// ================ Java Awt Compatibility ================
	
	/**
	 * Creates and returns the Java Awt Shape of the shape.
	 * This function is used for compatibility with java.awt.geom shapes.
	 * 
	 * @return	The Java Awt Shape of the shape.
	 * 
	 * @see
	 * {@link java.awt.Shape}
	 */
	public java.awt.Shape getShape();
	
}
