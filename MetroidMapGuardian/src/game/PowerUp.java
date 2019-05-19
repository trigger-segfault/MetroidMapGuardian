package game;

import java.awt.Color;
import java.awt.Image;

import main.ImageLoader;

/**
 * The class used to store data about each collectable in each game. PowerUps
 * are referenced by Marker entities in maps.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain Marker}
 */
public class PowerUp {
	
	// =================== Static Variables ===================
	
	/** The number of power up types. */
	public static final int NUM_TYPES				= 11;

	public static final int SUIT_EXPANSION			= 0;
	public static final int MISSILE_EXPANSION		= 1;
	public static final int ENERGY_TANK				= 2;
	public static final int POWER_BOMB				= 3;
	
	public static final int ARTIFACT				= 4;
	
	public static final int BEAM_AMMO_EXPANSION		= 5;
	public static final int TEMPLE_KEY				= 6;
	public static final int SKY_TEMPLE_KEY			= 7;

	public static final int SHIP_MISSILE_EXPANSION	= 8;
	public static final int ENERGY_CELL				= 9;
	public static final int RED_PHAZOID				= 10;
	
	// ======================= Members ========================

	/** The type of the power up. */
	public int type;
	/** The name of the power up. */
	public String name;
	/** The location of the power up. */
	public String location;
	/** The description to acquire the power up. */
	public String description;
	/** True if the power up has been checked off. */
	public boolean collected;
	
	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a power up as a suit expansion.
	 * 
	 * @return	Returns the default power up.
	 */
	public PowerUp() {
		this.type			= 0;
		this.name			= "";
		this.location		= "";
		this.description	= "";
		this.collected		= false;
	}
	/**
	 * Constructs a power up of the given type.
	 * 
	 * @param	type - The type of power up.
	 * @return	Returns the power up of the specified type.
	 */
	public PowerUp(int type) {
		this.type			= type;
		this.name			= "";
		this.location		= "";
		this.description	= "";
		this.collected		= false;
	}
	
	// ======================= Methods ========================

	/**
	 * Gets the type name.
	 * 
	 * @return	Returns the name of the type.
	 */
	public String getTypeName() {
		return getPowerUpName(type);
	}
	/**
	 * Gets the type color used for the marker on the mini map.
	 * 
	 * @return	Returns the mini map marker color.
	 */
	public Color getTypeColor() {
		switch (type) {
		/* Global Power Ups */
		case SUIT_EXPANSION:			return new Color(255, 233, 0);
		case MISSILE_EXPANSION:			return new Color(255, 127, 39);
		case ENERGY_TANK:				return new Color(102, 232, 255);
		case POWER_BOMB:				return new Color(154, 58, 156);

		/* Metroid Prime 1 */
		case ARTIFACT:					return new Color(188, 176, 95);

		/* Metroid Prime 2 */
		case BEAM_AMMO_EXPANSION:		return new Color(157, 2, 251);
		case TEMPLE_KEY:				return new Color(243, 82, 82);
		case SKY_TEMPLE_KEY:			return new Color(96, 204, 201);
		
		/* Metroid Prime 3 */
		case SHIP_MISSILE_EXPANSION:	return new Color(32, 220, 5);
		case ENERGY_CELL:				return new Color(36, 217, 208);
		case RED_PHAZOID:				return new Color(255, 74, 74);
		}
		return null;
	}
	
	// ==================== Static Methods ====================
	
	/**
	 * Tests whether the power up is in the specified game.
	 * 
	 * @param	type - The power up type.
	 * @param	game - The game to check with.
	 * @return	Returns true if the power up type is in the game.
	 */
	public static boolean isPowerUpInGame(int type, int game) {
		switch (type) {
		/* Global Power Ups */
		case SUIT_EXPANSION:			return true;
		case MISSILE_EXPANSION:			return true;
		case ENERGY_TANK:				return true;
		case POWER_BOMB:				return (game == 1 || game == 2);

		/* Metroid Prime 1 */
		case ARTIFACT:					return (game == 1);

		/* Metroid Prime 2 */
		case BEAM_AMMO_EXPANSION:		return (game == 2);
		case TEMPLE_KEY:				return (game == 2);
		case SKY_TEMPLE_KEY:			return (game == 2);
		
		/* Metroid Prime 3 */
		case SHIP_MISSILE_EXPANSION:	return (game == 3);
		case ENERGY_CELL:				return (game == 3);
		case RED_PHAZOID:				return (game == 3);
		}
		return false;
	}
	/**
	 * Gets the image icon for the power up type.
	 * 
	 * @param	type - The power up type.
	 * @return	Returns the image of the power up type.
	 */
	public static Image getPowerUpIcon(int type) {
		switch (type) {
		/* Global Power Ups */
		case SUIT_EXPANSION:			return ImageLoader.getImage("suit_expansion");
		case MISSILE_EXPANSION:			return ImageLoader.getImage("missile_expansion");
		case ENERGY_TANK:				return ImageLoader.getImage("energy_tank");
		case POWER_BOMB:				return ImageLoader.getImage("power_bomb");

		/* Metroid Prime 1 */
		case ARTIFACT:					return ImageLoader.getImage("artifact");

		/* Metroid Prime 2 */
		case BEAM_AMMO_EXPANSION:		return ImageLoader.getImage("beam_ammo_expansion");
		case TEMPLE_KEY:				return ImageLoader.getImage("dark_temple_key");
		case SKY_TEMPLE_KEY:			return ImageLoader.getImage("sky_temple_key");
		
		/* Metroid Prime 3 */
		case SHIP_MISSILE_EXPANSION:	return ImageLoader.getImage("ship_missile_expansion");
		case ENERGY_CELL:				return ImageLoader.getImage("energy_cell");
		case RED_PHAZOID:				return ImageLoader.getImage("red_phazoid");
		}
		return null;
	}
	/**
	 * Gets the raw name for the power up type.
	 * 
	 * @param	type - The power up type.
	 * @return	Returns the raw name of the power up type.
	 */
	public static String getPowerUpRawName(int type) {
		switch (type) {
		/* Global Power Ups */
		case SUIT_EXPANSION:			return "suit_expansions";
		case MISSILE_EXPANSION:			return "missile_expansions";
		case ENERGY_TANK:				return "energy_tanks";
		case POWER_BOMB:				return "power_bombs";

		/* Metroid Prime 1 */
		case ARTIFACT:					return "artifacts";

		/* Metroid Prime 2 */
		case BEAM_AMMO_EXPANSION:		return "beam_ammo_expansions";
		case TEMPLE_KEY:				return "dark_temple_keys";
		case SKY_TEMPLE_KEY:			return "sky_temple_keys";
		
		/* Metroid Prime 3 */
		case SHIP_MISSILE_EXPANSION:	return "ship_missile_expansions";
		case ENERGY_CELL:				return "energy_cells";
		case RED_PHAZOID:				return "red_phazoids";
		}
		return "";
	}
	/**
	 * Gets the name for the power up type.
	 * 
	 * @param	type - The power up type.
	 * @return	Returns the name of the power up type.
	 */
	public static String getPowerUpName(int type) {
		switch (type) {
		/* Global Power Ups */
		case SUIT_EXPANSION:			return "Suit Expansion";
		case MISSILE_EXPANSION:			return "Missile Expansion";
		case ENERGY_TANK:				return "Energy Tank";
		case POWER_BOMB:				return "Power Bomb";

		/* Metroid Prime 1 */
		case ARTIFACT:					return "Artifact";

		/* Metroid Prime 2 */
		case BEAM_AMMO_EXPANSION:		return "Beam Ammo Expansion";
		case TEMPLE_KEY:				return "Dark Temple Key";
		case SKY_TEMPLE_KEY:			return "Sky Temple Key";
		
		/* Metroid Prime 3 */
		case SHIP_MISSILE_EXPANSION:	return "Ship Missile Expansion";
		case ENERGY_CELL:				return "Energy Cell";
		case RED_PHAZOID:				return "Red Phaazoid";
		}
		return "";
	}
}
