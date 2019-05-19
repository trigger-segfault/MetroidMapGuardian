package main;

/**
 * A basic resource that can be stored and loaded or unloaded in order to
 * manage memory. Each resource needs to have a name, path, and boolean of
 * whether it's loaded as a resource, or file.
 * @author	Robert Jordan
 * @see
 * {@linkplain ImageResource}
 */
public abstract class Resource {

	// ======================= Members ========================
	
	/** The path of the resource. */
	protected String	resourcePath;
	/** True if the resource should be loaded as a resource. */
	protected boolean	isResource;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an unloaded resource.
	 * 
	 * @return	Returns the default resource.
	 */
	protected Resource() {
		this.resourcePath	= "";
		this.isResource		= true;
	}
	/**
	 * Constructs a resource with the given name, path, and resource state.
	 * 
	 * @param	name - The name of the resource.
	 * @param	path - The path to the resource.
	 * @param	isResource - True if the resource should be loaded as a
	 * resource instead of as a file.
	 * @return	Returns an unloaded resource with the given file details.
	 */
	protected Resource(String path, boolean isResource) {
		this.resourcePath	= path;
		this.isResource		= isResource;
	}
	
	// ======================= Methods ========================
	
	/**
	 * Tests whether the resource has been loaded.
	 * 
	 * @return	Returns true if the resource is currently loaded.
	 */
	public abstract boolean isLoaded();
	/**
	 * Loads the resource from file.
	 */
	public abstract void load();
	/**
	 * Unloads the resource and removes all attached data to save memory.
	 */
	public abstract void unload();
	/**
	 * Gets the resource with the given name.
	 * 
	 * @return	Returns the resource object or null if it's not loaded.
	 */
	public abstract Object getResource();
	
}
