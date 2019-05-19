package room;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

import nbt.NBTElement;
import nbt.NBTTagCompound;

import main.Main;
import main.ResourceLoader;
import entity.Surge;

import game.GameInstance;
import game.Profile;
import game.Trilogy;
import geometry.GMath;
import geometry.Vector;
import graphics.Button;
import graphics.Palette;
import graphics.ProfileList;
import graphics.TextBox;

/**
 * 
 * @author	Robert Jordan
 *
 */
public class Menu extends Room {

	// ======================= Members ========================
	
	/** The list of profiles to choose from. */
	public ArrayList<Profile> profiles;
	
	public int mode;
	
	public int selectedProfile;
	
	public ProfileList profileList;
	
	// ===================== Constructors =====================
	
	/**
	 * The default constructor, constructs a basic entity.
	 * 
	 * @return	Returns the default entity.
	 */
	public Menu() {
		profiles = new ArrayList<Profile>();
		mode = 0;
		selectedProfile = -1;
		profileList = null;
	}
	/**
	 * Initializes the room by setting the game instance it is linked to.
	 * 
	 * @param	instance - The game instance that contains the room.
	 */
	public void initialize(GameInstance instance, Trilogy trilogy) {
		super.initialize(instance, trilogy);
		
		File directory = new File("profiles");
		if (!directory.exists()) {
			directory.mkdir();
		}
		
		profileList = new ProfileList();
		ArrayList<String> profilePaths = new ArrayList<String>();
		profilePaths.addAll(ResourceLoader.getResources("profiles/", "", ".dat", false));
		for (int i = 0; i < profilePaths.size(); i++) {
			String path = profilePaths.get(i);
			int endIndex = path.lastIndexOf("profiles/") + ("profiles/").length();
			NBTTagCompound nbt = NBTElement.loadNBTCompound(path, false);
			Profile profile = new Profile(path.substring(endIndex, path.length() - 4), nbt);
			profiles.add(profile);
		}
		profileList.profiles = profiles;
		
		addGui(profileList);
		
		Vector buttonSize = new Vector(174, 56);
		//Font font = new Font("Crystal clear", Font.PLAIN, 22);
		//Font font = new Font("Eurostar", Font.PLAIN, 29);
		Font font = Palette.fontEurostar.deriveFont(Font.PLAIN, 29);
		
		Button button = new Button("new", "New Profile", new Vector(48, 16), buttonSize, new Vector(), font, null, false);
		addGui(button);
		button = new Button("delete", "Delete Profile", new Vector(48 + buttonSize.x + 16, 16), buttonSize, new Vector(), font, null, false);
		addGui(button);

		TextBox textBox = new TextBox("textBox", "Select a profile", new Vector(48 + buttonSize.x * 2 + 32, 16), buttonSize.plus(256, 0), font, false, 20);
		addGui(textBox);
		
		font = Palette.fontEurostar.deriveFont(Font.PLAIN, 22);
		button = new Button("quit", "Quit", new Vector(10, 10), new Vector(80, 40), new Vector(), font, null, false);
		addGui(button);
		
	}
	
	// ======================= Updating =======================
	
	/**
	 * Called every step for the map to perform actions and update.
	 */
	public void update() {

		getGui("new").position.y = Main.frame.getContentPane().getHeight() - 56 - 24;
		getGui("delete").position.y = Main.frame.getContentPane().getHeight() - 56 - 24;
		getGui("quit").position.x = Main.frame.getContentPane().getWidth() - 80 - 10;
		getGui("textBox").position.y = Main.frame.getContentPane().getHeight() - 56 - 24;
		((TextBox)getGui("textBox")).size.x = Main.frame.getContentPane().getWidth() - 48 - getGui("textBox").position.x;
		
		super.update();
		
		//if (GMath.random.nextInt(6) == 0) {
		if (GMath.random.nextDouble() * 6.0 * 900.0 / Main.frame.getContentPane().getWidth() <= 1.0) {
			double vspeed = 1.5 + GMath.random.nextDouble();
			if (GMath.random.nextBoolean())
				vspeed *= -1;
			double x = GMath.random.nextDouble() * Main.frame.getContentPane().getWidth();
			int length = 60 + GMath.random.nextInt(60);
			addEntity(new Surge(x, vspeed, length));
		}
		
		if (((Button)getGui("new")).pressed) {
			if (mode == 0) { // New Profile
				setMode(1);
			}
			else if (mode == 1) { // Create New Profile
				char[] invalidCharacters = {'<', '>', ':', '"', '/', '\\', '|', '?', '*'};
				String name = ((TextBox)getGui("textBox")).text;
				boolean validName = true;
				for (Profile p : profiles) {
					if (p.profileName.equals(name)) {
						validName = false;
						break;
					}
				}
				for (char c : invalidCharacters) {
					if (name.indexOf(c) != -1) {
						validName = false;
						break;
					}
				}
				if (validName && !name.isEmpty()) {
					Profile newProfile = new Profile(name);
					newProfile.saveProfile();
					profiles.add(newProfile);
					setMode(0);
				}
			}
			else if (mode == 2) { // New Profile
				setMode(1);
			}
			else if (mode == 3) { // Yes Delete Profile
				File file = new File("profiles/" + profiles.get(selectedProfile).profileName + ".dat");
				file.delete();
				profiles.remove(selectedProfile);
				setMode(0);
			}
		}
		else if (((Button)getGui("delete")).pressed) {
			if (mode == 0) { // Delete Profile
				setMode(2);
			}
			else if (mode == 1) { // Cancel New Profile
				setMode(0);
			}
			else if (mode == 2) { // Cancel Delete Profile
				setMode(0);
			}
			else if (mode == 3) { // No Delete Profile
				setMode(0);
			}
		}
		else if (((Button)getGui("quit")).pressed) {
			Main.stop();
		}
		
		if (profileList.selectedIndex != -1) {
			if (mode == 0) { // Load Profile
				instance.inMenu = false;
				instance.menu = null;
				instance.loadProfile(profiles.get(profileList.selectedIndex));
			}
			else if (mode == 2) { // Delete Profile
				selectedProfile = profileList.selectedIndex;
				setMode(3);
			}
		}
	}
	/**
	 * Called every step for the map to draw to the screen.
	 * @param	g - The graphics object to draw to.
	 */
	public void draw(Graphics2D g) {

		
		// Draw the room
		super.draw(g);
	}
	
	
	public void setMode(int mode) {
		this.mode = mode;
		if (mode == 0) {
			((Button)getGui("new")).label = "New Profile";
			((Button)getGui("delete")).label = "Delete Profile";
			((TextBox)getGui("textBox")).text = "Select a profile";
			((TextBox)getGui("textBox")).editable = false;
		}
		else if (mode == 1) {
			((Button)getGui("new")).label = "Create";
			((Button)getGui("delete")).label = "Cancel";
			((TextBox)getGui("textBox")).text = "Enter profile name";
			((TextBox)getGui("textBox")).editable = true;
			((TextBox)getGui("textBox")).typing = true;
			((TextBox)getGui("textBox")).justStartedTyping = true;
		}
		else if (mode == 2) {
			((Button)getGui("new")).label = "New Profile";
			((Button)getGui("delete")).label = "Cancel";
			((TextBox)getGui("textBox")).text = "Delete a profile";
			((TextBox)getGui("textBox")).editable = false;
		}
		else if (mode == 3) {
			((Button)getGui("new")).label = "Yes";
			((Button)getGui("delete")).label = "No";
			((TextBox)getGui("textBox")).text = "Delete this profile?";
			((TextBox)getGui("textBox")).editable = false;
		}
	}
}
