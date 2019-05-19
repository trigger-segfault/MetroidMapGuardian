package main;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A static class that handles loading and storing images from the 'images' folder.
 * 
 * @author	Robert Jordan
 * @author	David Jordan
 */
public class ImageLoader {

	// ================== Private Variables ===================
	
	/** The number of images loaded. This is used for console output. */
	private static int imagesLoaded = 0;
	/** The map containing all the loaded images. */
	private static HashMap<String, HashMap<String, ImageResource>> imageMap = new HashMap<String, HashMap<String, ImageResource>>();

	// =================== Program Loading ====================
	
	/**
	 * Preload any images that need to be stored before the program is initialized.
	 */
	static void preloadImages() {
		// PRELOAD ALL IMAGES HERE:
		// example: loadImage("image1", "imagetest1.png");

		// Output console information:
		imagesLoaded = 0;
		System.out.println("Loading Images:");
		
		// Load the application icons:
		loadImage("icon16", "icon16.png", true, true);
		loadImage("icon24", "icon24.png", true, true);
		loadImage("icon32", "icon32.png", true, true);
		loadImage("icon48", "icon48.png", true, true);
		loadImage("icon64", "icon64.png", true, true);
		loadImage("icon96", "icon96.png", true, true);
		loadImage("icon128", "icon128.png", true, true);
		
		loadImage("s_emblem64", "s_emblem64.png", true, true);
		loadImage("s_emblem128", "s_emblem128.png", true, true);
		
		// Load the splash screen:
		//loadImage("splash", "splash.jpg", true, true);
		//loadImage("header_texture", "header_texture.png", true, true);

		// Output console information:
		System.out.println("");
		System.out.println("Loaded " + String.valueOf(imagesLoaded) + " images");
		System.out.println("--------------------------------");
	}
	/**
	 * Load the rest of the images for the program.
	 */
	static void loadImages() {
		// PRELOAD ALL IMAGES HERE:
		// example: loadImage("image1", "imagetest1.png");

		// Output console information:
		imagesLoaded = 0;
		System.out.println("Loading Images:");
				
		// Checked Image Icons:
		loadImage("checked",	"checked.png",		true, true);
		loadImage("unchecked",	"unchecked.png",	true, true);
		
		// Power Up Icons:
		loadImage("suit_expansion",			"suit_expansion.png",			true, true);
		loadImage("missile_expansion",		"missile_expansion.png",		true, true);
		loadImage("energy_tank",			"energy_tank.png",				true, true);
		loadImage("power_bomb",				"power_bomb.png",				true, true);
		loadImage("artifact",				"artifact.png",					true, true);
		loadImage("beam_ammo_expansion",	"beam_ammo_expansion.png",		true, true);
		loadImage("dark_temple_key",		"dark_temple_key.png",			true, true);
		loadImage("sky_temple_key",			"sky_temple_key.png",			true, true);
		loadImage("ship_missile_expansion",	"ship_missile_expansion.png",	true, true);
		loadImage("energy_cell",			"energy_cell.png",				true, true);
		loadImage("red_phazoid",			"red_phazoid.png",				true, true);
		loadImage("boss",					"boss.png",						true, true);
		

		// Load all basic Metroid Prime images:
		loadAllImages("metroid_prime_1/", "metroid_prime_1/", "maps/", true, true);
		loadAllImages("metroid_prime_2/", "metroid_prime_2/", "maps/", true, true);
		loadAllImages("metroid_prime_3/", "metroid_prime_3/", "maps/", true, true);
		
		// Load all Metroid Prime maps into a map group:
		loadAllImages("metroid_prime_1/maps/", "metroid_prime_1", "metroid_prime_1/maps/", "", true, false);
		loadAllImages("metroid_prime_2/maps/", "metroid_prime_2", "metroid_prime_2/maps/", "", true, false);
		loadAllImages("metroid_prime_3/maps/", "metroid_prime_3", "metroid_prime_3/maps/", "", true, false);

		// Output console information:
		System.out.println("");
		System.out.println("Loaded " + String.valueOf(imagesLoaded) + " images");
		System.out.println("--------------------------------");
	}

	// =================== Loading Methods ====================

	/**
	 * Finds the image with the given name.
	 * 
	 * @param	name - The name of the image to look for.
	 * @return	Returns the image resource or null if the image is not loaded.
	 */
	public static Image getImage(String name) {
		return getImage(name, "");
	}
	/**
	 * Finds the image with the given name.
	 * 
	 * @param	name - The name of the image to look for.
	 * @param	group - The resource group of the image.
	 * @return	Returns the image resource or null if the image is not loaded.
	 */
	public static Image getImage(String name, String group) {
		if (imageMap.containsKey(group)) {
			if (imageMap.get(group).containsKey(name)) {
				return imageMap.get(group).get(name).getResource();
			}
		}
		return null;
	}
	/**
	 * Tests whether the image with the specified name has been loaded.
	 * 
	 * @param	name - The name of the image to look for.
	 * @return	Returns true if the image has been loaded.
	 */
	public static boolean isImageLoaded(String name) {
		return isImageLoaded(name, "");
	}
	/**
	 * Tests whether the image with the specified name has been loaded.
	 * 
	 * @param	name - The name of the image to look for.
	 * @param	group - The resource group of the image.
	 * @return	Returns true if the image has been loaded.
	 */
	public static boolean isImageLoaded(String name, String group) {
		if (imageMap.containsKey(group)) {
			if (imageMap.get(group).containsKey(name)) {
				return imageMap.get(group).get(name).isLoaded();
			}
		}
		return false;
	}
	/**
	 * Loads the image with the specified name.
	 * 
	 * @param	name - The name of the image to load.
	 */
	public static void loadImage(String name) {
		loadImage(name, "");
	}
	/**
	 * Loads the image with the specified name.
	 * 
	 * @param	name - The name of the image to load.
	 * @param	group - The resource group of the image.
	 */
	public static void loadImage(String name, String group) {
		if (imageMap.containsKey(group)) {
			if (imageMap.get(group).containsKey(name)) {
				imageMap.get(group).get(name).load();
			}
		}
	}
	/**
	 * Loads all the image in specified group.
	 * 
	 * @param	group - The resource group to load.
	 */
	public static void loadGroup(String group) {
		if (imageMap.containsKey(group)) {
			for (ImageResource resource : imageMap.get(group).values()) {
				resource.load();
			}
		}
	}
	/**
	 * Unloads the image with the specified name.
	 * 
	 * @param	name - The name of the image to unload.
	 */
	public static void unloadImage(String name) {
		unloadImage(name, "");
	}
	/**
	 * Unloads the image with the specified name.
	 * 
	 * @param	name - The name of the image to unload.
	 * @param	group - The resource group of the image.
	 */
	public static void unloadImage(String name, String group) {
		if (imageMap.containsKey(group)) {
			if (imageMap.get(group).containsKey(name)) {
				imageMap.get(group).get(name).unload();
			}
		}
	}
	/**
	 * Unloads all the image in specified group.
	 * 
	 * @param	group - The resource group to unload.
	 */
	public static void unloadGroup(String group) {
		if (imageMap.containsKey(group)) {
			for (ImageResource resource : imageMap.get(group).values()) {
				resource.unload();
			}
		}
	}

	// =================== Private Methods ====================
	
	/**
	 * Load an image and stores it under a given name in the hash map.
	 * 
	 * @param	name - The name to store the image with.
	 * @param	path - The path to the image.
	 * @param	isResource - True if the image should be loaded as a resource.
	 * @param	load - True if the image should be loaded now.
	 */
	private static void loadImage(String name, String path, boolean isResource, boolean load) {
		loadImage(name, "", path, isResource, load);
	}
	/**
	 * Load an image and stores it under a given name in the hash map.
	 * 
	 * @param	name - The name to store the image with.
	 * @param	group - The group to store the image with.
	 * @param	path - The path to the image.
	 * @param	isResource - True if the image should be loaded as a resource.
	 * @param	load - True if the image should be loaded now.
	 */
	private static void loadImage(String name, String group, String path, boolean isResource, boolean load) {
		ImageResource resource = new ImageResource(path, isResource);
		if (load)
			resource.load();
		
		// Add the image to the hash map:
		// Create a new hash map group if one doesn't exist
		if (!imageMap.containsKey(group)) {
			imageMap.put(group, new HashMap<String, ImageResource>());
		}
		imageMap.get(group).put(name, resource);
		
		// Output the loaded image path to the console
		System.out.println("- " + path);
		imagesLoaded++;
	}
	/**
	 * Load all the images with a given path and stores them under a given
	 * name in the hash map.
	 * 
	 * @param	name - The name to store the images with.
	 * @param	path - The path to the images.
	 * @param	excludePath - The continued path to exclude from the search.
	 * @param	isResource - True if the image should be loaded as a resource.
	 * @param	load - True if the image should be loaded now.
	 */
	private static void loadAllImages(String name, String path, String excludePath, boolean isResource, boolean load) {
		loadAllImages(name, "", path, excludePath, isResource, load);
	}
	/**
	 * Load all the images with a given path and stores them under a given
	 * name in the hash map.
	 * 
	 * @param	name - The name to store the images with.
	 * @param	group - The group to store the images with.
	 * @param	path - The path to the images.
	 * @param	excludePath - The continued path to exclude from the search.
	 * @param	isResource - True if the image should be loaded as a resource.
	 * @param	load - True if the image should be loaded now.
	 */
	private static void loadAllImages(String name, String group, String path, String excludePath, boolean isResource, boolean load) {
		
		String imagePath = "resources/images/" + path;
		ArrayList<String> images = new ArrayList<String>();
		
		// Load all png's and jpg's:
		images.addAll(ResourceLoader.getResources("resources/images/" + path, excludePath, ".jpg", true));
		images.addAll(ResourceLoader.getResources("resources/images/" + path, excludePath, ".png", true));
		
		// For each image resource found, load the image:
		for (String fullPath : images) {
			// Get the index of the start of the path
			int index = fullPath.lastIndexOf("resources/images/" + path);
			
			String finalPath = fullPath.substring(index + imagePath.length(), fullPath.length());
			String finalName = name + finalPath.substring(0, finalPath.length() - 4);
			
			loadImage(finalName, group, path + finalPath, isResource, load);
		}
	}
}
