package room;

import main.Mouse;

import geometry.Vector;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class View {

	public static final double ZOOM_AMOUNT =	1.1;
	public static final double ZOOM_MIN    =	0.2;
	public static final double ZOOM_MAX    =	1.0;
	
	public boolean changing = false;
	
	
	// ======================= Members ========================
	
	/** The view position in the room. */
	public Vector pan;
	/** The view zoom in the room. */
	public double zoom;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a view control at (0, 0) with no zoom.
	 * 
	 * @return	Returns the default view control.
	 */
	public View() {
		this.pan	= new Vector();
		this.zoom	= 1.0;
	}
	/**
	 * Constructs a view control at the given position with the given zoom.
	 * 
	 * @return	Returns a view control with the given position with the given zoom.
	 */
	public View(Vector pan, double zoom) {
		this.pan	= new Vector(pan);
		this.zoom	= zoom;
	}

	// ===================== View Methods =====================
	
	/**
	 * Zooms in while focusing on the given coordinate.
	 * 
	 * @param	focusPoint - The point to zoom in to.
	 * @param	zoom - The new zoom.
	 */
	public void zoomFocus(Vector focusPoint, double zoom) {
		//Vector focus	= focusPoint.scaledBy(1.0 / this.zoom).plus(pan);
		//Vector focus	= focusPoint.scaledBy(1.0 * zoom).plus(pan);
		//this.zoom		= zoom;
		//this.pan		= focus.minus(focusPoint.scaledBy(1.0 / this.zoom));
		//this.pan		= focus.minus(focusPoint.scaledBy(1.0 * zoom));
		
		Vector focus	= focusPoint.scaledBy(1.0 / this.zoom);
		//System.out.println(focus.minus(focus.scaledBy(this.zoom / zoom)) + "  " + this.zoom / zoom + "   " + getMousePoint());
		//changing = true;
		this.pan		= pan.plus(focus.minus(focus.scaledBy(this.zoom / zoom)));
		this.zoom		= zoom;
		//changing = false;
		//System.out.println(focus.minus(focus.scaledBy(this.zoom / zoom)) + "  " + this.zoom / zoom + "   " + getMousePoint() + "   " + zoom);
	}
	
	// ===================== Coordinates ======================
	
	/**
	 * Converts the view point to game point coordinates.
	 * 
	 * @param	viewPoint - The view point.
	 * @return	Returns the game point from the view point.
	 */
	public Vector getGamePoint(Vector viewPoint) {
		return viewPoint.scaledBy(1.0 / zoom).plus(pan);
	}
	/**
	 * Converts the game point to view point coordinates.
	 * 
	 * @param	gamePoint - The game point.
	 * @return	Returns the view point from the game point.
	 */
	public Vector getViewPoint(Vector gamePoint) {
		return gamePoint.minus(pan).scaledBy(1.0 * zoom);
	}
	/**
	 * Converts the mouse position to game point coordinates.
	 * 
	 * @return	Returns the game point from the mouse position.
	 */
	public Vector getMousePoint() {
		return getGamePoint(Mouse.getVector());
	}
	
}

