package geometry;

/**
 * A basic rectangular shape with a point and size. This class is used for
 * bounding boxes of other shapes and works as a basic collision hit box.
 * 
 * @author Robert Jordan
 * 
 * @see
 * {@linkplain Vector},
 * {@linkplain Shape}
 */
public class Ellipse {

	// ======================= Members ========================
	
	/** The corner of the ellipse. */
	public Vector point;
	/** The size of the ellipse from the point. */
	public Vector size;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an ellipse with a point and size
	 * of (0, 0).
	 * 
	 * @return	Returns the default ellipse with a point and size of (0, 0).
	 */
	public Ellipse() {
		this.point = new Vector();
		this.size  = new Vector();
	}
	/**
	 * Constructs an ellipse with the specified dimensions.
	 * 
	 * @param	x - The x coordinate of the ellipse.
	 * @param	y - The y coordinate of the ellipse.
	 * @param	w - The width of the ellipse.
	 * @param	h - The height of the ellipse.
	 * @return	Returns an ellipse with the given dimensions.
	 */
	public Ellipse(double x, double y, double w, double h) {
		this.point = new Vector(x, y);
		this.size  = new Vector(w, h);
	}
	/**
	 * Constructs an ellipse with the specified dimensions.
	 * 
	 * @param	point - The point of the ellipse.
	 * @param	size - The size of the ellipse.
	 * @return	Returns an ellipse with the given dimensions.
	 */
	public Ellipse(Vector point, Vector size) {
		this.point = point;
		this.size  = size;
	}
	/**
	 * Constructs an ellipse with the same point and size as the given one.
	 * 
	 * @param	e - The ellipse to copy.
	 * @return	Returns an ellipse that is identical to the given one.
	 */
	public Ellipse(Ellipse e) {
		this.point = e.point;
		this.size  = e.size;
	}

	// ==================== Shape Details =====================
	
	/**
	 * Returns the minimum point of the shape.
	 * 
	 * @return	Returns the minimum point of the shape.
	 */
	public Vector min() {
		return Vector.min(point, point.plus(size));
	}
	/**
	 * Returns the maximum point of the shape.
	 * 
	 * @return	Returns the maximum point of the shape.
	 */
	public Vector max() {
		return Vector.max(point, point.plus(size));
	}
	/**
	 * Calculates and returns the center of the shape.
	 * 
	 * @return	The position of the center of the shape.
	 */
	public Vector getCenter() {
		return point.plus(size.scaledBy(0.5));
	}
	/**
	 * Calculates and returns the bounding box of the shape.
	 * 
	 * @return	Returns the rectangle of the bounding box of the shape.
	 */
	public Rectangle getBounds() {
		return new Rectangle(min(), max().minus(min()));
	}
	
	// ================== Shape Modification ==================

	/**
	 * Returns the shape translated by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns a translated shape.
	 */
	public Ellipse getTranslation(Vector v) {
		return new Ellipse(this).translate(v);
	}
	/**
	 * Translates the shape by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns the shape after it has been translated.
	 */
	public Ellipse translate(Vector v) {
		point.add(v);
		return this;
	}
	/**
	 * Returns the scaled shape.
	 * 
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Ellipse getScaled(double scale) {
		return new Ellipse(this).scale(scale);
	}
	/**
	 * Returns the shape scaled around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Ellipse getScaled(Vector anchor, double scale) {
		return new Ellipse(this).scale(anchor, scale);
	}
	/**
	 * Scales the shape by the specified size.
	 * 
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Ellipse scale(double scale) {
		point.scale(scale);
		size.scale(scale);
		return this;
	}
	/**
	 * Scales the shape by the specified size around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Ellipse scale(Vector anchor, double scale) {
		point.sub(anchor);
		point.scale(scale);
		point.add(anchor);
		size.scale(scale);
		return this;
	}
	/**
	 * Returns the rotated shape.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The changed rotation of the shape
	 * @return	Returns a rotated shape.
	 */
	public Ellipse getRotation(Vector anchor, double theta) {
		return null;
	}
	/**
	 * Rotates the shape by the specified angle around the anchor.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The new rotation of the shape
	 * @return	Returns the shape after it has been rotated.
	 */
	public Ellipse rotate(Vector anchor, double theta) {
		return null;
	}

	// ==================== Contains Shape ====================
	
	/**
	 * Tests if the specified point is inside the boundary of the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is inside the boundary of the shape.
	 */
	public boolean contains(Vector v) {
		return false;
	}
	/**
	 * Tests if the specified line is inside the boundary of the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line is inside the boundary of the shape.
	 */
	public boolean contains(Line l) {
		return false;
	}
	/**
	 * Tests if the specified rectangle is inside the boundary of the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle is inside the boundary of the shape.
	 */
	public boolean contains(Rectangle r) {
		return false;
	}

	// =================== Intersects Shape ===================
	
	/**
	 * Tests if the specified point is on the boundary of the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is on the boundary of the shape.
	 */
	public boolean intersects(Vector v) {
		return false;
	}
	/**
	 * Tests if the specified line intersects the boundary of the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line intersects the boundary of the shape.
	 */
	public boolean intersects(Line l) {
		return false;
	}
	/**
	 * Tests if the specified rectangle intersects the boundary of the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle intersects the boundary of the shape.
	 */
	public boolean intersects(Rectangle r) {
		return false;
	}
	
	// ==================== Touching Shape ====================

	/**
	 * Tests if the specified point is colliding with the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is colliding with the shape.
	 */
	public boolean colliding(Vector v) {
		return false;
	}
	/**
	 * Tests if the specified line is colliding with the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line is colliding with the shape.
	 */
	public boolean colliding(Line l) {
		return false;
	}
	/**
	 * Tests if the specified rectangle is colliding with the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle is colliding with the shape.
	 */
	public boolean colliding(Rectangle r) {
		return false;
	}
	
	// ================ Java Awt Compatibility ================
	
	/**
	 * Creates and returns the Java Awt Shape of the shape.
	 * This function is used for compatibility with java.awt.geom shapes.
	 * 
	 * @return	The Java Awt Shape of the shape.
	 * 
	 * @see
	 * {@linkplain java.awt.Shape},
	 * {@linkplain java.awt.Rectangle2D.Double}
	 */
	public java.awt.geom.Ellipse2D.Double getShape() {
		return new java.awt.geom.Ellipse2D.Double(point.x, point.y, size.x, size.y);
	}
	
}


