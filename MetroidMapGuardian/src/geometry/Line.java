package geometry;

/**
 * A line segment that has two end points, This is the basic shape that
 * constructs all polygons.
 * 
 * @author Robert Jordan
 *
 * @see
 * {@linkplain Vector},
 * {@linkplain Shape},
 * {@linkplain java.awt.geom.Line2D}
 */
public class Line {

	// ======================= Members ========================
	
	/** The first end point of the line. */
	public Vector end1;
	/** The second end point of the line. */
	public Vector end2;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a line with both points at (0, 0).
	 * 
	 * @return	Returns a line at (0, 0).
	 */
	public Line() {
		this.end1 = new Vector();
		this.end2 = new Vector();
	}
	/**
	 * Constructs a line with the two specified end points.
	 * 
	 * @param	end1 - The first end point.
	 * @param	end2 - The second end point.
	 * @return	Returns a line with the two end points.
	 */
	public Line(Vector end1, Vector end2) {
		this.end1 = new Vector(end1);
		this.end2 = new Vector(end2);
	}
	/**
	 * Constructs a line with the two specified end points.
	 * 
	 * @param	x1 - The x coordinate of the first end point.
	 * @param	y1 - The y coordinate of the first end point.
	 * @param	x2 - The x coordinate of the second end point.
	 * @param	y2 - The y coordinate of the second end point.
	 * @return	Returns a line with the two end points.
	 */
	public Line(double x1, double y1, double x2, double y2) {
		this.end1 = new Vector(x1, y1);
		this.end2 = new Vector(x2, y2);
	}
	/**
	 * Constructs a line with the same end points as the given line.
	 * 
	 * @param	l - The line to copy.
	 * @return	Returns a line that is identical to the given line.
	 */
	public Line(Line l) {
		this.end1 = new Vector(l.end1);
		this.end2 = new Vector(l.end2);
	}
	
	// ==================== Shape Details =====================

	/**
	 * Returns the direction of the line from end point 1 to end point 2.
	 * 
	 * @return	Returns the direction of the line from end point 1 to end
	 * point 2.
	 */
	public double direction() {
		return end2.minus(end1).direction();
	}
	/**
	 * Returns the length of the line.
	 * 
	 * @return	Returns the length of the line.
	 */
	public double length() {
		return end2.minus(end1).length();
	}
	/**
	 * Returns the minimum coordinates of the two end points.
	 * 
	 * @return	Returns the minimum coordinates.
	 */
	public Vector min() {
		return Vector.min(end1, end2);
	}
	/**
	 * Returns the maximum coordinates of the two end points.
	 * 
	 * @return	Returns the maximum coordinates.
	 */
	public Vector max() {
		return Vector.max(end1, end2);
	}
	/**
	 * Returns the size from end point 1 to end point 2.
	 * 
	 * @return	Returns a the size of the line.
	 */
	public Vector size() {
		return new Vector(end1, end2);
	}
	/**
	 * Tests whether the line is horizontal.
	 * 
	 * @return	Returns true if the line is horizontal.
	 */
	public boolean isHorizontal() {
		return (end1.y == end2.y);
	}
	/**
	 * Tests whether the line is vertical.
	 * 
	 * @return	Returns true if the line is vertical.
	 */
	public boolean isVertical() {
		return (end1.x == end2.x);
	}
	/**
	 * Creates a polygon with the same points as this line.
	 * 
	 * @return	Returns the polygon shape that represents this line.
	 */
	public Polygon getPolygon() {
		return new Polygon(end1, end2);
	}
	/**
	 * Returns the vector of the line with end point 1 being the origin.
	 * 
	 * @return	TReturns the vector of the line.
	 */
	public Vector getVector() {
		return new Vector(end1, end2);
	}
	/**
	 * Calculates and returns the center of the shape.
	 * 
	 * @return	The position of the center of the shape.
	 */
	public Vector getCenter() {
		return new Vector(end1.plus(end2).scaledBy(0.5));
	}
	/**
	 * Calculates and returns the bounding box of the shape.
	 * 
	 * @return	The rectangle of the bounding box of the shape.
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
	public Line getTranslation(Vector v) {
		return new Line(this).translate(v);
	}
	/**
	 * Translates the shape by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns the shape after it has been translated.
	 */
	public Line translate(Vector v) {
		end1.add(v);
		end2.add(v);
		return this;
	}
	/**
	 * Returns the scaled shape.
	 * 
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Line getScaled(double scale) {
		return new Line(this).scale(scale);
	}
	/**
	 * Returns the shape scaled around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Line getScaled(Vector anchor, double scale) {
		return new Line(this).scale(anchor, scale);
	}
	/**
	 * Scales the shape by the specified size.
	 * 
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Line scale(double scale) {
		end1.scale(scale);
		end2.scale(scale);
		return this;
	}
	/**
	 * Scales the shape by the specified size around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Line scale(Vector anchor, double scale) {
		end1.sub(anchor);
		end2.sub(anchor);
		end1.scale(scale);
		end2.scale(scale);
		end1.add(anchor);
		end2.add(anchor);
		return this;
	}
	/**
	 * Returns the rotated shape.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The changed rotation of the shape
	 * @return	Returns a rotated shape.
	 */
	public Line getRotation(Vector anchor, double theta) {
		return new Line(this).rotate(anchor, theta);
	}
	/**
	 * Rotates the shape by the specified angle around the anchor.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The new rotation of the shape
	 * @return	Returns the shape after it has been rotated.
	 */
	public Line rotate(Vector anchor, double theta) {
		end1.sub(anchor);
		end2.sub(anchor);
		end1.scale(end1.direction() + theta);
		end2.scale(end2.direction() + theta);
		end1.add(anchor);
		end2.add(anchor);
		return this;
	}

	// =================== Shape Collision ====================
	
	/**
	 * Finds the intersection point between this line and another.
	 * 
	 * @param	l - The line to intersect with
	 * @return	Returns the point of intersection between the two lines,
	 * returns null if there is no intersection or the lines are parallel.
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Line-line_intersection">
	   		Wikipedia: Line-Line Intersection</a>
	 */
	public Vector getIntersection(Line l) {
		Vector pt = getIntersectionEndless(l);
		
		// Return null if the point is not within the end points of the lines.
		if (pt == null)
			return null;
		if (!pt.greaterThanOrEqual(min()) || !pt.lessThanOrEqual(max()))
			return null;
		if (!pt.greaterThanOrEqual(l.min()) || !pt.lessThanOrEqual(l.max()))
			return null;
		
		return pt;
	}
	/**
	 * Finds the intersection point between this line and another,
	 * assuming they extend on forever.
	 * 
	 * @param	l - The line to intersect with
	 * @return	Returns the point of intersection between the two endless lines,
	 * returns null if the lines are parallel.
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Line-line_intersection">
	   		Wikipedia: Line-Line Intersection</a>
	 */
	public Vector getIntersectionEndless(Line l) {
		// Check if one or both of the lines are horizontal or parallel
		// in order to speed up calculation.
		if (isHorizontal()) {
			// Lines are parallel
			if (l.isHorizontal())
				return null;
			if (l.isVertical())
				return new Vector(l.end1.x, end1.y);
			
			double xi = l.end1.x + l.size().x * ((end1.y - l.end1.y) / l.size().y);
			return new Vector(xi, end1.y);
		}
		else if (isVertical()) {
			// Lines are parallel
			if (l.isVertical())
				return null;
			if (l.isHorizontal())
				return new Vector(end1.x, l.end1.y);

			double yi = l.end1.y + l.size().y * ((end1.x - l.end1.x) / l.size().x);
			return new Vector(end1.x, yi);
		}
		else if (l.isHorizontal() || l.isVertical())
			return l.getIntersectionEndless(this);
		
		double det = (size().x * l.size().y) - (size().y * l.size().x);
		// Lines are parallel
		if (det == 0.0)
			return null;
		
		double d1 = ((end1.x * end2.y) - (end1.y * end2.x));
		double d2 = ((l.end1.x * l.end2.y) - (l.end1.y * l.end2.x));
		
		double xi = -((d1 * l.size().x) - (d2 * size().x)) / det;
		double yi = -((d1 * l.size().y) - (d2 * size().y)) / det;
		
		return new Vector(xi, yi);
	}
	/**
	 * Tests whether the two lines are parallel.
	 * 
	 * @param	l - The line to check the slope of.
	 * @return	Returns true if the two lines are parallel.
	 */
	public boolean isParallel(Line l) {
		return ((size().x * l.size().y) - (size().y * l.size().x) == 0.0);
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
	 * {@linkplain java.awt.Line2D.Double}
	 */
	public java.awt.geom.Line2D.Double getShape() {
		return new java.awt.geom.Line2D.Double(end1.x, end1.y, end2.x, end2.y);
	}
	
}



