package graphics;

import game.Profile;
import geometry.Rectangle;
import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Main;
import main.Mouse;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class ProfileList extends Gui {
	
	// ==================== Public Members ====================

	public ArrayList<Profile> profiles;
	public int hoverIndex;
	public int selectedIndex;
	
	public double scrollPosition;
	
	public double scrollSpeed;
	public double scrollScale;
	public boolean scrollingUp;
	
	
	public boolean scrollDragging;
	public double scrollGrip;
	public boolean scrollHover;
	
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public ProfileList() {
		super();

		profiles = new ArrayList<Profile>();
		scrollPosition = 0.0;
		
		selectedIndex = -1;
		hoverIndex = -1;
		this.scrollSpeed	= 0.0;
		this.scrollingUp	= false;
		this.scrollScale	= 0.0;
		scrollDragging = false;
		scrollGrip = 0.0;
		scrollHover = false;
	}
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the gui to perform actions and update.
	 */
	public void update() {
		selectedIndex = -1;
		hoverIndex = -1;

		Vector size = new Vector(Main.frame.getContentPane().getWidth() - 96 - 24, Main.frame.getContentPane().getHeight() - 200);
		position = new Vector(48, 104);
		Vector itemSize = new Vector(size.x, 80);
		double scrollBarHeight = Math.max(6, size.y / (profiles.size() * itemSize.y) * (size.y - 4));
		double scrollBarPos = (size.y - 4 - scrollBarHeight) / (profiles.size() * itemSize.y - size.y) * scrollPosition;
		
		Rectangle scrollRect = new Rectangle(position.plus(size.x + 3, scrollBarPos + 3), new Vector(20, scrollBarHeight));
		if (scrollRect.contains(Mouse.getVector())) {
			scrollHover = true;
			if (Mouse.left.pressed()) {
				scrollDragging = true;
				scrollGrip = Mouse.getVector().y - scrollRect.point.y;
			}
		}
		else {
			scrollHover = false;
		}
		if (scrollDragging) {
			if (Mouse.left.released()) {
				scrollDragging = false;
			}
			else {
				scrollBarPos = Mouse.getVector().y - scrollGrip - (position.y + 3);
				if (scrollBarPos < 0.0) {
					scrollBarPos = 0.0;
				}
				else if (scrollBarPos > size.y - 4 - scrollBarHeight) {
					scrollBarPos = size.y - 4 - scrollBarHeight;
				}
				scrollPosition = (profiles.size() * itemSize.y - size.y) / (size.y - 4 - scrollBarHeight) * scrollBarPos;
			}
		}
		
		for (int i = 0; i < profiles.size() && !scrollDragging; i++) {
			Rectangle itemRect = new Rectangle(position.plus(0, i * itemSize.y - scrollPosition), itemSize);
			
			if (itemRect.point.y + itemRect.size.y >= position.y &&
					itemRect.point.y <= position.y + size.y) {
				
				if (itemRect.point.y < position.y) {
					Vector newSize = itemRect.size.minus(position).plus(itemRect.point);
					itemRect.point = new Vector(position);
					itemRect.size = new Vector(newSize);
				}
				if (itemRect.point.y + itemRect.size.y > position.y + size.y) {
					itemRect.size.y += (position.y + size.y) - (itemRect.point.y + itemRect.size.y);
				}
				
				if (itemRect.contains(Mouse.getVector())) {
					hoverIndex = i;
					if (Mouse.left.pressed()) {
						selectedIndex = i;
					}
				}
			}
		}

		double scaleMultiplier = 1.2;
		double scaleAddition = 0.2;
		double speedMultiplier = 4.0;
		double speedDeduction = 0.3;
		double maxScale = 10.0;
		double minScroll = 0.0;
		double maxScroll = profiles.size() * itemSize.y - size.y;
		
		if (Mouse.wheelUp()) {
			if (!scrollingUp || scrollSpeed == 0.0) {
				scrollScale = 1.0;
				scrollSpeed = speedMultiplier;
				scrollingUp = true;
			}
			else {
				scrollScale *= scaleMultiplier + scaleAddition;
				scrollScale = Math.min(scrollScale, maxScale);
				scrollSpeed = speedMultiplier * scrollScale;
			}
		}
		if (Mouse.wheelDown()) {
			if (scrollingUp || scrollSpeed == 0.0) {
				scrollScale = 1.0;
				scrollSpeed = speedMultiplier;
				scrollingUp = false;
			}
			else {
				scrollScale *= scaleMultiplier + scaleAddition;
				scrollScale = Math.min(scrollScale, maxScale);
				scrollSpeed = speedMultiplier * scrollScale;
			}
		}
		
		if (scrollSpeed != 0.0) {
			scrollSpeed -= speedDeduction * scrollScale;
			if (scrollSpeed <= 0.0) {
				scrollSpeed = 0.0;
			}
			else {
				if (scrollingUp) {
					scrollPosition -= scrollSpeed;
					if (scrollPosition <= minScroll) {
						scrollPosition = minScroll;
						scrollSpeed = 0.0;
					}
				}
				else {
					scrollPosition += scrollSpeed;
					if (scrollPosition >= maxScroll) {
						scrollPosition = maxScroll;
						scrollSpeed = 0.0;
					}
				}
			}
		}
		if (scrollPosition <= minScroll) {
			scrollPosition = minScroll;
		}
		else if (scrollPosition >= maxScroll) {
			scrollPosition = maxScroll;
		}
	}
	/**
	 * Called every step for the gui to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {

		// Draw the title:
		g.setPaint(new GradientPaint(64.0f, 0.0f, new Color(24, 50, 67, 255),
				Main.frame.getContentPane().getWidth(), 0.0f, new Color(0, 0, 0, 0)));
		Draw.fillRect(g, 0, 24, Main.frame.getContentPane().getWidth(), 104 - 40);
		
		g.setFont(new Font("Eurostar Black Extended", Font.PLAIN, 52));
		g.setColor(new Color(172, 180, 190));
		Draw.drawString(g, "PROFILE SELECT", 24, 24, Draw.ALIGN_TOP);

		Vector size = new Vector(Main.frame.getContentPane().getWidth() - 96 - 24, Main.frame.getContentPane().getHeight() - 200);
		position = new Vector(48, 104);
		Vector itemSize = new Vector(size.x, 80);
		double scrollBarHeight = Math.min(size.y - 4, Math.max(6, size.y / (profiles.size() * itemSize.y) * (size.y - 4)));
		double scrollBarPos = (size.y - 4 - scrollBarHeight) / (profiles.size() * itemSize.y - size.y) * scrollPosition;
		
		// If the scroll bar is not needed, make it fill the allowed height
		if (size.y >= profiles.size() * itemSize.y) {
			scrollBarHeight = size.y - 4;
		}
		
		// Draw the list background
		g.setColor(new Color(16, 36, 47, 210));
		Draw.fillRect(g, position, size);
		Draw.fillRect(g, position.plus(size.x, 0), new Vector(24, size.y));
		
		// Draw each profile entry:
		for (int i = 0; i < profiles.size(); i++) {
			Rectangle itemRect = new Rectangle(position.plus(0, i * itemSize.y - scrollPosition), itemSize);
			
			// Only draw the profile if it is within the visible portion of the list:
			if (itemRect.point.y + itemRect.size.y >= position.y &&
				itemRect.point.y <= position.y + size.y) {
				
				java.awt.Rectangle oldClip = g.getClipBounds();
				
				Rectangle clipBounds = new Rectangle(itemRect);
				
				// Set the clip bounds so the rectangle and text do not draw out of bounds
				if (itemRect.point.y < position.y) {
					clipBounds.point.y = position.y;
					clipBounds.size.y += itemRect.point.y - position.y;
				}
				if (itemRect.point.y + itemRect.size.y > position.y + size.y) {
					clipBounds.size.y += (position.y + size.y) - (itemRect.point.y + itemRect.size.y);
				}
				g.setClip((int)clipBounds.point.x, (int)clipBounds.point.y, (int)clipBounds.size.x, (int)clipBounds.size.y + 1);
				
				// Highlight the entry if the mouse is hovering over it.
				if (hoverIndex == i) {
					g.setColor(new Color(46, 102, 133, 210));
					Draw.fillRect(g, itemRect);
				}

				int percentX1 = 68;
				int percentX2 = 20;
				
				// Draw the profile name and completion status:
				g.setColor(new Color(33, 189, 222));
				//g.setFont(new Font("Eurostar", Font.PLAIN, 28));
				g.setFont(Palette.fontEurostar.deriveFont(Font.PLAIN, 28));
				Draw.drawString(g, profiles.get(i).profileName, itemRect.point.plus(10, 6), Draw.ALIGN_TOP);
				//g.setFont(new Font("Eurostar", Font.PLAIN, 18));
				g.setFont(Palette.fontEurostar.deriveFont(Font.PLAIN, 18));
				Draw.drawString(g, "Metroid Prime 1: ", itemRect.point.plus(6, 6).plus(itemRect.size.x - percentX1, 0), Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
				Draw.drawString(g, profiles.get(i).getCompletionPercentage(1) + "%",
						itemRect.point.plus(6, 6).plus(itemRect.size.x - percentX2, 0), Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
				Draw.drawString(g, "Metroid Prime 2: ", itemRect.point.plus(6, 6 + 24).plus(itemRect.size.x - percentX1, 0), Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
				Draw.drawString(g, profiles.get(i).getCompletionPercentage(2) + "%",
						itemRect.point.plus(6, 6 + 24).plus(itemRect.size.x - percentX2, 0), Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
				Draw.drawString(g, "Metroid Prime 3: ", itemRect.point.plus(6, 6 + 48).plus(itemRect.size.x - percentX1, 0), Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
				Draw.drawString(g, profiles.get(i).getCompletionPercentage(3) + "%",
						itemRect.point.plus(6, 6 + 48).plus(itemRect.size.x - percentX2, 0), Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
				/*Draw.drawString(g, "Metroid Prime 2: ", itemRect.point.plus(6, 6 + 24).plus(itemRect.size.x - 192, 0), Draw.ALIGN_TOP);
				Draw.drawString(g, "Metroid Prime 3: ", itemRect.point.plus(6, 6 + 48).plus(itemRect.size.x - 192, 0), Draw.ALIGN_TOP);*/
				g.setColor(new Color(76, 147, 179, 210));
				Draw.drawRect(g, itemRect);
				
				g.setClip(oldClip);
			}
		}
		
		if (profiles.isEmpty()) {
			g.setColor(new Color(33, 189, 222));
			g.setFont(new Font("Eurostar", Font.PLAIN, 32));
			Draw.drawString(g, "There are no profiles", position.plus(size.scaledBy(0.5)), Draw.ALIGN_CENTER | Draw.ALIGN_TOP);
		}
		
		// Draw the list frames:
		g.setColor(new Color(46, 102, 133, 210));
		Draw.drawRect(g, position, size);
		Draw.drawRect(g, position.plus(size.x, 0), new Vector(24, size.y));
		
		// Draw the scroll bar:
		if (scrollHover || scrollDragging)
			g.setColor(new Color(62, 130, 170, 210));
		Draw.fillRect(g, position.plus(size.x + 3, 3 + scrollBarPos), new Vector(20, scrollBarHeight).minus(1, 1));
	}
}
