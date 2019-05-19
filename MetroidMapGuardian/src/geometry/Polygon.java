package geometry;


/**
 * A shape that can contain a variable amount of sides. All basic shapes
 * such as rectangles and lines can be converted into a polygon.
 * 
 * @author Robert Jordan
 *
 * @see
 * {@linkplain Vector},
 * {@linkplain Shape},
 * {@linkplain Line}
 */
public class Polygon {

	// ======================= Members ========================
	
	/** The number of points in the polygon. */
	public int		npoints;
	/** An array containing the points of the polygon. */
	public Vector[]	points;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a polygon with no points.
	 * 
	 * @return	Returns a polygon with no points
	 */
	public Polygon() {
		this.npoints = 0;
		this.points	 = new Vector[npoints];
	}
	/**
	 * Constructs a polygon with the specified points.
	 * Input the coordinates in the format x1, y1, x2, y2...
	 * 
	 * @param	points - The x and y values for each point.
	 * @return	Returns a polygon with no points
	 */
	public Polygon(double... points) {
		this.npoints = points.length / 2;
		this.points	 = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			this.points[i] = new Vector(points[i * 2], points[i * 2 + 1]);
		}
	}
	/**
	 * Constructs a polygon with the specified points.
	 * 
	 * @param	points - The vector values for each point.
	 * @return	Returns a polygon with the given points.
	 */
	public Polygon(Vector... points) {
		this.npoints = points.length;
		this.points	 = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			this.points[i] = new Vector(points[i]);
		}
	}
	/**
	 * Constructs a polygon with the specified points.
	 * 
	 * @param	xpoints - The x coordinates of the points.
	 * @param	ypoints - The y coordinates of the points.
	 * @param	npoints - The number of points.
	 * @return	Returns a polygon with the given points.
	 */
	public Polygon(double[] xpoints, double[] ypoints, int npoints) {
		this.npoints = npoints;
		this.points	 = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			this.points[i] = new Vector(xpoints[i], ypoints[i]);
		}
	}
	/**
	 * Constructs a polygon with the specified points.
	 * 
	 * @param	points - The vectors of the points.
	 * @param	npoints - The number of points.
	 * @return	Returns a polygon with the given points.
	 */
	public Polygon(Vector[] points, int npoints) {
		this.npoints = npoints;
		this.points  = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			this.points[i] = new Vector(points[i]);
		}
	}
	/**
	 * Constructs a polygon with the same points as the given one.
	 * 
	 * @param	p - The polygon to copy.
	 * @return	Returns a polygon that is identical to the given one.
	 */
	public Polygon(Polygon p) {
		this.npoints = p.npoints;
		this.points  = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			this.points[i] = new Vector(p.points[i]);
		}
	}

	// ================== Point Modification ==================
	
	/**
	 * Adds a point to the polygon at the specified location.
	 * 
	 * @param	x - The x coordinates of the point.
	 * @param	y - The y coordinates of the point.
	 */
	public void addPoint(double x, double y) {
		addPoint(new Vector(x, y), npoints);
	}
	/**
	 * Adds a point to the polygon at the specified location with the specified index.
	 * 
	 * @param	x - The x coordinates of the point.
	 * @param	y - The y coordinates of the point.
	 * @param	index - The index of the point.
	 */
	public void addPoint(double x, double y, int index) {
		addPoint(new Vector(x, y), index);
	}
	/**
	 * Adds a point to the polygon at the specified location.
	 * 
	 * @param	v - The vector of the point.
	 */
	public void addPoint(Vector v) {
		addPoint(v, npoints);
	}
	/**
	 * Adds a point to the polygon at the specified location with the specified index.
	 * 
	 * @param	v - The vector of the point.
	 * @param	index - The index of the point.
	 */
	public void addPoint(Vector v, int index) {
		npoints++;
		Vector[] newPoints = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			if (i == index)
				newPoints[i] = new Vector(v);
			else if (i > index)
				newPoints[i] = points[i - 1];
			else
				newPoints[i] = points[i];
		}
		points = newPoints;
	}
	/**
	 * Removes the last point in the polygon.
	 */
	public void removePoint() {
		removePoint(npoints - 1);
	}
	/**
	 * Removes the point at the specified index.
	 * 
	 * @param	index - The index of the point.
	 */
	public void removePoint(int index) {
		npoints--;
		Vector[] newPoints = new Vector[npoints];
		
		for (int i = 0; i < npoints; i++) {
			if (i >= index)
				newPoints[i] = points[i + 1];
			else
				newPoints[i] = points[i];
		}
		points = newPoints;
	}
	
	// ==================== Shape Details =====================

	/**
	 * Gets the point at the specified index.
	 * 
	 * @param	index - The index of the point.
	 * @return	Returns the point at the specified index.
	 */
	public Vector getPoint(int index) {
		return new Vector(points[index]);
	}
	/**
	 * Calculates and returns the line of the point at index and the next point.
	 * 
	 * @param	index - The index of the first point.
	 * @return	Returns the line with end points at point1 and point2.
	 */
	public Line getLine(int index) {
		return new Line(points[index], points[(index + 1) % npoints]);
	}

	/**
	 * Calculates and returns the center of the shape.
	 * 
	 * @return	Returns the position of the center of the shape.
	 */
	public Vector getCenter() {
		Vector total = new Vector();
		for (int i = 0; i < npoints; i++) {
			total.add(points[i]);
		}

		return total.scaledBy(1.0 / (double)npoints);
	}
	/**
	 * Calculates and returns the bounding box of the shape.
	 * 
	 * @return	Returns the rectangle of the bounding box of the shape.
	 */
	public Rectangle getBounds() {
		Vector min = new Vector(points[0]);
		Vector max = new Vector(points[0]);
		for (int i = 0; i < npoints; i++) {
			min = Vector.min(min, points[i]);
			max = Vector.max(max, points[i]);
		}
		
		return new Rectangle(min, max.minus(min));
	}
	
	// ================== Shape Modification ==================

	/**
	 * Returns the shape translated by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns a translated shape.
	 */
	public Polygon getTranslation(Vector v) {
		return new Polygon(points, npoints).translate(v);
	}
	/**
	 * Translates the shape by the specified distance.
	 * 
	 * @param	v - The Vector of the distance to translate.
	 * @return	Returns the shape after it has been translated.
	 */
	public Polygon translate(Vector v) {
		for (int i = 0; i < npoints; i++) {
			points[i].add(v);
		}
		return this;
	}
	/**
	 * Returns the scaled shape.
	 * 
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Polygon getScaled(double scale) {
		return new Polygon(points, npoints).scale(scale);
	}
	/**
	 * Returns the shape scaled around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The change in scale of the shape
	 * @return	Returns a scaled shape.
	 */
	public Polygon getScaled(Vector anchor, double scale) {
		return new Polygon(points, npoints).scale(anchor, scale);
	}
	/**
	 * Scales the shape by the specified size.
	 * 
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Polygon scale(double scale) {
		for (int i = 0; i < npoints; i++) {
			points[i].scale(scale);
		}
		return this;
	}
	/**
	 * Scales the shape by the specified size around the specified anchor.
	 * 
	 * @param	anchor - The anchor to scale the shape around.
	 * @param	scale - The new scale of the shape
	 * @return	Returns the shape after it has been scaled.
	 */
	public Polygon scale(Vector anchor, double scale) {
		for (int i = 0; i < npoints; i++) {
			points[i].sub(anchor);
			points[i].scale(scale);
			points[i].add(anchor);
		}
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
		return new Polygon(points, npoints).rotate(anchor, theta);
	}
	/**
	 * Rotates the shape by the specified angle around the anchor.
	 * 
	 * @param	anchor - The anchor to rotate the shape around.
	 * @param	theta - The new rotation of the shape
	 * @return	Returns the shape after it has been rotated.
	 */
	public Polygon rotate(Vector anchor, double theta) {
		for (int i = 0; i < npoints; i++) {
			points[i].sub(anchor);
			points[i].setDirection(points[i].direction() + theta);
			points[i].add(anchor);
		}
		return this;
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
	 * {@link java.awt.Shape}
	 */
	public java.awt.Shape getShape() {
		java.awt.geom.Path2D.Double path = new java.awt.geom.Path2D.Double(java.awt.geom.Path2D.WIND_EVEN_ODD);

		path.moveTo(points[0].x, points[0].y);
		for (int i = 1; i < npoints; i++) {
			path.lineTo(points[i].x, points[i].y);
		}
		path.closePath();
		
		return path;
	}
		
}



