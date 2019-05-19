package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import room.Room;
import game.GameInstance;
import game.PowerUp;
import game.Profile;
import game.Trilogy;
import geometry.Rectangle;
import geometry.Vector;
import graphics.Draw;
import graphics.MarkerDetails;
import graphics.PopupMarkerDetails;
import graphics.ScalableImage;
import main.Keyboard;
import main.Mouse;
import nbt.NBTTagCompound;

/**
 * The marker used for checking off items completed on the map. Each marker
 * stores and index and type of the power up, the power up information is
 * then looked up based on that information.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain PowerUp}
 */
public class Marker extends Entity {

	// ==================== Static Members ====================
	
	/** The image to display when the marker has been checked off. */
	public static ScalableImage imageChecked	= new ScalableImage("checked", "",
			BufferedImage.TYPE_INT_ARGB, RenderingHints.VALUE_INTERPOLATION_BICUBIC, 1.0);
	/** The image to display when the marker is unchecked and unchecked markers are visible. */
	public static ScalableImage imageUnchecked	= new ScalableImage("unchecked", "",
			BufferedImage.TYPE_INT_ARGB, RenderingHints.VALUE_INTERPOLATION_BICUBIC, 1.0);
	
	// ======================= Members ========================

	/** The name of the marker type. */
	public int type;
	/** The index of the marker. */
	public int index;
	/** The secondary type of the marker. */
	public int linkedType;
	/** The secondary index of the marker. */
	public int linkedIndex;
	
	/** True if the mouse is hovering over the marker. */
	public boolean hovering;
	/** A debug feature used in the editor. */
	public boolean placing;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a marker.
	 * 
	 * @return	Returns the default marker.
	 */
	public Marker() {
		super();
		this.type			= 0;
		this.index			= 0;
		this.linkedType		= -1;
		this.linkedIndex	= 0;
		
		this.hovering		= false;
		this.placing		= false;
	}
	/**
	 * Constructs a marker at the given coordinates.
	 * 
	 * @param	type - The name of the marker type.
	 * @param	index - The index of the marker.
	 * @return	Returns a marker at the given coordinates.
	 */
	public Marker(int type, int index) {
		super();
		this.type			= type;
		this.index			= index;
		this.linkedType		= -1;
		this.linkedIndex	= 0;
		
		this.hovering		= false;
		this.placing		= false;
	}
	/**
	 * Constructs the entity from an NBT compound of values. This is mainly used
	 * To load entire maps from file in NBT format.
	 * 
	 * @param	nbt - The compound containing the tags related to the entity.
	 * @return	Returns the entity constructed from tag data.
	 * 
	 * @see
	 * {@linkplain NBTElement},
	 * {@linkplain NBTTagCompound}
	 */
	public Marker(NBTTagCompound nbt) {
		super(nbt);
		this.type			= nbt.getInteger("type", 0);
		this.index			= nbt.getInteger("index", 0);
		this.linkedType		= nbt.getInteger("linkedType", -1);
		this.linkedIndex	= nbt.getInteger("linkedIndex", 0);
		
		this.hovering		= false;
		this.placing		= false;
		
		if (linkedType == type) {
			linkedType = -1;
			linkedIndex = 0;
		}
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
	
	// ===================== NBT File IO ======================

	/**
	 * Saves tag information about the entity to the NBT compound.
	 * 
	 * @param	nbt - The NBT compound to save tag information to.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound}
	 */
	public void saveEntity(NBTTagCompound nbt) {
		super.saveEntity(nbt);
		
		nbt.setInteger(	"type",			type);
		nbt.setInteger(	"index",		index);
		nbt.setInteger(	"linkedType",	linkedType);
		nbt.setInteger(	"linkedIndex",	linkedIndex);
	}
	/**
	 * Saves the entity to an NBT compound tag.
	 * 
	 * @return	Returns the NBT compound containing the values on the entity.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound}
	 */
	public NBTTagCompound saveEntity() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("class", "Marker");
		saveEntity(nbt);
		return nbt;
	}
	/**
	 * Creates an entity based on the information given from an NBT compound. The
	 * entity must first be defined in EntityLoader in order to be loaded from a
	 * map file.
	 * 
	 * @param	nbt - The NBT compound containing tag information on the entity.
	 * @return	Returns an entity constructed from the specified tag information.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound},
	 * {@linkplain EntityLoader}
	 */
	public Marker loadEntity(NBTTagCompound nbt) {
		return new Marker(nbt);
	}

	// ======================== Updating ========================
	
	/**
	 * Called every step for the entity to perform actions and update.
	 */
	public void update() {

		double radius = (double)imageChecked.getWidth() / 2.0;
		Vector radiusVec = new Vector(radius, radius);
		Rectangle rect = new Rectangle(position.minus(radiusVec), radiusVec.scaledBy(2.0));
		
		if (rect.contains(room.view.getMousePoint()) && !room.isMouseOverGUI) {
			// Create the marker display
			if (!hovering) {
				room.addGui(new MarkerDetails(this));
			}
			hovering = true;
			
			if (Mouse.left.pressed() && !instance.editorMode) {
				trilogy.checkPowerUp(type, index);
				if (linkedType != -1)
					trilogy.checkPowerUp(linkedType, linkedIndex);
				new Profile(instance).saveProfile();
			}
			else if (Mouse.right.pressed() && !instance.editorMode) {
				room.addGui(new PopupMarkerDetails(this));
			}
		}
		else {
			hovering = false;
		}
			
		if (instance.editorMode) {
			try {
				if (placing) {
					position = room.view.getMousePoint();
				if (Mouse.middle.released())
					placing = false;
				}
				else if (hovering) {
					if (Mouse.left.pressed()) {
						if (Keyboard.control.down() && !Keyboard.shift.down()) {
							String num = JOptionPane.showInputDialog(null, "New Marker Index", Integer.toString(index + 1), JOptionPane.OK_CANCEL_OPTION);
							index = Integer.parseInt(num) - 1;
							Keyboard.reset();
							Mouse.reset();
						}
						else if (Keyboard.control.down() && Keyboard.shift.down()) {
							String num = JOptionPane.showInputDialog(null, "New Marker Linked Index", Integer.toString(linkedIndex + 1), JOptionPane.OK_CANCEL_OPTION);
							linkedIndex = Integer.parseInt(num) - 1;
							Keyboard.reset();
							Mouse.reset();
						}
						else if (!Keyboard.control.down() && Keyboard.shift.down()) {
							String num = JOptionPane.showInputDialog(null, "New Marker Linked Type", Integer.toString(linkedType), JOptionPane.OK_CANCEL_OPTION);
							linkedType = Integer.parseInt(num);
							Keyboard.reset();
							Mouse.reset();
						}
						else {
							trilogy.checkPowerUp(type, index);
							if (linkedType != -1)
								trilogy.checkPowerUp(linkedType, linkedIndex);
						}
					}
					else if (Mouse.right.pressed()) {
						destroy();
					}
				}
			}
			catch (NumberFormatException e) {
				
			}
		}
		
		super.update();
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
		double radius = (double)imageChecked.getWidth() / 2.0 * imageChecked.getScale();
		Vector point = room.view.getViewPoint(position);
		
		Vector radiusVec = new Vector(radius, radius);
		
		// Draw check image
		if (getPowerUp().collected)
			Draw.drawImage(g, imageChecked.getScaledImage(), point.minus(radiusVec));
		else if(instance.showUncheckedMarkers)
			Draw.drawImage(g, imageUnchecked.getScaledImage(), point.minus(radiusVec));
		
		// Draw the bright circle to emphasize highlighting
		if (hovering) {
			g.setPaint(new RadialGradientPaint((float)point.x, (float)point.y, (float)radius, new float[]{0.0f, 1.0f},
					new Color[]{new Color(255, 255, 255, 200), new Color(255, 255, 255, 20)}));
			
			Draw.fillEllipse(g, point, radiusVec, true);
		}
		
		// Draw a red circle to aid in placing
		if (placing) {
			g.setColor(new Color(255, 0, 0));
			Draw.drawEllipse(g, point, radiusVec, true);
		}
		
	}
	
	// ==================== Marker Methods ====================
	
	/**
	 * Called when the mouse is hovering over the marker.
	 */
	public PowerUp getPowerUp() {
		return trilogy.getPowerUp(type, index);
	}
	/**
	 * Called when the mouse is hovering over the marker.
	 */
	public PowerUp getLinkedPowerUp() {
		return trilogy.getPowerUp(linkedType, linkedIndex);
	}
	public boolean hasLinkedPowerUp() {
		return linkedType != -1;
	}
	
}


