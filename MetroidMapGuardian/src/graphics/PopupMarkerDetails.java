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
import main.Main;
import main.Mouse;

import room.Room;

public class PopupMarkerDetails extends Gui {

	// ==================== Public Members ====================
	
	/** The marker this display is linked to. */
	public Marker marker;
	
	public Vector size;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public PopupMarkerDetails() {
		super("popup", 100, new Vector());
		this.marker = null;
		size = new Vector(660, 350);
	}
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public PopupMarkerDetails(Marker marker) {
		super("popup", 100, new Vector());
		this.marker = marker;
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
		
		Font font = new Font("Crystal clear", Font.PLAIN, 12);
		
		this.position = Mouse.getVector();
		
		Vector size = new Vector(256 + 400 + 4, 0);

		double boxWidth1 = 256 - 8;
		double drawWidth1 = boxWidth1 - 6 - 5;
		double boxWidth2 = 400;
		double drawWidth2 = boxWidth2 - 6 - 5;
		double drawSeparation = 0;
		int maxLines = 30;

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

		double titleHeight = Draw.getStringWrapSize(title, font, drawWidth1, maxLines, drawSeparation).y;
		double locationHeight = Draw.getStringWrapSize(location, font, drawWidth1, maxLines, drawSeparation).y;
		double descriptionHeight = Draw.getStringWrapSize(description, font, drawWidth2, maxLines, drawSeparation).y;
		double imageHeight = 0;
		if (image != null)
			imageHeight = image.getHeight(null);
		
		Rectangle titleRect = new Rectangle(new Vector(4, 4),
				new Vector(boxWidth1, titleHeight + 6));
		Rectangle locationRect = new Rectangle(titleRect.point.plus(0, titleRect.size.y + 4),
				new Vector(boxWidth1, locationHeight + 6));
		Rectangle imageRect = new Rectangle(locationRect.point.plus(0, locationRect.size.y + 4),
				new Vector(boxWidth1, imageHeight + 6));

		Rectangle descriptionRect = new Rectangle(new Vector(256, 4),
				new Vector(boxWidth2, descriptionHeight + 6));
		
		double height1 = (image != null ? (imageRect.point.y + imageRect.size.y) :
			(locationRect.point.y + locationRect.size.y)) + 4;
		
		double height2 = descriptionRect.point.y + descriptionRect.size.y + 4;
		
		size.y = Math.max(height1,  height2);
		
		position = new Vector((Main.frame.getContentPane().getWidth() - size.x) / 2,
				(Main.frame.getContentPane().getHeight() - size.y) / 2);
		
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
		
		Draw.drawStringWrap(g, title, titleRect.point.plus(3, 3), Draw.ALIGN_TOP, drawWidth1, maxLines, drawSeparation);
		Draw.drawStringWrap(g, location, locationRect.point.plus(3, 3), Draw.ALIGN_TOP, drawWidth1, maxLines, drawSeparation);
		Draw.drawStringWrap(g, description, descriptionRect.point.plus(3, 3), Draw.ALIGN_TOP, drawWidth2, maxLines, drawSeparation);
		
		if (image != null)
			Draw.drawImage(g, image, imageRect.point.plus(3 + drawWidth1 / 2 - image.getWidth(null) / 2, 3));
	}
	
}
