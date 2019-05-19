package graphics;

import game.GameInstance;
import game.Trilogy;
import geometry.Rectangle;
import geometry.Vector;

import java.awt.Graphics2D;
import room.Room;

/**
 * 
 * @author Jrob
 *
 */
public abstract class Gui {
	
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
	
	public Rectangle	bounds;
	/** True if the entity has been destroyed. */
	public boolean		destroyed;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	protected Gui() {
		this.instance		= null;
		this.trilogy		= null;
		this.room			= null;

		this.id				= "";
		this.depth			= 0;
		
		this.position		= new Vector();
		
		this.destroyed		= false;
	}
	/**
	 * Constructs an entity with the specified details.
	 * 
	 * @param	id - The key identifier of the entity.
	 * @param	depth - The depth of the entity.
	 * @param	position - The position of the entity.
	 * @return	Returns an entity with the specified details.
	 */
	protected Gui(String id, int depth, Vector position) {
		this.instance		= null;
		this.trilogy		= null;
		this.room			= null;

		this.id				= id;
		this.depth			= depth;
		
		this.position		= new Vector(position);
		
		this.destroyed		= false;
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
	/*protected Gui(NBTTagCompound nbt) {
		instance		= null;
		room			= null;
		id				= nbt.getString("id", "");
		depth			= nbt.getInteger("depth", 0);
		position		= new Vector(nbt.getDouble("x", 0.0), nbt.getDouble("y", 0.0));
		
		destroyed = false;
	}*/
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
	/*public void saveGui(NBTTagCompound nbt) {
		nbt.setString(	"id",				id);
		nbt.setInteger(	"depth",			depth);
		nbt.setDouble(	"x",				position.x);
		nbt.setDouble(	"y",				position.y);
	}*/
	/**
	 * Saves the entity to an NBT compound tag.
	 * 
	 * @return	Returns the NBT compound containing the values on the entity.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound}
	 */
	//public abstract NBTTagCompound saveGui();
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
	//public abstract Gui loadGui(NBTTagCompound nbt);
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the entity to perform actions and update.
	 */
	public void update() {
		
		
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
