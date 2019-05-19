package room;

import entity.Marker;
import game.GameInstance;
import game.Profile;
import game.Trilogy;
import geometry.Vector;
import graphics.Button;
import graphics.Draw;
import graphics.MiniMap;
import graphics.PopupAbout;
import graphics.PopupControls;
import graphics.ScalableImage;
import graphics.StatusBar;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import main.ImageLoader;
import main.Mouse;
import nbt.*;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class Map extends Room {

	// ======================= Members ========================
	
	public boolean scaleInThread = true;
	
	/** The name of the map. */
	public String	name;
	/** The raw name of the map. */
	public String	rawName;
	
	/** The background image of the map. */
	public ScalableImage	image;
	
	public int game;
	
	public double zoomSpeed;
	
	public boolean zoomingIn;
	
	public double zoomScale;
	
	public Vector mouseFocus;
	
	public static final boolean SMOOTH_ZOOM = true;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a basic entity.
	 * 
	 * @return	Returns the default entity.
	 */
	public Map() {
		super();
	
		this.name		= "";
		this.rawName	= "";
		this.game		= 1;
		this.image		= null;
		this.zoomSpeed	= 0.0;
		this.zoomingIn	= false;
		this.zoomScale	= 0.0;
		this.mouseFocus = new Vector();
	}
	/**
	 * The default constructor, constructs a basic entity.
	 * 
	 * @return	Returns the default entity.
	 */
	public Map(String name, String rawName, int game) {
		super();

		this.name		= name;
		this.rawName	= rawName;
		this.game		= game;
		this.image		= new ScalableImage(Trilogy.getRawName(game) + "/maps/" + rawName, Trilogy.getRawName(game),
				BufferedImage.TYPE_INT_RGB, RenderingHints.VALUE_INTERPOLATION_BICUBIC, 1.0);
		this.image.scaleInThread = scaleInThread;
		this.zoomSpeed	= 0.0;
		this.zoomingIn	= false;
		this.zoomScale	= 0.0;
		this.mouseFocus = new Vector();
	}
	/**
	 * Constructs the map from an NBT compound of values. This is mainly used
	 * To load entire maps from file in NBT format.
	 * 
	 * @param	nbt - The compound containing the tags related to the map.
	 * @return	Returns the map constructed from tag data.
	 * 
	 * @see
	 * {@linkplain NBTElement},
	 * {@linkplain NBTTagCompound}
	 */
	public Map(String name, String rawName, int game, NBTTagCompound nbt) {
		super(nbt);
		
		this.name		= name;
		this.rawName	= rawName;
		this.game		= game;
		this.image		= new ScalableImage(Trilogy.getRawName(game) + "/maps/" + rawName, Trilogy.getRawName(game),
				BufferedImage.TYPE_INT_RGB, RenderingHints.VALUE_INTERPOLATION_BICUBIC, 1.0);
		this.image.scaleInThread = scaleInThread;
		this.zoomSpeed	= 0.0;
		this.zoomingIn	= false;
		this.zoomScale	= 0.0;
		this.mouseFocus = new Vector();
	}
	/**
	 * Constructs the map from an NBT compound of values. This is mainly used
	 * To load entire maps from file in NBT format.
	 * 
	 * @param	nbt - The compound containing the tags related to the map.
	 * @return	Returns the map constructed from tag data.
	 * 
	 * @see
	 * {@linkplain NBTElement},
	 * {@linkplain NBTTagCompound}
	 */
	public Map(NBTTagCompound nbt) {
		super(nbt);
	
		this.name		= nbt.getString("name", "");
		this.rawName	= nbt.getString("rawName", "");
		this.game		= nbt.getByte("game", (byte)1);
		this.image		= new ScalableImage(Trilogy.getRawName(game) + "/maps/" + rawName, Trilogy.getRawName(game),
				BufferedImage.TYPE_INT_RGB, RenderingHints.VALUE_INTERPOLATION_BICUBIC, 1.0);
		this.image.scaleInThread = scaleInThread;
		this.zoomSpeed	= 0.0;
		this.zoomingIn	= false;
		this.zoomScale	= 0.0;
		this.mouseFocus = new Vector();
	}
	/**
	 * Initializes the room by setting the game instance it is linked to.
	 * 
	 * @param	instance - The game instance that contains the room.
	 */
	public void initialize(GameInstance instance, Trilogy trilogy) {
		super.initialize(instance, trilogy);
		
		Vector buttonSize = new Vector(168, 56);
		double dropHeight = 28;
		Font font = new Font("Crystal clear", Font.PLAIN, 22);
		Font dropFont = new Font("Crystal clear", Font.PLAIN, 18);

		addGui(new MiniMap());
		addGui(new StatusBar());
		Button button = new Button("trilogy", "Trilogy", new Vector(32 + buttonSize.x * 0, 32), buttonSize, new Vector(370, dropHeight), font, dropFont, true);
		for (int i = 0; i < instance.trilogies.size(); i++) {
			button.listItems.add(instance.trilogies.get(i).name);
		}
		addGui(button);
		button = new Button("maps", "Map", new Vector(32 + buttonSize.x * 1, 32), buttonSize, new Vector(320, dropHeight), font, dropFont, true);
		for (int i = 0; i < trilogy.maps.size(); i++) {
			button.listItems.add(trilogy.maps.get(i).name);
		}
		addGui(button);
		button = new Button("reset", "Reset View", new Vector(32 + buttonSize.x * 2, 32), buttonSize, new Vector(), font, null, false);
		addGui(button);
		button = new Button("help", "Help", new Vector(32 + buttonSize.x * 3, 32), buttonSize, new Vector(220, dropHeight), font, dropFont, true);
		button.listItems.add("Controls");
		button.listItems.add("About");
		addGui(button);
		button = new Button("quit", "Main Menu", new Vector(32 + buttonSize.x * 4, 32), buttonSize, new Vector(), font, null, false);
		addGui(button);
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
		super.saveRoom(nbt);
		
		nbt.setString("name", name);
		nbt.setString("rawName", rawName);
		nbt.setByte("game", (byte)game);
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
		nbt.setString("class", "Map");
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
	public Map loadRoom(NBTTagCompound nbt) {
		return new Map(nbt);
	}

	// ======================= Updating =======================
	
	/**
	 * Called every step for the map to perform actions and update.
	 */
	public void update() {

		// Update the view
		updateMousePanControls();
		updateZoomControls();
		
		// Update the room
		super.update();
		
		if (Mouse.middle.pressed() && instance.editorMode) {
			Marker marker = new Marker(instance.currentPowerUp, 0);
			marker.placing = true;
			addEntity(marker);
		}

		if (((Button)getGui("trilogy")).selectedIndex != -1) {
			instance.changeTrilogy(((Button)getGui("trilogy")).selectedIndex + 1);
		}
		if (((Button)getGui("maps")).selectedIndex != -1) {
			trilogy.changeMap(((Button)getGui("maps")).selectedIndex);
		}
		if (((Button)getGui("reset")).pressed) {
			view.zoom = 1.0;
			view.pan.zero();
		}
		if (((Button)getGui("help")).selectedIndex != -1) {
			if (((Button)getGui("help")).selectedIndex == 0) {
				addGui(new PopupControls());
			}
			else if (((Button)getGui("help")).selectedIndex == 1) {
				addGui(new PopupAbout());
			}
		}
		if (((Button)getGui("help")).pressed) {
			
			/*long timer = System.currentTimeMillis();
			new Profile(instance).saveProfile();
			System.out.println("Save took " + (System.currentTimeMillis() - timer) + " milliseconds.");*/
		}
		else if (((Button)getGui("quit")).pressed) {
			new Profile(instance).saveProfile();
			instance.menu = new Menu();
			instance.menu.initialize(instance, trilogy);
			instance.inMenu = true;
			image.unloadScaledImage();
			ImageLoader.unloadImage(trilogy.rawName + "/maps/" + rawName, trilogy.rawName);
		}
	}
	/**
	 * Called every step for the map to draw to the screen.
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {

		//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		Marker.imageChecked.setScale(view.zoom);
		Marker.imageUnchecked.setScale(view.zoom);
		
		// Draw map image
		Vector point = view.getViewPoint(new Vector(0, 0));
		if (zoomSpeed != 0.0 || (view.zoom != 1.0 && instance.editorMode)) {
			Draw.drawImage(g, image.getImage(), point, image.getSize().scaledBy(view.zoom));
		}
		else if (view.zoom != 1.0 && !instance.editorMode) {
			image.setScale(view.zoom);
			if (image.finishedScaling) {
				Draw.drawImage(g, image.getScaledImage(), point);
			}
			else {
				Draw.drawImage(g, image.getImage(), point, image.getSize().scaledBy(view.zoom));
			}
		}
		else {
			Draw.drawImage(g, image.getImage(), point);
			//g.drawImage(image.getImage(), (int)point.x, (int)point.y, null);
		}
		
		// Draw the room
		super.draw(g);
	}

	// ======================= Panning ========================

	/**
	 * Pans the view when the right mouse button is down.
	 */
	public void updateMousePanControls() {
		// Pan the view:
		if (Mouse.right.down()) {
			view.pan.add(Mouse.getVectorPrevious().minus(Mouse.getVector()).scale(1.0 / view.zoom));
		}
	}
	/**
	 * Zooms the view with the mouse wheel.
	 */
	public void updateZoomControls() {
		Vector ms = Mouse.getVector();
		
		final double scaleMultiplier = 1.2;
		final double speedMultiplier = 0.32;
		
		// Zoom in and out:
		if (Mouse.wheelUp() && view.zoom < View.ZOOM_MAX) {
			if (SMOOTH_ZOOM) {
				mouseFocus = new Vector(ms);
				if (!zoomingIn || zoomSpeed == 0.0) {
					zoomScale = 1.0;
					zoomSpeed = speedMultiplier;
					zoomingIn = true;
				}
				else {
					zoomScale *= scaleMultiplier;
					zoomSpeed = speedMultiplier * zoomScale;
				}
			}
			else {
				double newZoom = view.zoom * View.ZOOM_AMOUNT;
				if (newZoom + 0.01 > View.ZOOM_MAX)
					newZoom = View.ZOOM_MAX;
				
				view.zoomFocus(ms, newZoom);
			}
		}
		if (Mouse.wheelDown() && view.zoom > View.ZOOM_MIN + 0.00001) {
			if (SMOOTH_ZOOM) {
				mouseFocus = new Vector(ms);
				if (zoomingIn || zoomSpeed == 0.0) {
					zoomScale = 1.0;
					zoomSpeed = speedMultiplier;
					zoomingIn = false;
				}
				else {
					zoomScale *= scaleMultiplier;
					zoomSpeed = speedMultiplier * zoomScale;
				}
			}
			else {
				double newZoom = view.zoom / View.ZOOM_AMOUNT;
				if (newZoom - 0.04 < View.ZOOM_MIN)
					newZoom = View.ZOOM_MIN;
				
				view.zoomFocus(ms, newZoom);
			}
		}
		if (SMOOTH_ZOOM) {
			if (zoomSpeed != 0.0) {
				zoomSpeed -= 0.02 * zoomScale;
				if (zoomSpeed <= 0.0) {
					zoomSpeed = 0.0;
				}
				else {
					double newZoom = 1.0;
					if (zoomingIn) {
						newZoom = view.zoom * (0.1 * zoomSpeed + 1.0);
						if (newZoom + 0.002 > View.ZOOM_MAX) {
							newZoom = View.ZOOM_MAX;
							zoomSpeed = 0.0;
						}
					}
					else {
						newZoom = view.zoom / (0.1 * zoomSpeed + 1.0);
						if (newZoom - 0.005 < View.ZOOM_MIN) {
							newZoom = View.ZOOM_MIN;
							zoomSpeed = 0.0;
						}
					}
					view.zoomFocus(mouseFocus, newZoom);
				}
			}
		}
	}
	
}

