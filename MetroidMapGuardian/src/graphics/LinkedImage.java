package graphics;

import geometry.Vector;

import java.awt.Image;

import main.ImageLoader;

/**
 * An image linked to a resource image. This way the image can exist even
 * if the resource is not loaded.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain ImageLoader},
 * {@linkplain ImageResource}
 */
public class LinkedImage {

	// ==================== Public Members ====================
	
	/** The name of the image. */
	protected String resourceName;
	/** The group of the image. */
	protected String resourceGroup;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a linked image with no name.
	 * 
	 * @return	Returns the default linked image.
	 */
	public LinkedImage() {
		this.resourceName	= "";
		this.resourceGroup	= "";
	}
	/**
	 * Constructs a linked image with the specified name.
	 * 
	 * @param	name - The name of the image.
	 * @return	Returns a linked image with the specified name.
	 */
	public LinkedImage(String name, String group) {
		this.resourceName	= name;
		this.resourceGroup	= group;
	}
	
	// ======================= Methods ========================
	
	/**
	 * Gets the image with the given name.
	 * 
	 * @return	Returns the image or null if the image has not been loaded.
	 */
	public Image getImage() {
		return ImageLoader.getImage(resourceName, resourceGroup);
	}
	/**
	 * Gets the width of the image.
	 * 
	 * @return	Returns the image's width.
	 */
	public int getWidth() {
		Image image = ImageLoader.getImage(resourceName, resourceGroup);
		if (image != null)
			return image.getWidth(null);
		
		return 0;
	}
	/**
	 * Gets the height of the image.
	 * 
	 * @return	Returns the image's height.
	 */
	public int getHeight() {
		Image image = ImageLoader.getImage(resourceName, resourceGroup);
		if (image != null)
			return image.getHeight(null);
		
		return 0;
	}
	/**
	 * Gets the height of the image.
	 * 
	 * @return	Returns the image's height.
	 */
	public Vector getSize() {
		Image image = ImageLoader.getImage(resourceName, resourceGroup);
		if (image != null)
			return new Vector(image.getWidth(null), image.getHeight(null));
		
		return new Vector();
	}
}
