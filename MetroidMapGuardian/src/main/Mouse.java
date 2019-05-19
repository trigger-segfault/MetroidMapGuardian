package main;

import geometry.Vector;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


/**
 * A static mouse listener class that handles
 * mouse buttons and wheel movement.
 * 
 * @author David
 */
public class Mouse extends MouseAdapter implements MouseWheelListener {
	public static Button left            = new Button(MouseEvent.BUTTON1);
	public static Button middle          = new Button(MouseEvent.BUTTON2);
	public static Button right           = new Button(MouseEvent.BUTTON3);
	private static Button[] buttons      = {left, middle, right};
	private static Point position        = new Point();
	private static Point positionLast    = new Point();
	private static boolean mouseInWindow = false;
	private static int rawWheelMovement  = 0;
	private static int wheelMovement     = 0;

	
	public static void reset() {
		for (int i = 0; i < 3; i++) {
			buttons[i].rawDown = false;
			buttons[i].rawClicked = false;
		}
	}
	

	/** Sub-class that represents a mouse button. **/
	public static class Button {
		protected int buttonIndex;
		protected boolean rawDown;
		protected boolean down;
		protected boolean downPrev;
		protected boolean rawClicked;
		protected boolean clicked;
		
		public Button(int buttonIndex) {
			this.buttonIndex = buttonIndex;
			this.rawDown     = false;
			this.down        = false;
			this.downPrev    = false;
			this.rawClicked  = false;
			this.clicked     = false;
		}

		/** Check if the mouse button is down. **/
		public boolean down() {
			return down;
		}

		/** Check if the mouse button was pressed. **/
		public boolean pressed() {
			return (down && !downPrev);
		}

		/** Check if the mouse button was released. **/
		public boolean released() {
			return (!down && downPrev);
		}

		/** Check if the mouse button was clicked (pressed & released without movement). **/
		public boolean clicked() {
			return clicked;
		}
	}
	

	/** Get the x component of the mouse position. **/
	public static int x() {
		return position.x;
	}

	/** Get the y component of the mouse position. **/
	public static int y() {
		return position.y;
	}
	
	/** Get the previous x component of the mouse position. **/
	public static int xPrevious() {
		return positionLast.x;
	}
	
	/** Get the previous y component of the mouse position. **/
	public static int yPrevious() {
		return positionLast.y;
	}

	/** Get the mouse position as a Point. **/
	public static Point position() {
		return new Point(position);
	}
	
	/** Get the mouse position as a Vector. **/
	public static Vector getVector() {
		return new Vector(position.x, position.y);
	}
	
	
	/** Get the previous mouse position as a Vector. **/
	public static Vector getVectorPrevious() {
		return new Vector(positionLast.x, positionLast.y);
	}
	
	/** Get the previous mouse position as a Point. **/
	public static Point positionLast() {
		return new Point(positionLast);
	}

	/** Check if the mouse wheel is moved up. **/
	public static boolean wheelUp() {
		return (wheelMovement < 0);
	}

	/** Check if the mouse wheel is moved down. **/
	public static boolean wheelDown() {
		return (wheelMovement > 0);
	}

	/** Check if the mouse is inside the window. **/
	public static boolean inWindow() {
		return mouseInWindow;
	}

	/** Check if the mouse position is in a rectangular area. **/
	public static boolean inArea(int x1, int y1, int x2, int y2) {
		return (x() >= x1 && y() >= y1 && x() < x2 && y() < y2);
	}

	/** Check if the mouse position is in a rectangular area defnined by two points. **/
	public static boolean inArea(Point p1, Point p2) {
		return (x() >= p1.x && y() >= p1.y && x() < p2.x && y() < p2.y);
	}

	/** Check if the mouse position is in a rectangular area. **/
	public static boolean inArea(Rectangle r) {
		return (x() >= r.x && y() >= r.y && x() < r.x + r.width && y() < r.y + r.height);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Button b : buttons) {
			if (b.buttonIndex == e.getButton()) {
				b.rawDown = true;
				break;
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		for (Button b : buttons) {
			if (b.buttonIndex == e.getButton()) {
				b.rawDown = false;
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for (Button b : buttons) {
			if (b.buttonIndex == e.getButton()) {
				b.rawClicked = true;
				break;
			}
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		rawWheelMovement = e.getWheelRotation();
	}
	
	/** Update button states, wheel movement, and mouse coordinates. **/
	public static void update() {
		// Update the button states:
		for (Button b : buttons) {
			b.downPrev = b.down;
			b.down     = b.rawDown;
			b.clicked  = b.rawClicked;
		}
		
		// Update the scroll wheel movement:
		wheelMovement    = rawWheelMovement;
		rawWheelMovement = 0;

		// Update the mouse coordinates:
		positionLast.setLocation(position);
		Point msPosition = Main.game.getMousePosition();
		mouseInWindow    = (msPosition != null);
		if (mouseInWindow) {
			position.setLocation(msPosition);
		}
	}
}
