package game;

import geometry.Vector;
import graphics.Draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entity.Marker;
import main.ImageLoader;
import main.Keyboard;
import main.Mouse;
import nbt.NBTElement;
import nbt.NBTTagCompound;
import nbt.NBTTagList;

import room.Map;
import room.Menu;
import room.Room;

/**
 * The instance that stores all game information.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain Profile},
 * {@linkplain Trilogy},
 * {@linkplain Map}
 */
public class GameInstance {

	// ======================= Members ========================
	
	public static boolean PROGRAMMER_MODE = false;
	
	/** The list of trilogies. */
	public ArrayList<Trilogy> trilogies;
	/** The index of the current trilogy. */
	public int currentTrilogy;
	/** The current trilogy. */
	public Trilogy trilogy;
	/** The name of the current profile to save to. */
	public String profileName;
	/** The current room that is running. */
	public Room room;
	public Menu menu;
	public boolean inMenu;
	
	/** Setting to show unchecked markers as red chozo symbols. */
	public boolean showUncheckedMarkers;

	// ===================== Debug Mode =======================
	
	/** True if editor mode is enabled to place power ups. */
	public boolean editorMode;
	/** The current power up to place in editor mode. */
	public int currentPowerUp;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs an entity at (0, 0).
	 * 
	 * @return	Returns the default entity.
	 */
	public GameInstance() {
		
		editorMode = false;
		currentPowerUp = PowerUp.MISSILE_EXPANSION;
		showUncheckedMarkers = false;
		profileName = "";
		trilogies = new ArrayList<Trilogy>();
		inMenu = true;
		menu = new Menu();
		
		NBTTagCompound nbt = NBTElement.loadNBTCompound("/resources/data/map.dat", true).getCompound("MetroidMapData");
		NBTTagList trilogyList = nbt.getList("trilogies");
		
		for (NBTElement tag : trilogyList.getTags()) {
			Trilogy t = new Trilogy((NBTTagCompound)tag);
			trilogies.add(t);
		}
		
		trilogy = trilogies.get(0);
	}
	/**
	 * Initializes game instance.
	 */
	public void initialize() {
		
		for (Trilogy t : trilogies) {
			t.initialize(this);
		}
		changeTrilogy(1);
		menu.initialize(this, null);
	}
	
	// ======================== Updating ========================
	
	/**
	 * Called every step for the game to perform actions and update.
	 */
	public void update() {
		
		if (Keyboard.getKey(KeyEvent.VK_S).pressed() && editorMode) {
			NBTTagCompound root = new NBTTagCompound("root");
			NBTTagCompound nbt = new NBTTagCompound("MetroidMapData");
			NBTTagList trilogyList = new NBTTagList("trilogies", NBTElement.TAG_COMPOUND);
			
			for (Trilogy t : trilogies) {
				NBTTagCompound trilogyTag = new NBTTagCompound();
				trilogyTag.setString("name", t.name);
				trilogyTag.setString("rawName", t.rawName);
				trilogyTag.setByte("game", (byte)t.game);
				NBTTagList mapList = new NBTTagList("maps", NBTElement.TAG_COMPOUND);
				
				for (Map m : t.maps) {
					NBTTagCompound mapTag = m.saveRoom();
					mapList.addCompound(mapTag);
				}
				
				trilogyTag.setList("maps", mapList);
				trilogyList.addCompound(trilogyTag);
			}
			nbt.setList("trilogies", trilogyList);
			root.setCompound("MetroidMapData", nbt);
			NBTElement.saveNBTCompound("map.dat", root);
			System.out.println("Levels Saved in New Format.");
		}
		else if (Keyboard.getKey(KeyEvent.VK_L).pressed() && editorMode) {
			NBTTagCompound nbt = NBTElement.loadNBTCompound("map.dat", false);
			nbt = nbt.getCompound("MetroidMaps", null);
			for (Trilogy t : trilogies) {
				NBTTagCompound game = nbt.getCompound(t.rawName, null);
				for (int i = 0; i < t.maps.size(); i++) {
					NBTTagCompound map = game.getCompound(t.maps.get(i).rawName, null);
					t.maps.set(i, t.maps.get(i).loadRoom(map));
					t.maps.get(i).initialize(this, t);
				}
				t.map = t.maps.get(t.currentMap);
			}
			System.out.println("Levels Loaded.");
		}
		
		if (Keyboard.getKey(KeyEvent.VK_F4).pressed() && PROGRAMMER_MODE)
			editorMode = !editorMode;
		
		
		if (Keyboard.getKey(KeyEvent.VK_1).pressed())
			currentPowerUp = 0;
		if (Keyboard.getKey(KeyEvent.VK_2).pressed())
			currentPowerUp = 1;
		if (Keyboard.getKey(KeyEvent.VK_3).pressed())
			currentPowerUp = 2;
		if (Keyboard.getKey(KeyEvent.VK_4).pressed())
			currentPowerUp = 3;
		if (Keyboard.getKey(KeyEvent.VK_5).pressed())
			currentPowerUp = 4;
		if (Keyboard.getKey(KeyEvent.VK_6).pressed())
			currentPowerUp = 5;
		if (Keyboard.getKey(KeyEvent.VK_7).pressed())
			currentPowerUp = 6;
		if (Keyboard.getKey(KeyEvent.VK_8).pressed())
			currentPowerUp = 7;
		if (Keyboard.getKey(KeyEvent.VK_9).pressed())
			currentPowerUp = 8;
		if (Keyboard.getKey(KeyEvent.VK_0).pressed())
			currentPowerUp = 9;
		if (Keyboard.getKey(KeyEvent.VK_MINUS).pressed())
			currentPowerUp = 10;
		
		if (Keyboard.insert.pressed()) {
			showUncheckedMarkers = !showUncheckedMarkers;
		}
		if (Keyboard.enter.pressed() && editorMode) {
			ArrayList<ArrayList<Integer>> collection = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < trilogy.powerUps.size(); i++) {
				collection.add(new ArrayList<Integer>());
				for (int j = 0; j < trilogy.powerUps.get(i).size(); j++) {
					collection.get(i).add(0);
				}
			}
			for (int i = 0; i < trilogy.maps.size(); i++) {
				for (int j = 0; j < trilogy.maps.get(i).entities.size(); j++) {
					if (trilogy.maps.get(i).entities.get(j) instanceof Marker) {
						Marker marker = (Marker)trilogy.maps.get(i).entities.get(j);
						collection.get(marker.type).set(marker.index, collection.get(marker.type).get(marker.index) + 1);
						if (marker.hasLinkedPowerUp()) {
							collection.get(marker.linkedType).set(marker.linkedIndex, collection.get(marker.linkedType).get(marker.linkedIndex) + 1);
						}
					}
				}
			}
			int type = -1;
			int index = 0;
			for(int i = 0; i < collection.size(); i++) {
				for (int j = 0; j < collection.get(i).size(); j++) {
					if (collection.get(i).get(j) != 1) {
						type = i;
						index = j;
					}
				}
			}
			if (type != -1) {
				boolean zero = (collection.get(type).get(index) == 0);
				JOptionPane.showMessageDialog(null, "Number of power-ups for " + trilogy.getPowerUp(type, index).getTypeName() +
				": " + trilogy.getPowerUp(type, index).name + " is " +
				(zero ? "less than" : "greater than") + " one.", "Power Up Collection Check", JOptionPane.ERROR_MESSAGE);
				Keyboard.reset();
				Mouse.reset();
				
			}
			else {
				JOptionPane.showMessageDialog(null, "All collectables are correct", "Power Up Collection Check", JOptionPane.PLAIN_MESSAGE);
				Keyboard.reset();
				Mouse.reset();
						
			}
		}
		
		// Update the map
		if (inMenu)
			menu.update();
		else
			trilogy.map.update();
	}
	/**
	 * Called every step for the game to draw to the screen.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		// Draw the map
		if (inMenu)
			menu.draw(g);
		else
			trilogy.map.draw(g);
		
		if (editorMode) {
			g.setFont(new Font("Eurostar Black", Font.PLAIN, 16));
			Stroke oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			//g.setColor(Color.WHITE);
			g.setColor(Color.BLACK);
			Draw.drawStringOutline(g, PowerUp.getPowerUpName(currentPowerUp), 24, 160, 0);//Draw.ALIGN_TOP);
			g.setStroke(oldStroke);
			
			//g.setColor(new Color(33, 189, 222));
			g.setColor(Color.WHITE);
			Draw.drawString(g, PowerUp.getPowerUpName(currentPowerUp), 24, 160, 0);//Draw.ALIGN_TOP);
			oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(0.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			//g.setColor(Color.WHITE);
			g.setColor(Color.BLACK);
			//Draw.drawStringOutline(g, PowerUp.getPowerUpName(currentPowerUp), 24, 160, 0);//Draw.ALIGN_TOP);
			g.setStroke(oldStroke);
		}
	}
	
	public void changeTrilogy(int game) {
		trilogy.map.image.unloadScaledImage();
		ImageLoader.unloadImage(trilogy.rawName + "/maps/" + trilogy.map.rawName, trilogy.rawName);
		currentTrilogy = game - 1;
		trilogy = trilogies.get(game - 1);
		ImageLoader.loadImage(trilogy.rawName + "/maps/" + trilogy.map.rawName, trilogy.rawName);
	}
	
	public void loadProfile(Profile profile) {
		
		profileName = profile.profileName;
		currentTrilogy = profile.currentTrilogy;
		trilogy = trilogies.get(currentTrilogy);
		
		for (int i = 0; i < trilogies.size(); i++) {
			TrilogyInfo t = profile.trilogies.get(i);
			trilogies.get(i).currentMap = t.currentMap;
			trilogies.get(i).map = trilogies.get(i).maps.get(t.currentMap);
			for (int j = 0; j < t.powerUpsCollected.size(); j++) {
				for (int k = 0; k < t.powerUpsCollected.get(j).size(); k++) {
					trilogies.get(i).powerUps.get(j).get(k).collected = t.powerUpsCollected.get(j).get(k);
				}
			}
			for (int j = 0; j < t.mapPans.size(); j++) {
				trilogies.get(i).maps.get(j).view.pan = new Vector(t.mapPans.get(j));
				trilogies.get(i).maps.get(j).view.zoom = t.mapZooms.get(j);
			}
		}
		trilogy.changeMap(trilogy.currentMap);
	}
}
