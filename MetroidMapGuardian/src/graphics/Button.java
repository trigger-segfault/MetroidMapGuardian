package graphics;

import geometry.Rectangle;
import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Mouse;

public class Button extends Gui {

	// ==================== Public Members ====================
	
	public boolean visible;
	
	public Vector size;
	
	public String label;
	
	public boolean hover;
	public boolean pressed;
	
	public Font font;
	
	public boolean dropDown;
	
	public ArrayList<String> listItems;
	
	public int selectedIndex;
	
	public int hoverIndex;
	
	public boolean dropDownOpen;
	
	public Vector dropDownSize;
	
	public Font dropDownFont;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public Button() {
		super();
		
		visible = true;
		size = new Vector();
		label = "";
		hover = false;
		pressed = false;
		font = null;
		dropDown = false;
		listItems = new ArrayList<String>();
		selectedIndex = -1;
		dropDownOpen = false;
		hoverIndex = -1;
		dropDownFont = null;
		dropDownSize = new Vector();
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
	public Button(String id, String label, Vector position, Vector size, Vector dropDownSize, Font font, Font dropDownFont, boolean dropDown) {
		super(id, -5, position);

		visible = true;
		this.size = new Vector(size);
		this.label = label;
		hover = false;
		pressed = false;
		this.font = font;
		this.dropDown = dropDown;
		listItems = new ArrayList<String>();
		selectedIndex = -1;
		dropDownOpen = false;
		hoverIndex = -1;
		this.dropDownSize = dropDownSize;
		this.dropDownFont = dropDownFont;
	}
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the gui to perform actions and update.
	 */
	public void update() {
		selectedIndex = -1;
		hoverIndex = -1;
		boolean guiJustOpened = false;
		Rectangle buttonRect = new Rectangle(position, size);
		Rectangle dropDownRect = new Rectangle(position.plus(0, size.y), dropDownSize);
		
		pressed = false;
		
		if (buttonRect.contains(Mouse.getVector())) {
			room.isMouseOverGUI = true;
			if (!hover) {
				hover = true;
			}
			if (Mouse.left.pressed()) {
				pressed = true;
				if (dropDown) {
					if (!dropDownOpen) {
						guiJustOpened = true;
					}
					dropDownOpen = !dropDownOpen;
				}
			}
		}
		else {
			hover = false;
		}
		
		if (dropDownOpen) {
			for (int i = 0; i < listItems.size(); i++) {
				
				if (dropDownRect.contains(Mouse.getVector())) {
					room.isMouseOverGUI = true;
					hoverIndex = i;
					if (Mouse.left.pressed()) {
						selectedIndex = i;
						dropDownOpen = false;
					}
				}
				
				dropDownRect.translate(new Vector(0, dropDownRect.size.y));
			}
		}
		
		if (Mouse.left.released()) {
			pressed = false;
		}
		
		if (!guiJustOpened && (Mouse.left.pressed() || Mouse.right.pressed())) {
			dropDownOpen = false;
		}
	}
	/**
	 * Called every step for the gui to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
		if (hover) {
			g.setPaint(new GradientPaint((float)position.x, (float)position.y, new Color(90, 160, 199, 210),
					(float)position.x, (float)(position.y + size.y), new Color(46, 102, 133, 210)));
		}
		else {
			g.setPaint(new GradientPaint((float)position.x, (float)position.y, new Color(46, 102, 133, 210),
					(float)position.x, (float)(position.y + size.y), new Color(16, 36, 47, 210)));
		}
		Draw.fillRect(g, position, size);
		g.setColor(new Color(76, 147, 179, 210));
		Draw.drawRect(g, position, size);

		g.setColor(new Color(33, 189, 222));
		g.setFont(font);
		Draw.drawString(g, label, position.plus(size.scaledBy(0.5)), Draw.ALIGN_CENTER | Draw.ALIGN_MIDDLE);
		
		g.setFont(dropDownFont);
		if (dropDownOpen) {

			g.setColor(new Color(16, 36, 47, 210));
			Draw.fillRect(g, position.plus(0, size.y), new Vector(dropDownSize.x, dropDownSize.y * listItems.size()));
			g.fillRect((int)(position.x),
					(int)(position.y + size.y),
					(int)dropDownSize.x,
					(int)(dropDownSize.y * listItems.size()));
			
			for (int i = 0; i < listItems.size(); i++) {
				if (hoverIndex == i) {
					g.setColor(new Color(46, 102, 133, 210));
					Draw.fillRect(g, position.plus(0, size.y + dropDownSize.y * i), dropDownSize);
					g.fillRect((int)(position.x),
							(int)(position.y + size.y + dropDownSize.y * i),
							(int)dropDownSize.x,
							(int)dropDownSize.y);
				}
				g.setColor(new Color(33, 189, 222));
				Draw.drawString(g, listItems.get(i), position.plus(24, size.y + dropDownSize.y * (i + 0.5)), Draw.ALIGN_LEFT | Draw.ALIGN_MIDDLE);
			}
			
			g.setColor(new Color(76, 147, 179, 210));
			Draw.drawRect(g, position.plus(0, size.y), new Vector(dropDownSize.x, dropDownSize.y * listItems.size()));
		}
	}	
}
