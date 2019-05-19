package graphics;

import game.GameInstance;
import game.Trilogy;
import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import main.ImageLoader;
import main.Main;
import main.Mouse;

import room.Room;

public class PopupAbout extends Gui {

	// ==================== Public Members ====================
	
	public Vector size;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public PopupAbout() {
		super("popup", 100, new Vector());
		
		size = new Vector(660, 350);
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
		
		g.setColor(new Color(12, 26, 36, 180));
		Draw.fillRect(g, position.plus(10, 10), new Vector(148, size.y - 20));
		
		g.setFont(Palette.fontEurostar.deriveFont(Font.PLAIN, 22));
		g.setColor(new Color(33, 189, 222));
		Draw.drawStringWrap(g,
				"Metroid Map Guardian\n" +
				"Copyright (c) 2013 Robert Jordan\n" +
				"\n" +
				"\n" +
				"Many thanks to Falcon Zero for permission to use his maps and " +
				"guides for this program. Visit Metroid Recon (metroid.retropixel.net) " +
				"for the full guides and item descriptions.\n" +
				"\n" +
				"Samus head icon by Nahlej on DeviantArt.\n" +
				//"\n" +
				"S emblem icon by newbreed on DeviantArt.",
				position.plus(180, 20), Draw.ALIGN_TOP, 440, -1, 0);
		
		Image image = ImageLoader.getImage("s_emblem128");
		Draw.drawImage(g, image, position.plus(20, 40));
		
		/*System.out.println("Many thanks to Falcon Zero for permission to use " +
				"his maps and guides for this program. " +
				"Visit Metroid Recon for the full guides and item descriptions.\n\n" +
				"Samus head icon by Nahlej on DeviantArt.\n\n" +
				"S emblem icon by newbreed on DeviantArt.");*/
		/*Draw.drawStringWrap(g, "Visit Metroid Recon for the full guides " +
				"and item descriptions.", position.plus(30, 80),
				Draw.ALIGN_TOP, 440, 10, 0);
		

		Draw.drawStringWrap(g, "Samus head icon by Nahlej on DeviantArt.", position.plus(30, 190),
				Draw.ALIGN_TOP, 440, 10, 0);
		
		Draw.drawStringWrap(g, "S emblem icon by newbreed on DeviantArt.", position.plus(30, 230),
				Draw.ALIGN_TOP, 440, 10, 0);*/
	}
	
}
