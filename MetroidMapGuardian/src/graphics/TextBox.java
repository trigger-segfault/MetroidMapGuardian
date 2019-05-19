package graphics;

import geometry.Rectangle;
import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Keyboard;
import main.Mouse;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class TextBox extends Gui {

	// ==================== Public Members ====================
	
	public boolean typing;
	
	public String text;
	
	public Vector size;
	
	public Font font;
	
	public int maxLength;
	
	public long cursorTimer;
	public boolean cursorShow;
	
	public boolean editable;
	
	public boolean justStartedTyping = false;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public TextBox() {
		super();
		typing = false;
		text = "";
		size = new Vector();
		maxLength = -1;
		font = null;
		cursorTimer = System.currentTimeMillis();
		cursorShow = true;
		editable = true;
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
	public TextBox(String id, String text, Vector position, Vector size, Font font, boolean editable, int maxLength) {
		super(id, -5, position);
		
		this.text = text;
		this.size = new Vector(size);
		this.font = font;
		this.editable = editable;
		this.typing = false;
		this.maxLength = maxLength;
		this.cursorTimer = System.currentTimeMillis();
		this.cursorShow = true;
	}
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the gui to perform actions and update.
	 */
	public void update() {
		
		if (!editable) {
			typing = false;
		}
		
		
		if (System.currentTimeMillis() - cursorTimer > 600) {
			cursorTimer = System.currentTimeMillis();
			cursorShow = !cursorShow;
		}
		
		if (editable) {
			Rectangle rect = new Rectangle(position, size);
			if (rect.contains(Mouse.getVector())) {
				if (Mouse.left.pressed() && !typing) {
					typing = true;
					cursorTimer = System.currentTimeMillis();
					cursorShow = true;
				}
			}
			else {
				if (Mouse.left.pressed() && typing && !justStartedTyping) {
					typing = false;
				}
			}
			if (typing) {
				if (Keyboard.keyWasTyped()) {
					char typedChar =  Keyboard.getTypedChar();
					if (Keyboard.enter.pressed()) {
						typing = false;
					}
					else if (typedChar == (char)8) {
						if (!text.isEmpty()) {
							cursorTimer = System.currentTimeMillis();
							cursorShow = true;
							text = text.substring(0, text.length() - 1);
						}
					}
					else if (text.length() < maxLength) {
						text += typedChar;
						cursorTimer = System.currentTimeMillis();
						cursorShow = true;
					}
				}
			}
		}
		
		justStartedTyping = false;
	}
	/**
	 * Called every step for the gui to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {

		g.setColor(new Color(16, 36, 47, 170));
		Draw.fillRect(g, position, size);
		g.setColor(new Color(46, 102, 133, 210));
		Draw.drawRect(g, position, size);
		
		java.awt.Rectangle oldClip = g.getClipBounds();
		
		g.setClip((int)position.x, (int)position.y, (int)size.x, (int)size.y);

		g.setColor(new Color(33, 189, 222));
		g.setFont(font);
		Draw.drawString(g, text + (cursorShow && typing ? "_" : ""), position.plus(12, size.y / 2), Draw.ALIGN_MIDDLE);
		
		g.setClip(oldClip);
		
		
	}
	
}
