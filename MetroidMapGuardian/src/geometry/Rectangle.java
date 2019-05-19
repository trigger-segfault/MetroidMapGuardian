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
public class Rectangle {

	// ======================= Members ========================
	
	/** The corner of the rectangle. */
	public Vector point;
	/** The size of the rectangle from the point. */
	public Vector size;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a rectangle
	 * with a point and size of (0, 0).
	 * 
	 * @return	Returns the default rectangle with a point and size of (0, 0).
	 */
	public Rectangle() {
		this.point = new Vector();
		this.size  = new Vector();
	}
	/**
	 * Constructs a rectangle with the specified dimensions.
	 * 
	 * @param	x - The x coordinate of the rectangle.
	 * @param	y - The y coordinate of the rectangle.
	 * @param	w - The width of the rectangle.
	 * @param	h - The height of the rectangle.
	 * @return	Returns a rectangle with the given dimensions.
	 */
	public Rectangle(double x, double y, double w, double h) {
		this.point = new Vector(x, y);
		this.size  = new Vector(w, h);
	}
	/**
	 * Constructs a rectangle with the specified dimensions.
	 * 
	 * @param	point - The point of the rectangle.
	 * @param	size - The size of the rectangle.
	 * @return	Returns a rectangle with the given dimensions.
	 */
	public Rectangle(Vector point, Vector size) {
		this.point = new Vector(point);
		this.size  = new Vector(size);
	}
	/**
	 * Constructs a rectangle with the same point and size as the given one.
	 * 
	 * @param	r - The rectangle to copy.
	 * @return	Returns a rectangle that is identical to the given one.
	 */
	public Rectangle(Rectangle r) {
		this.point = new Vector(r.point);
		this.size  = new Vector(r.size);
	}

	// ==================== Shape Details =====================

	/**
	 * Returns one of the shape's points.
	 * 
	 * @param	index - The index of the point. This can be between 0 and 3.
	 * @return	Returns the point of the specified index.
	 */
	public Vector getPoint(int index) {
		switch (index) {
		case 0: return new Vector(point.plus(0, 0));
		case 1: return new Vector(point.plus(size.x, 0));
		case 2: return new Vector(point.plus(size));
		case 3: return new Vector(point.plus(0, size.y));
		}
		return null;
	}
	/**
	 * Returns one of the shape's lines.
	 * 
	 * @param	index - The index of the line. This can be between 0 and 3.
	 * @return	Returns the line of the specified index.
	 */
	public Line getLine(int index) {
		switch (index) {
		case 0: return new Line(point.plus(0, 0),		point.plus(size.x, 0));
		case 1: return new Line(point.plus(size.x, 0),	point.plus(size));
		case 2: return new Line(point.plus(size),		point.plus(0, size.y));
		case 3: return new Line(point.plus(0, size.y),	point.plus(0, 0));
		}
		return null;
	}
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
	 * Creates a polygon with the same points as this line.
	 * 
	 * @return	Returns the polygon shape that represents this line.
	 */
	public Polygon getPolygon() {
		return new Polygon(getPoint(0), getPoint(1), getPoint(2), getPoint(3));
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
	public Rectangle getTranslation(Vector v) {
		return new Rectangle(this).translate(v);
	}
	/**
	 * Translates the shape by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns the shape after it has been translated.
	 */
	public Rectangle translate(Vector v) {
		point.add(v);
		return this;
	}
	/**
	 * Returns the scaled shape.
	 * 
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Rectangle getScaled(double scale) {
		return new Rectangle(this).scale(scale);
	}
	/**
	 * Returns the shape scaled around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Rectangle getScaled(Vector anchor, double scale) {
		return new Rectangle(this).scale(anchor, scale);
	}
	/**
	 * Scales the shape by the specified size.
	 * 
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Rectangle scale(double scale) {
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
	public Rectangle scale(Vector anchor, double scale) {
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
	public Polygon getRotation(Vector anchor, double theta) {
		return getPolygon().rotate(anchor, theta);
	}
	/**
	 * Rotates the shape by the specified angle around the anchor.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The new rotation of the shape
	 * @return	Returns the shape after it has been rotated.
	 */
	public Rectangle rotate(Vector anchor, double theta) {
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
		return (v.greaterThanOrEqual(min()) && v.lessThanOrEqual(max()));
	}
	/**
	 * Tests if the specified line is inside the boundary of the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line is inside the boundary of the shape.
	 */
	public boolean contains(Line l) {
		return (l.min().greaterThanOrEqual(min()) && l.max().lessThanOrEqual(max()));
	}
	/**
	 * Tests if the specified rectangle is inside the boundary of the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle is inside the boundary of the shape.
	 */
	public boolean contains(Rectangle r) {
		return (r.min().greaterThanOrEqual(min()) && r.max().lessThanOrEqual(max()));
	}

	// =================== Intersects Shape ===================
	
	/**
	 * Tests if the specified point is on the boundary of the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is on the boundary of the shape.
	 */
	public boolean intersects(Vector v) {
		if ((v.x == point.x || v.x == point.x + size.x) &&
			(v.y >= min().y && v.y <= max().y)) {
			return true;
		}
		if ((v.y == point.y || v.y == point.y + size.y) &&
				(v.x >= min().x && v.x <= max().x)) {
			return true;
		}
		return false;
	}
	/**
	 * Tests if the specified line intersects the boundary of the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line intersects the boundary of the shape.
	 */
	public boolean intersects(Line l) {
		for (int i = 0; i < 4; i++) {
			if (l.intersects(getLine(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Tests if the specified rectangle intersects the boundary of the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle intersects the boundary of the shape.
	 */
	public boolean intersects(Rectangle r) {
		return (r.min().greaterThanOrEqual(min()) || r.max().lessThanOrEqual(max()));
	}
	
	// ==================== Touching Shape ====================

	/**
	 * Tests if the specified point is colliding with the shape.
	 * 
	 * @param	v - The Vector of the point.
	 * @return	Returns true if the point is colliding with the shape.
	 */
	public boolean colliding(Vector v) {
		return (v.greaterThanOrEqual(min()) && v.lessThanOrEqual(max()));
	}
	/**
	 * Tests if the specified line is colliding with the shape.
	 * 
	 * @param	l - The line.
	 * @return	Returns true if the line is colliding with the shape.
	 */
	public boolean colliding(Line l) {
		return (l.min().greaterThanOrEqual(min()) && l.max().lessThanOrEqual(max()));
	}
	/**
	 * Tests if the specified rectangle is colliding with the shape.
	 * 
	 * @param	r - The rectangle.
	 * @return	Returns true if the rectangle is colliding with the shape.
	 */
	public boolean colliding(Rectangle r) {
		return (r.max().greaterThanOrEqual(min()) && r.min().lessThanOrEqual(max()));
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
	public java.awt.geom.Rectangle2D.Double getShape() {
		return new java.awt.geom.Rectangle2D.Double(point.x, point.y, size.x, size.y);
	}
}
