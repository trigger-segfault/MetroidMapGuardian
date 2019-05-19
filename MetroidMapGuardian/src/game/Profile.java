package game;

import geometry.Vector;

import java.util.ArrayList;

import nbt.NBTElement;
import nbt.NBTTagCompound;
import nbt.NBTTagList;

import main.Game;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class Profile {
	
	// ======================= Members ========================

	public String profileName;
	
	public int currentTrilogy;
	
	public ArrayList<TrilogyInfo> trilogies;
	

	// ===================== Constructors =====================
	
	/**
	 * 
	 */
	public Profile() {
		this.profileName	= "";
		this.currentTrilogy	= 0;
		this.trilogies = new ArrayList<TrilogyInfo>();
		this.trilogies.add(new TrilogyInfo(1));
		this.trilogies.add(new TrilogyInfo(2));
		this.trilogies.add(new TrilogyInfo(3));
	}
	
	public Profile(String name) {
		this.profileName	= name;
		this.currentTrilogy	= 0;
		this.trilogies = new ArrayList<TrilogyInfo>();
		this.trilogies.add(new TrilogyInfo(1));
		this.trilogies.add(new TrilogyInfo(2));
		this.trilogies.add(new TrilogyInfo(3));
	}
	public Profile(String name, NBTTagCompound nbt) {
		nbt = nbt.getCompound("MetroidProfileData");
		
		this.profileName	= name;
		this.currentTrilogy	= nbt.getByte("currentTrilogy");
		this.trilogies = new ArrayList<TrilogyInfo>();
		for (NBTElement tag : nbt.getList("trilogies").getTags()) {
			TrilogyInfo t = new TrilogyInfo((NBTTagCompound)tag);
			this.trilogies.add(t);
		}
	}
	public Profile(GameInstance instance) {
		this.profileName	= instance.profileName;
		this.currentTrilogy	= instance.currentTrilogy;
		this.trilogies = new ArrayList<TrilogyInfo>();
		for (Trilogy t : instance.trilogies) {
			this.trilogies.add(new TrilogyInfo(t));
		}
	}
	
	// ======================= Methods ========================
	
	public int getCompletionPercentage(int game) {
		TrilogyInfo t = trilogies.get(game - 1);
		
		int total = 0;
		int collected = 0;
		
		for (int i = 0; i < t.powerUpsCollected.size(); i++) {
			for (int j = 0; j < t.powerUpsCollected.get(i).size(); j++) {
				total++;
				if (t.powerUpsCollected.get(i).get(j))
					collected ++;
			}
		}
		return (int)((double)collected / total * 100);
	}
	
	public void saveProfile() {
		NBTTagCompound root = new NBTTagCompound("root");
		NBTTagCompound nbt = new NBTTagCompound("MetroidProfileData");
		
		nbt.setByte("currentTrilogy", (byte)currentTrilogy);
		NBTTagList list = new NBTTagList("trilogies");
		for (TrilogyInfo t : trilogies) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setByte("game", (byte)t.game);
			tag.setInteger("currentMap", t.currentMap);
			
			NBTTagList powerUps = new NBTTagList("powerUpsCollected");
			for (int i = 0; i < t.powerUpsCollected.size(); i++) {
				NBTTagList powerUpTypes = new NBTTagList();
				for (int j = 0; j < t.powerUpsCollected.get(i).size(); j++) {
					powerUpTypes.addBoolean(t.powerUpsCollected.get(i).get(j));
				}
				powerUps.addList(powerUpTypes);
			}
			tag.setList("powerUpsCollected", powerUps);
			
			NBTTagList maps = new NBTTagList("maps");
			for (int i = 0; i < t.mapPans.size(); i++) {
				NBTTagCompound map = new NBTTagCompound();
				map.setDouble("x", t.mapPans.get(i).x);
				map.setDouble("y", t.mapPans.get(i).y);
				map.setDouble("zoom", t.mapZooms.get(i));
				maps.addCompound(map);
			}
			tag.setList("maps", maps);
			list.addCompound(tag);
		}
		nbt.setList("trilogies", list);
		root.setCompound("MetroidProfileData", nbt);
		
		NBTElement.saveNBTCompound("profiles/" + profileName + ".dat", root);
	}
}

/**
 * 
 * @author	Robert Jordan
 *
 */
class TrilogyInfo {
	
	// ======================= Members ========================

	/** The collection of power up collected states. */
	public ArrayList<ArrayList<Boolean>> powerUpsCollected;
	
	public ArrayList<Vector> mapPans;
	
	public ArrayList<Double> mapZooms;
	
	public int currentMap;
	
	public int game;
	

	// ===================== Constructors =====================
	
	/**
	 * 
	 */
	public TrilogyInfo(int game) {
		this.game		= game;
		this.currentMap	= 0;
		this.mapPans	= new ArrayList<Vector>();
		this.mapZooms	= new ArrayList<Double>();
		this.powerUpsCollected = new ArrayList<ArrayList<Boolean>>();
		
		Trilogy t = Game.instance.trilogies.get(game - 1);
		for (int i = 0; i < t.powerUps.size(); i++) {
			this.powerUpsCollected.add(new ArrayList<Boolean>());
			for (int j = 0; j < t.powerUps.get(i).size(); j++) {
				this.powerUpsCollected.get(i).add(false);
			}
		}
		for (int i = 0; i < t.maps.size(); i++) {
			this.mapPans.add(new Vector());
			this.mapZooms.add(1.0);
		}
	}
	
	public TrilogyInfo(NBTTagCompound nbt) {
		this.game		= nbt.getByte("game");
		this.currentMap	= nbt.getInteger("currentMap");
		this.mapPans	= new ArrayList<Vector>();
		this.mapZooms	= new ArrayList<Double>();
		this.powerUpsCollected = new ArrayList<ArrayList<Boolean>>();
		
		Trilogy t = Game.instance.trilogies.get(game - 1);
		for (int i = 0; i < t.powerUps.size(); i++) {
			NBTTagList list = nbt.getList("powerUpsCollected").getList(i, new NBTTagList());
			this.powerUpsCollected.add(new ArrayList<Boolean>());
			
			for (int j = 0; j < t.powerUps.get(i).size(); j++) {
				this.powerUpsCollected.get(i).add(list.getBoolean(j));
			}
		}
		for (int i = 0; i < t.maps.size(); i++) {
			NBTTagCompound map = nbt.getList("maps").getCompound(i, new NBTTagCompound());
			this.mapPans.add(new Vector(map.getDouble("x"), map.getDouble("y")));
			this.mapZooms.add(map.getDouble("zoom", 1.0));
		}
	}
	
	public TrilogyInfo(Trilogy t) {
		this.game		= t.game;
		this.currentMap	= t.currentMap;
		this.mapPans	= new ArrayList<Vector>();
		this.mapZooms	= new ArrayList<Double>();
		this.powerUpsCollected = new ArrayList<ArrayList<Boolean>>();
		
		for (int i = 0; i < t.powerUps.size(); i++) {
			this.powerUpsCollected.add(new ArrayList<Boolean>());
			
			for (int j = 0; j < t.powerUps.get(i).size(); j++) {
				this.powerUpsCollected.get(i).add(t.powerUps.get(i).get(j).collected);
			}
		}
		for (int i = 0; i < t.maps.size(); i++) {
			this.mapPans.add(new Vector(t.maps.get(i).view.pan));
			this.mapZooms.add(t.maps.get(i).view.zoom);
		}
	}
}
