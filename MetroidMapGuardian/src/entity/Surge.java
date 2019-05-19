package entity;

import game.GameInstance;
import game.Trilogy;
import geometry.Vector;
import graphics.Draw;

import java.awt.Color;
import java.awt.Graphics2D;

import room.Room;
import main.Main;
import nbt.NBTTagCompound;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class Surge extends Entity {
	
	// ======================= Members ========================

	/** The length of the surge. */
	public int length;

	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a marker.
	 * 
	 * @return	Returns the default marker.
	 */
	public Surge() {
		super();
		this.length = 80;
		this.velocity.y = 2;
	}
	/**
	 * Constructs a marker at the given coordinates.
	 * 
	 * @param	type - The name of the marker type.
	 * @param	index - The index of the marker.
	 * @return	Returns a marker at the given coordinates.
	 */
	public Surge(double x, double vspeed, int length) {
		super();
		this.length = length;
		this.velocity.y = vspeed;
		if (vspeed < 0.0)
			this.position = new Vector(x, Main.frame.getHeight());
		else
			this.position = new Vector(x, -length);
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
	public Surge(NBTTagCompound nbt) {
		super(nbt);
		this.length		= nbt.getInteger("length");
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
		
		nbt.setInteger(	"length",	length);
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
		nbt.setString("class", "Surge");
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
	public Surge loadEntity(NBTTagCompound nbt) {
		return new Surge(nbt);
	}

	// ======================== Updating ========================
	
	/**
	 * Called every step for the entity to perform actions and update.
	 */
	public void update() {
		super.update();
		
		if ((velocity.y < 0.0 && position.y < -length) ||
			(velocity.y > 0.0 && position.y > Main.frame.getContentPane().getHeight())) {
			destroy();
		}
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		g.setColor(new Color(50, 70, 100));
		Draw.drawLine(g, position, position.plus(0, length));
	}

}
