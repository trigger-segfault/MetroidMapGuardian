package entity;

import java.awt.Graphics2D;

import nbt.NBTTagCompound;

import room.Room;
import game.GameInstance;
import game.Trilogy;
import geometry.Rectangle;
import geometry.Vector;

/**
 * A base class for all objects that are contained in, and interact with the map.
 * 
 * @author	Robert Jordan
 *
 * @see
 * {@linkplain Map},
 * {@linkplain GameInstance}
 */
public abstract class Entity {

	// =================== Private Members ====================
	
	/**
	 * The last known direction of the entity. This is used when
	 * the entities velocity is 0.
	 */
	private double		lastDirection;
	
	// ==================== Public Members ====================
	
	/** The game this entity is linked to. */
	public GameInstance	instance;
	public Trilogy trilogy;
	/** The room that contains this entity. */
	public Room			room;
	/**
	 * A string that represents a way to access the entity without receiving
	 * information about it in advance.
	 */
	public String		id;
	/**
	 * The draw depth of the entity, entities with higher depths will draw
	 * before entities with lower depths.
	 */
	public int			depth;
	/** The position on the map of the entity. */
	public Vector		position;
	/** The speed of the entity in units per update. */
	public Vector		velocity;
	
	public Rectangle	bounds;
	/** True if the entity has been destroyed. */
	public boolean		destroyed;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	protected Entity() {
		this.instance		= null;
		this.trilogy		= null;
		this.room			= null;

		this.id				= "";
		this.depth			= 0;
		
		this.position		= new Vector();
		this.velocity		= new Vector();
		this.lastDirection	= 0.0;
		
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
	protected Entity(String id, int depth, Vector position, Vector velocity) {
		this.instance		= null;
		this.trilogy		= null;
		this.room			= null;

		this.id				= id;
		this.depth			= depth;
		
		this.position		= new Vector(position);
		this.velocity		= new Vector(velocity);
		this.lastDirection	= 0.0;
		
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
	protected Entity(NBTTagCompound nbt) {
		instance		= null;
		trilogy			= null;
		room			= null;

		id				= nbt.getString("id", "");
		depth			= nbt.getInteger("depth");
		
		if (nbt.hasKey("speed") || nbt.hasKey("direction"))
			velocity	= new Vector(nbt.getDouble("speed"), nbt.getDouble("direction"), true);
		else
			velocity	= new Vector(nbt.getDouble("hspeed"), nbt.getDouble("vspeed"));
		position		= new Vector(nbt.getDouble("x"), nbt.getDouble("y"));
		lastDirection	= nbt.getDouble("direction");
		
	}
	/**
	 * Initializes the entity by setting the game instance it is linked to.
	 * 
	 * @param	room - The room that contains the entity.
	 * @param	instance - The game instance that contains the room.
	 */
	public void initialize(Room room, GameInstance instance, Trilogy trilogy) {
		this.room		= room;
		this.trilogy	= trilogy;
		this.instance	= instance;
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
		nbt.setString(	"id",				id);
		nbt.setInteger(	"depth",			depth);
		nbt.setDouble(	"x",				position.x);
		nbt.setDouble(	"y",				position.y);
		nbt.setDouble(	"hspeed",			velocity.x);
		nbt.setDouble(	"vspeed",			velocity.y);
		nbt.setDouble(	"lastDirection",	lastDirection);
	}
	/**
	 * Saves the entity to an NBT compound tag.
	 * 
	 * @return	Returns the NBT compound containing the values on the entity.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound}
	 */
	public abstract NBTTagCompound saveEntity();
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
	public abstract Entity loadEntity(NBTTagCompound nbt);
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the entity to perform actions and update.
	 */
	public void update() {
		
		// Update position
		position.add(velocity);
		
	}
	/**
	 * Called every step for the entity to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
	}
	/**
	 * Destroys the entity and marks it for removal.
	 */
	public void destroy() {
		destroyed = true;
	}
	
}

