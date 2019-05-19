package graphics;

import geometry.Vector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import main.ImageLoader;


/**
 * A linked image that can be scaled with ease. The last scaled image is
 * stored in the class.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain LinkedImage}
 */
public class ScalableImage extends LinkedImage {

	// ======================= Members ========================
	
	/** The new scaled image. */
	private BufferedImage	scaledImage;
	/** The currently used scale. */
	public double	scale;
	/** True if the scaled image is being used. */
	public boolean	isScaled;
	/** The color type used by the buffered image. */
	private int		colorType;
	/** The interpolation used to render the object. */
	private Object	interpolation;
	/** The maximum size of the scaled image. */
	private double	maxScale;
	
	public boolean scaleInThread;
	
	private boolean threadFinished = true;
	
	public boolean finishedScaling = true;
	
	private boolean shouldUnload = false;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a basic scalable image.
	 * 
	 * @return	Returns the default scalable image.
	 */
	public ScalableImage() {
		super();
		this.scaledImage	= null;
		this.scale			= 1.0;
		this.isScaled		= false;
		this.colorType		= BufferedImage.TYPE_INT_ARGB;
		this.interpolation	= RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
		this.maxScale		= 1.0;
		this.scaleInThread	= false;
	}
	/**
	 * Constructs a scalable image using the given image.
	 * 
	 * @param	name - The name of the image to use.
	 * @return	Returns a scalable image.
	 */
	public ScalableImage(String name, String group) {
		super(name, group);
		this.scaledImage	= null;
		this.scale			= 1.0;
		this.isScaled		= false;
		this.colorType		= BufferedImage.TYPE_INT_ARGB;
		this.interpolation	= RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
		this.maxScale		= 0.0;
		this.scaleInThread	= false;
	}
	/**
	 * Constructs a scalable image with the given image and color type.
	 * 
	 * @param	name - The name of the image to use.
	 * @param	colorType - The buffered  image color type of the scaled image.
	 * @param	interpolation - The interpolation rendering hint.
	 * @return	Returns a scalable image with the given color type.
	 */
	public ScalableImage(String name, String group, int colorType, Object interpolation) {
		super(name, group);
		this.scaledImage	= null;
		this.scale			= 1.0;
		this.isScaled		= false;
		this.colorType		= colorType;
		this.interpolation	= interpolation;
		this.maxScale		= 0.0;
		this.scaleInThread	= false;
	}
	/**
	 * Constructs a scalable image with the given image and color type.
	 * 
	 * @param	name - The name of the image to use.
	 * @param	colorType - The buffered  image color type of the scaled image.
	 * @param	interpolation - The interpolation rendering hint.
	 * @param	maxScale - The max size of the image to continuously use.
	 * @return	Returns a scalable image with the given color type.
	 */
	public ScalableImage(String name, String group, int colorType, Object interpolation, double maxScale) {
		super(name, group);
		this.scaledImage	= null;
		this.scale			= 1.0;
		this.isScaled		= false;
		this.colorType		= colorType;
		this.interpolation	= interpolation;
		this.maxScale		= maxScale;
		this.scaleInThread	= false;
	}

	// ==================== Image Details =====================
	
	public void unloadScaledImage() {
		if (scaleInThread && !threadFinished) {
			shouldUnload = true;
		}
		else {
			if (scaledImage != null)
				scaledImage.flush();
			scaledImage = null;
			scale		= 1.0;
			isScaled	= false;
		}
	}
	/**
	 * Gets the scaled image.
	 * 
	 * @return	Returns the scaled image.
	 */
	public Image getScaledImage() {
		return (isScaled ? scaledImage : ImageLoader.getImage(resourceName, resourceGroup));
	}
	/**
	 * Tests whether the scaled image is being used.
	 * 
	 * @return	Returns true if the image is using the scaled version.
	 */
	public boolean isScaled() {
		return isScaled;
	}
	/**
	 * Returns the scale being used.
	 * 
	 * @return	Returns the current scale or 1.0 if there is no scale
	 */
	public double getScale() {
		return (isScaled ? scale : 1.0);
	}
	/**
	 * Scales the image to the requested size
	 * 
	 * @param	scale - The new scale of the image.
	 */
	public void setScale(final double scale) {
		if (!scaleInThread || threadFinished)
		{
		if (scale == 1.0) {
			isScaled = false;
		}
		else if (this.scale != scale) {
			if (scaleInThread) {
				finishedScaling = false;
				threadFinished = false;
				final ScalableImage scalableImage = this;
				new Thread() {
					public void run() {
						scalableImage.isScaled = true;
						scalableImage.scale = scale;
						Image image = getImage();
			
						// Get the new image size
						Vector newSize = new Vector(image.getWidth(null), image.getHeight(null));
						if (scalableImage.maxScale == 0.0)
							newSize.scale(scale).add(1, 1);
						else
							newSize.scale(scalableImage.maxScale).add(1, 1);
						
						
						// Create the scaled image if needed
						if (scalableImage.scaledImage == null || scalableImage.maxScale == 0.0) {
							if (scalableImage.scaledImage != null)
								scalableImage.scaledImage.flush();
							scalableImage.scaledImage = new BufferedImage((int)newSize.x, (int)newSize.y, scalableImage.colorType);
						}
						
						Graphics2D g = (Graphics2D)scalableImage.scaledImage.getGraphics();
						
						// Clear the scaled image
						if (scalableImage.colorType == BufferedImage.TYPE_INT_ARGB)
							g.setBackground(new Color(0, 0, 0, 0));
						else
							g.setBackground(new Color(0, 0, 0));
						
						Draw.clear(g, new Vector(), new Vector(image.getWidth(null), image.getHeight(null)));
						g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, scalableImage.interpolation);
						
						g.scale(scale, scale);
						g.drawImage(image, 0, 0, null);
						g.dispose();
						
						scalableImage.threadFinished = true;
						scalableImage.finishedScaling = true;
						if (scalableImage.shouldUnload) {
							if (scalableImage.scaledImage != null)
								scalableImage.scaledImage.flush();
							scalableImage.scaledImage = null;
							scalableImage.scale		= 1.0;
							scalableImage.isScaled	= false;
						}
					}
				}.start();
			}
			else {
				isScaled = true;
				this.scale = scale;
				Image image = getImage();
	
				// Get the new image size
				Vector newSize = new Vector(image.getWidth(null), image.getHeight(null));
				if (maxScale == 0.0)
					newSize.scale(scale).add(1, 1);
				else
					newSize.scale(maxScale).add(1, 1);
				
				
				// Create the scaled image if needed
				if (scaledImage == null || maxScale == 0.0) {
					if (scaledImage != null)
						scaledImage.flush();
					scaledImage = new BufferedImage((int)newSize.x, (int)newSize.y, colorType);
				}
				
				Graphics2D g = (Graphics2D)scaledImage.getGraphics();
				
				// Clear the scaled image
				if (colorType == BufferedImage.TYPE_INT_ARGB)
					g.setBackground(new Color(0, 0, 0, 0));
				else
					g.setBackground(new Color(0, 0, 0));
				
				Draw.clear(g, new Vector(), new Vector(image.getWidth(null), image.getHeight(null)));
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolation);
				
				g.scale(scale, scale);
				g.drawImage(image, 0, 0, null);
				g.dispose();
			}
		}
		else {
			isScaled = true;
		}
		}
	}
	
}

