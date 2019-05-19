package entity;

import java.util.HashMap;

import nbt.NBTTagCompound;

public class EntityLoader {

	// ==================== Static Members ====================
	
	/** The map that contains entities linked to class names. */
	private static HashMap<String, Entity> entityMap = new HashMap<String, Entity>();

	// ==================== Static Methods ====================

	/**
	 * Registers a class name with the specified entity so it can be linked
	 * during map construction.
	 * 
	 * @param	className - The class name for the entity.
	 * @param	entity - The entity class to register.
	 */
	private static void register(String className, Entity entity) {
		entityMap.put(className, entity);
	}
	/**
	 * Called at the beginning of the program to register and link all class
	 * names to entities.
	 */
	public static void registerEntities() {
		
		// Living
		register("Marker",		new Marker());
	}
	/**
	 * Creates an entity based on the information given from an NBT compound.
	 * 
	 * @param	nbt - The NBT compound containing tag information on the entity.
	 * @return	Returns the loaded entity.
	 */
	public static Entity loadEntity(NBTTagCompound nbt) {
		
		String className = nbt.getString("class", "");
		
		if (entityMap.containsKey(className))
			return entityMap.get(className).loadEntity(nbt);
		
		return null;
	}
	
}
