package game;

import java.util.ArrayList;
import java.util.Scanner;

import main.ImageLoader;
import nbt.NBTElement;
import nbt.NBTTagCompound;

import room.Map;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class Trilogy {

	// ================== Private Variables ===================
	
	
	// =================== Public Variables ===================
	
	public static final int METROID_PRIME				= 1;
	public static final int METROID_PRIME_ECHOES		= 2;
	public static final int METROID_PRIME_CORRUPTION	= 3;
	
	// ======================= Members ========================

	/** The game instance that contains this trilogy. */
	public GameInstance instance;
	/** The list of maps for this trilogy. */
	public ArrayList<Map> maps;
	/** The index of the current map. */
	public int currentMap;
	/** The current map. */
	public Map map;
	
	/** The collection of power up descriptions and settings. */
	public ArrayList<ArrayList<PowerUp>> powerUps;
	/** The index of the game. */
	public int game;
	/** The name of the game. */
	public String name;
	/** The raw name of the game. */
	public String rawName;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public Trilogy(NBTTagCompound nbt) {
		
		this.name = nbt.getString("name");
		this.rawName = nbt.getString("rawName");
		this.game = nbt.getByte("game");
		this.maps = new ArrayList<Map>();
		this.currentMap = 0;
		
		this.powerUps = new ArrayList<ArrayList<PowerUp>>();
		for (int i = 0; i < PowerUp.NUM_TYPES; i++) {
			this.powerUps.add(new ArrayList<PowerUp>());
		}
		
		// Load the power up data
		for (int i = 0; i < PowerUp.NUM_TYPES; i++) {
			if (PowerUp.isPowerUpInGame(i, game)) {
				try (Scanner reader = new Scanner(Trilogy.class.getResourceAsStream("/resources/text/" + this.rawName + "/" + PowerUp.getPowerUpRawName(i) + ".txt"))) {
					
					while (reader.hasNextLine()) {
						PowerUp powerUp = new PowerUp(i);
						reader.nextLine();
						powerUp.name = reader.nextLine();
						powerUp.location = reader.nextLine();
						powerUp.description = reader.nextLine();
						
						this.powerUps.get(i).add(powerUp);
					}
				}
			}
		}
		
		// Load the maps
		for (NBTElement tag : nbt.getList("maps", null).getTags()) {
			this.maps.add(new Map((NBTTagCompound)tag));
		}
		
		this.map = this.maps.get(0);
	}
	/**
	 * Initializes the entity by setting the game instance it is linked to.
	 * 
	 * @param	room - The room that contains the entity.
	 * @param	instance - The game instance that contains the room.
	 */
	public void initialize(GameInstance instance) {
		this.instance	= instance;
		
		for (Map m : maps) {
			m.initialize(instance, this);
		}
	}
	
	// ======================= Methods ========================
	
	public void changeMap(int index) {
		map.image.unloadScaledImage();
		ImageLoader.unloadImage(rawName + "/maps/" + map.rawName, rawName);
		currentMap = index;
		map = maps.get(index);
		ImageLoader.loadImage(rawName + "/maps/" + map.rawName, rawName);
	}
	/**
	 * Gets the power up details from the list of power ups
	 * 
	 * @param	type - The type of power up.
	 * @param	index - The index of the power up.
	 * @return	Returns the power up of the given type or an empty power up if
	 * 			the index was out of range.
	 */
	public PowerUp getPowerUp(int type, int index) {
		if (index >= 0 && index < powerUps.get(type).size()) {
			return powerUps.get(type).get(index);
		}
		return new PowerUp(type);
	}
	/**
	 * Checks or unchecks the power up of the given type.
	 * 
	 * @param	type - The type of power up.
	 * @param	index - The index of the power up.
	 */
	public void checkPowerUp(int type, int index) {
		if (index >= 0 && index < powerUps.get(type).size()) {
			powerUps.get(type).get(index).collected = !powerUps.get(type).get(index).collected;
		}
	}
	
	public static String getRawName(int game) {
		switch (game) {
		case METROID_PRIME:				return "metroid_prime_1";
		case METROID_PRIME_ECHOES:		return "metroid_prime_2";
		case METROID_PRIME_CORRUPTION:	return "metroid_prime_3";
		}
		return "";
	}
	
	public int getCompletionPercentage() {
		
		int total = 0;
		int collected = 0;
		
		for (int i = 0; i < powerUps.size(); i++) {
			for (int j = 0; j < powerUps.get(i).size(); j++) {
				total++;
				if (powerUps.get(i).get(j).collected)
					collected ++;
			}
		}
		return (int)((double)collected / total * 100);
	}
	
}

