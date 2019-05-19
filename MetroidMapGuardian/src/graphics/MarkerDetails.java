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

import entity.Marker;
import main.ImageLoader;
import main.Keyboard;
import main.Mouse;
import room.Room;

/**
 * A gui that displays details about the marker's power up. When the mouse is
 * no longer hovering over the marker, then the gui will be destroyed.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain Marker}
 */
public class MarkerDetails extends Gui {

	// ==================== Public Members ====================
	
	/** The marker this display is linked to. */
	public Marker marker;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a gui at (0, 0)).
	 * 
	 * @return	Returns the default gui.
	 */
	public MarkerDetails() {
		super();
		this.marker		= null;
	}
	/**
	 * Constructs an entity with the specified details.
	 * 
	 * @param	marker - The marker to link the gui to.
	 * @return	Returns an gui with the specified details.
	 */
	public MarkerDetails(Marker marker) {
		super("", -5, new Vector());
		this.marker		= marker;
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
		
		// Destroy the display if the marker doesn't exist or the mouse is not
		// hovering over it.
		if (!marker.hovering || !room.entityExists(marker)) {
			destroy();
		}
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
		Font font = new Font("Crystal clear", Font.PLAIN, 12);
		
		this.position = Mouse.getVector();
		
		Vector size = new Vector(256, 0);

		double boxWidth = size.x - 8;
		double drawWidth = boxWidth - 6 - 5;
		double drawSeparation = 0;
		int maxLines = 10;

		PowerUp powerUp = marker.getPowerUp();
		if (Keyboard.shift.down() && marker.hasLinkedPowerUp()) {
			powerUp = marker.getLinkedPowerUp();
		}
		
		String title = powerUp.getTypeName() + ": " + powerUp.name;
		String location = powerUp.location;
		String description = powerUp.description;
		
		String num = Integer.toString(marker.index + 1);
		while (num.length() < 3) {
			num = "0" + num;
		}
		
		Image image = ImageLoader.getImage(trilogy.rawName + "/" + PowerUp.getPowerUpRawName(marker.type) + "/" + num);

		
		double titleHeight = Draw.getStringWrapSize(title, font, drawWidth, maxLines, drawSeparation).y;
		double locationHeight = Draw.getStringWrapSize(location, font, drawWidth, maxLines, drawSeparation).y;
		double descriptionHeight = Draw.getStringWrapSize(description, font, drawWidth, maxLines, drawSeparation).y;
		double imageHeight = 0;
		if (image != null)
			imageHeight = image.getHeight(null);
		
		Rectangle titleRect = new Rectangle(new Vector(4, 4),
				new Vector(boxWidth, titleHeight + 6));
		Rectangle locationRect = new Rectangle(titleRect.point.plus(0, titleRect.size.y + 4),
				new Vector(boxWidth, locationHeight + 6));
		Rectangle descriptionRect = new Rectangle(locationRect.point.plus(0, locationRect.size.y + 4),
				new Vector(boxWidth, descriptionHeight + 6));
		Rectangle imageRect = new Rectangle(descriptionRect.point.plus(0, descriptionRect.size.y + 4),
				new Vector(boxWidth, imageHeight + 6));
		
		if (image != null)
			size.y = imageRect.point.y + imageRect.size.y + 4;
		else
			size.y = descriptionRect.point.y + descriptionRect.size.y + 4;
		
		Rectangle window = new Rectangle(position, size);
		
		titleRect.translate(position);
		locationRect.translate(position);
		descriptionRect.translate(position);
		imageRect.translate(position);
		
		g.setPaint(new GradientPaint((float)position.x, (float)position.y, new Color(46, 102, 133, 210),
				(float)position.x, (float)(position.y + size.y), new Color(16, 36, 47, 210)));
		
		Draw.fillRect(g, window);
		g.setColor(new Color(16, 36, 47, 160));
		Draw.fillRect(g, titleRect);
		Draw.fillRect(g, locationRect);
		Draw.fillRect(g, descriptionRect);
		if (image != null)
			Draw.fillRect(g, imageRect);
		g.setColor(new Color(76, 147, 179, 210));
		Draw.drawRect(g, window);
		
		g.setFont(font);
		g.setColor(new Color(33, 189, 222));

		//System.out.println(Draw.getStringSize("From here, turn around and look for", font));
		Draw.drawStringWrap(g, title, titleRect.point.plus(3, 3), Draw.ALIGN_TOP, drawWidth, maxLines, drawSeparation);
		Draw.drawStringWrap(g, location, locationRect.point.plus(3, 3), Draw.ALIGN_TOP, drawWidth, maxLines, drawSeparation);
		Draw.drawStringWrap(g, description, descriptionRect.point.plus(3, 3), Draw.ALIGN_TOP, drawWidth, maxLines, drawSeparation);

		//System.out.println(Draw.getStringSize("From here, turn around and look for", font));
		if (image != null)
			Draw.drawImage(g, image, imageRect.point.plus(3 + drawWidth / 2 - image.getWidth(null) / 2, 3));
	}
	
}
