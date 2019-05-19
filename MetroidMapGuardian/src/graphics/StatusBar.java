package graphics;

import game.GameInstance;
import game.PowerUp;
import game.Trilogy;
import geometry.Rectangle;
import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;

import main.Main;
import main.Mouse;
import room.Room;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class StatusBar extends Gui {

	// ==================== Public Members ====================
	
	
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a gui at (0, 0).
	 * 
	 * @return	Returns the default gui.
	 */
	public StatusBar() {
		super();
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
		
		Vector size = new Vector(Main.frame.getContentPane().getWidth() + 64, 64 + 64);
		position = new Vector(0, Main.frame.getContentPane().getHeight() - 40);
		
		Rectangle rect = new Rectangle(position, size);
		
		if (rect.contains(Mouse.getVector())) {
			room.isMouseOverGUI = true;
		}
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {

		Vector size = new Vector(Main.frame.getContentPane().getWidth() + 64, 64 + 64);
		position = new Vector(0, Main.frame.getContentPane().getHeight() - 40);

		g.setPaint(new GradientPaint((float)position.x, (float)position.y, new Color(46, 102, 133, 210),
				(float)position.x, (float)(position.y + 40), new Color(16, 36, 47, 210)));
		
		g.fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);
		Draw.fillRect(g, position, size);
		g.setColor(new Color(76, 147, 179, 210));
		Draw.drawLine(g, position, position.plus(size.x, 0));
		
		double offset = 16;

		Font font = new Font("Crystal clear", Font.PLAIN, 18);
		g.setFont(font);
		g.setColor(new Color(33, 189, 222));
		
		for (int i = 0; i < PowerUp.NUM_TYPES; i++) {
			
			if (PowerUp.isPowerUpInGame(i, trilogy.game)) {
				
				int numCollected = 0;
				int numTotal = trilogy.powerUps.get(i).size();
				
				for (PowerUp p : trilogy.powerUps.get(i)) {
					if (p.collected)
						numCollected++;
				}
				
				Image image = PowerUp.getPowerUpIcon(i);
				if (image != null) {
					Draw.drawImage(g, image, position.plus(offset, -4));
					offset += image.getWidth(null);
					offset += 6;
				}
				
				Draw.drawString(g, numCollected + "/" + numTotal, position.plus(offset, 9), Draw.ALIGN_TOP);
				
				offset += Draw.getStringSize(numCollected + "/" + numTotal, font).x;
				//offset += Draw.getStringSize("000/000", font).x;
				
				offset += 12;
			}
		}

		font = new Font("Crystal clear", Font.PLAIN, 18);
		font = Palette.fontEurostar.deriveFont(Font.PLAIN, 20);
		g.setFont(font);
		
		Draw.drawString(g, "Completion:",
				position.plus(Main.frame.getContentPane().getWidth() - 72, 8),
				Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
		
		Draw.drawString(g, trilogy.getCompletionPercentage() + "%",
		//Draw.drawString(g, "100%",
				position.plus(Main.frame.getContentPane().getWidth() - 16, 8),
				Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
	}
	
}
