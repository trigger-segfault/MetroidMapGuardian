package graphics;

import game.GameInstance;
import game.Trilogy;
import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Main;
import main.Mouse;

import room.Room;

public class PopupControls extends Gui {

	// ==================== Public Members ====================
	
	public Vector size;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public PopupControls() {
		super("popup", 100, new Vector());
		
		size = new Vector(640, 380);
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
		
		room.isMouseOverGUI = true;
		
		if (Mouse.left.pressed() || Mouse.right.pressed() || Mouse.middle.pressed() ||
				Mouse.wheelUp() || Mouse.wheelDown()) {
			destroy();
		}
		
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		position = new Vector((Main.frame.getContentPane().getWidth() - size.x) / 2,
				(Main.frame.getContentPane().getHeight() - size.y) / 2);
		
		g.setColor(new Color(16, 36, 47, 210));
		Draw.fillRect(g, position, size);
		g.setColor(new Color(76, 147, 179, 210));
		Draw.drawRect(g, position, size);
		
		g.setFont(Palette.fontEurostar.deriveFont(Font.PLAIN, 22));
		g.setColor(new Color(33, 189, 222));
		Draw.drawStringWrap(g,
				"Controls\n" +
				"\n" +
				"RMB on map - Move the view.\n" +
				"LMB on minimap - Move the view.\n" +
				"Mouse wheel - Zoom in and out.\n" +
				"\n" +
				"Insert - Toggle showing symbols for uncollected power-ups.\n" +
				"\n" +
				"RMB on power-up - Display full power-up details.\n" +
				"\n" +
				"F3 - Toggle debug mode.\n" +
				"F11 - Toggle fullscreen mode.\n",
				position.plus(26, 20), Draw.ALIGN_TOP, 600, -1, 0);
	}
	
}
