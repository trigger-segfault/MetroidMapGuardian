package room;

import java.awt.Graphics2D;
import java.util.ArrayList;

import nbt.NBTElement;
import nbt.NBTTagCompound;
import nbt.NBTTagList;
import entity.Entity;
import entity.EntityLoader;

import game.GameInstance;
import game.Trilogy;
import graphics.Gui;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class Room {

	// ====================== Constants =======================

	/** The max amount of entities allowed to be contained within the room. */
	public static final int MAX_ENTITIES		= 10000;
	/** The max amount of guis allowed to be contained within the room. */
	public static final int MAX_GUIS			=  1000;
	
	// ======================= Members ========================
	
	/** The name identifier of the room. */
	public String		id;
	/** The instance that contains the room. */
	public Trilogy		trilogy;
	/** The instance that contains the room. */
	public GameInstance	instance;
	
	/** The list of entities contained in the room. */
	public ArrayList<Entity>	entities;
	/** The list of GUIs contained in the room. */
	public ArrayList<Gui>		guis;
	/** The view pan and zoom in the room. */
	public View		view;
	/** Used to determine if the entity can interact with the mouse. */
	public boolean	isMouseOverGUI;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a basic room.
	 * 
	 * @return	Returns the default room.
	 */
	public Room() {
		id			= "";
		trilogy		= null;
		instance	= null;
		entities	= new ArrayList<Entity>();
		guis		= new ArrayList<Gui>();
		view		= new View();
		
		isMouseOverGUI	= false;
	}
	/**
	 * Constructs the room from an NBT compound of values. This is mainly used
	 * to load entire rooms from file in NBT format.
	 * 
	 * @param	nbt - The compound containing the tags related to the room.
	 * @return	Returns the room constructed from tag data.
	 * 
	 * @see
	 * {@linkplain NBTElement},
	 * {@linkplain NBTTagCompound}
	 */
	public Room(NBTTagCompound nbt) {
		id			= nbt.getString("id", "");
		trilogy		= null;
		instance	= null;
		entities	= new ArrayList<Entity>();
		guis		= new ArrayList<Gui>();
		view		= new View();
		
		isMouseOverGUI	= false;
		
		for (NBTElement tag : nbt.getList("entities").getTags()) {
			entities.add(EntityLoader.loadEntity((NBTTagCompound)tag));
		}
	}
	/**
	 * Initializes the room by setting the game instance it is linked to.
	 * 
	 * @param	instance - The game instance that contains the room.
	 */
	public void initialize(GameInstance instance, Trilogy trilogy) {
		this.trilogy	= trilogy;
		this.instance	= instance;
		
		for (Entity e : entities) {
			e.initialize(this, instance, trilogy);
		}

		for (Gui g : guis) {
			g.initialize(this, instance, trilogy);
		}
	}
	
	// ===================== NBT File IO ======================
	
	/**
	 * Saves tag information about the room to the NBT compound.
	 * 
	 * @param	nbt - The NBT compound to save tag information to.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound}
	 */
	protected void saveRoom(NBTTagCompound nbt) {
		nbt.setString("id", id);
		NBTTagList entityTags = new NBTTagList("entities", NBTElement.TAG_COMPOUND);
		for (Entity e : entities) {
			entityTags.addCompound(e.saveEntity());
		}
		nbt.setList("entities", entityTags);
	}
	/**
	 * Saves the room to an NBT compound tag.
	 * 
	 * @return	Returns the NBT compound containing the values on the room.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound}
	 */
	public NBTTagCompound saveRoom() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("class", "Room");
		saveRoom(nbt);
		return nbt;
	}
	/**
	 * Creates a room based on the information given from an NBT compound. The
	 * room must first be defined in RoomLoader in order to be loaded from an
	 * nbt file.
	 * 
	 * @param	nbt - The NBT compound containing tag information on the room.
	 * @return	Returns a room constructed from the specified tag information.
	 * 
	 * @see
	 * {@linkplain NBTTagCompound},
	 * {@linkplain RoomLoader}
	 */
	public Room loadRoom(NBTTagCompound nbt) {
		return new Room(nbt);
	}

	// ======================= Updating =======================
	
	/**
	 * Called every step for the room to perform actions and update.
	 */
	public void update() {
		
		// This is considered false until a GUI changes it.
		isMouseOverGUI	= false;
		
		// Update the GUIs
		for (Gui g : guis) {
			if (!g.destroyed) {
				g.update();
			}
		}
		// Remove any GUIs that have been destroyed
		for (int i = 0; i < guis.size(); i++) {
			if (guis.get(i).destroyed) {
				guis.remove(i);
				i--;
			}
		}
		
		// Update the entities
		for (Entity e : entities) {
			if (!e.destroyed) {
				e.update();
			}
		}
		// Remove any entities that have been destroyed
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).destroyed) {
				entities.remove(i);
				i--;
			}
		}
		
	}
	/**
	 * Called every step for the room to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
		// Draw the entities
		for (Entity e : entities) {
			e.draw(g);
		}
		
		// Draw the GUIs
		for (Gui gu : guis) {
			gu.draw(g);
		}
	}

	// ======================= Entities =======================
	
	/**
	 * Adds the entity to the room and sets its game instance and room variable.
	 * 
	 * @param	e - The entity to add to the room.
	 */
	public void addEntity(Entity e) {
		if (entities.size() < MAX_ENTITIES) {
			entities.add(e);
			e.initialize(this, instance, trilogy);
		}
	}
	/**
	 * Finds the entity with the given id.
	 * 
	 * @param	id - The key identifier used to locate the entity.
	 * @return	Returns the entity with the given id.
	 */
	public Entity getEntity(String id) {
		for (Entity e : entities) {
			if (e.id == id) {
				return e;
			}
		}
		return null;
	}
	/**
	 * Returns true if the entity with the given id exists.
	 * 
	 * @param	id - The key identifier used to locate the entity.
	 * @return	Returns true if the entity with the given id exists.
	 */
	public boolean entityExists(String id) {
		for (Entity e : entities) {
			if (e.id == id) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns true if the entity exists in the room.
	 * 
	 * @param	e - The entity to check for existence.
	 * @return	Returns true if the entity exists in the room.
	 */
	public boolean entityExists(Entity e) {
		return entities.contains(e);
	}
	/**
	 * Returns the number of entities in the room.
	 * 
	 * @return	Returns the number of entities in the room.
	 */
	public int numEntities() {
		return entities.size();
	}
	
	// ========================= Guis =========================
	
	/**
	 * Adds the GUI to the room and sets its game instance and room variable.
	 * 
	 * @param	e - The GUI to add to the room.
	 */
	public void addGui(Gui g) {
		if (guis.size() < MAX_GUIS) {
			guis.add(g);
			g.initialize(this, instance, trilogy);
		}
	}
	/**
	 * Finds the GUI with the given id.
	 * 
	 * @param	id - The key identifier used to locate the GUI.
	 * @return	Returns the GUI with the given id or null if the id does not exist.
	 */
	public Gui getGui(String id) {
		for (Gui g : guis) {
			if (g.id == id) {
				return g;
			}
		}
		return null;
	}
	/**
	 * Returns true if the GUI with the given id exists.
	 * 
	 * @param	id - The key identifier used to locate the GUI.
	 * @return	Returns true if the GUI with the given id exists.
	 */
	public boolean guiExists(String id) {
		for (Gui g : guis) {
			if (g.id == id) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns true if the GUI exists in the room.
	 * 
	 * @param	e - The GUI to check for existence.
	 * @return	Returns true if the GUI exists in the room.
	 */
	public boolean guiExists(Gui g) {
		return guis.contains(g);
	}
	/**
	 * Returns the number of GUIs in the room.
	 * 
	 * @return	Returns the number of GUIs in the room.
	 */
	public int numGuis() {
		return guis.size();
	}
	
}


