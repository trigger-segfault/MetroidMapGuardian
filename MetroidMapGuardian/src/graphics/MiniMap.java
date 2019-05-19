package graphics;

import entity.Entity;
import entity.Marker;
import game.GameInstance;
import game.Trilogy;
import geometry.Rectangle;
import geometry.Vector;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Main;
import main.Mouse;

import room.Map;
import room.Room;

public class MiniMap extends Gui {

	// ==================== Public Members ====================
	
	//public static final double scale = 12.0;
	public static final Vector maxSize = new Vector(192, 224);
	
	public boolean visible;
	
	public Vector size;
	
	public boolean pressed;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public MiniMap() {
		super();
		
		visible = true;
		size = new Vector();
		pressed = false;
	}
	/**
	 * Constructs an entity with the specified details.
	 * 
	 * @param	id - The key identifier of the entity.
	 * @param	depth - The depth of the entity.
	 * @param	position - The position of the entity.
	 * @param	velocity - The velocity of the entity.
	 * @return	Returns an entity with the specified details.
	 */
	protected MiniMap(String id, int depth, Vector position) {
		super("minimap", -10, new Vector(0, 0));

		visible = true;
		size = new Vector();
	}
	/**
	 * Initializes the entity by setting the game instance it is linked to.
	 * 
	 * @param	room - The room that contains the entity.
	 * @param	instance - The game instance that contains the room.
	 */
	public void initialize(Room room, GameInstance instance, Trilogy trilogy) {
		super.initialize(room, instance, trilogy);
	}
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the entity to perform actions and update.
	 */
	public void update() {
		
		LinkedImage image = ((Map)room).image;
		double scale = 0.0;
		
		// Determine which axis has to be shrunk more to fit in the map size:
		Vector scaleVec = new Vector((double)image.getWidth() / maxSize.x, (double)image.getHeight() / maxSize.y);
		if (scaleVec.x >= scaleVec.y)
			scale = scaleVec.x;
		else
			scale = scaleVec.y;
		size = ((Map)room).image.getSize().scaledBy(1.0 / scale);
		
		position = new Vector(Main.frame.getContentPane().getWidth() - 24, Main.frame.getContentPane().getHeight() - 24 - 32).minus(size);
		
		Rectangle rect = new Rectangle(position, size);
		
		if (rect.contains(Mouse.getVector())) {
			room.isMouseOverGUI = true;
			if (Mouse.left.pressed()) {
				pressed = true;
			}
			if (Mouse.left.down() && pressed) {
				Vector mouseCenter = Mouse.getVector().minus(position).scaledBy(scale);
				mouseCenter.sub(Main.frame.getContentPane().getWidth() / (2 * room.view.zoom),
						Main.frame.getContentPane().getHeight() / (2 * room.view.zoom));
				room.view.pan = mouseCenter;
			}
		}
		if (Mouse.left.released()) {
			pressed = false;
		}
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
		LinkedImage image = ((Map)room).image;
		double scale = 0.0;
		
		// Determine which axis has to be shrunk more to fit in the map size:
		Vector scaleVec = new Vector((double)image.getWidth() / maxSize.x, (double)image.getHeight() / maxSize.y);
		if (scaleVec.x >= scaleVec.y)
			scale = scaleVec.x;
		else
			scale = scaleVec.y;
		size = ((Map)room).image.getSize().scaledBy(1.0 / scale);
		
		position = new Vector(Main.frame.getContentPane().getWidth() - 24, Main.frame.getContentPane().getHeight() - 24 - 32).minus(size);

		Vector windowSize = new Vector(Main.frame.getContentPane().getWidth(), Main.frame.getContentPane().getHeight()).scale(1.0 / room.view.zoom);
		
		g.setColor(new Color(16, 36, 47, 210));
		Draw.fillRect(g, position, size);
		g.setColor(new Color(76, 147, 179, 210));
		Draw.drawRect(g, position, size);
		
		for (Entity e : room.entities) {
			if (e instanceof Marker) {
				Marker m = (Marker)e;
				if (!m.getPowerUp().collected) {
					Vector point = position.plus(m.position.scaledBy(1.0 / scale)).minus(2, 2);
					g.setColor(m.getPowerUp().getTypeColor());
					Draw.fillRect(g, point, new Vector(4, 4));
				}
			}
		}

		Vector viewPoint = position.plus(room.view.pan.scaledBy(1.0 / scale));
		Vector viewSize = windowSize.scaledBy(1.0 / scale);

		java.awt.Rectangle clip = g.getClipBounds();
		g.setClip((int)position.x, (int)position.y, (int)size.x, (int)size.y);
		
		g.setColor(new Color(255, 255, 255));
		Draw.drawRect(g, viewPoint, viewSize);
		
		g.setClip(clip);
		
		
	}
	
}
