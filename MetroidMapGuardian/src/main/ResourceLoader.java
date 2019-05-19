package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * A static class to simplify loading resources and make the call to load
 * all program resources at once.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain ImageLoader},
 * {@linkplain Resource},
 * {@linkplain ImageResource}
 */
public class ResourceLoader {

	// =================== Program Loading ====================
	
	/**
	 * Preload any resources that need to be stored before the program is initialized.
	 */
	static void preloadResources() {
		System.out.println("");
		System.out.println("============= Preloading Resources =============");
		
		preloadFonts();
		
		ImageLoader.preloadImages();
	}
	/**
	 * Load the rest of the resources for the program.
	 */
	static void loadResources() {
		System.out.println("");
		System.out.println("============== Loading Resources ===============");
		
		
		ImageLoader.loadImages();
		
		//SoundLoader.loadSounds();
		//RoomLoader.loadRooms();
	}
	/**
	 * Preload all fonts in the fonts folder.
	 */
	static void preloadFonts() {
		
		System.out.println("Loading Fonts:");
		
		// Get all font paths
		ArrayList<String> paths = new ArrayList<String>();
		paths.addAll(getResources("resources/fonts/", "", ".ttf", true));
		
		// Register all loaded fonts
		for( int i = 0; i < paths.size(); i++ ) {
			try {
				InputStream stream = ClassLoader.getSystemResourceAsStream(paths.get(i));
				Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
				GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
				System.out.println("- " + font.getFontName());
			}
			catch (FontFormatException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("");
		System.out.println("Loaded " + String.valueOf(paths.size()) + " fonts");
		System.out.println("--------------------------------");
	}

	// =================== Loading Methods ====================
	
	/**
	 * Find the paths to all resources with a given path.
	 * 
	 * @param	path - The base path of the resource.
	 * @param	excludePath - The continued path to exclude from the list.
	 * @param	extension - The extension of the resource, this can be empty.
	 * @param	isResource - True if the file is a resource.
	 * @return	Returns a collection of strings with the paths of the matching
	 * resources.
	 */
	public static Collection<String> getResources(String path, String excludePath, String extension, boolean isResource) {
		
		ArrayList<String> paths = new ArrayList<String>();
		
		// Get the path of the jar file
		String classPath = System.getProperty((isResource ? "java.class.path" : "user.dir"), ".");

		// Set directory separator due to changes in OS:
		// Replace '/' with '\\' for Windows OS
		paths.addAll(getResources(classPath.replace('/', '\\'), path.replace('/', '\\'), excludePath.replace('/', '\\'), extension));
		// Replace '\\' with '/' for Mac OS
		paths.addAll(getResources(classPath.replace('\\', '/'), path.replace('\\', '/'), excludePath.replace('\\', '/'), extension));
		
		// Replace all '\\'s with '/' in the paths for consistency
		for (int i = 0; i < paths.size(); i++) {
			paths.set(i, paths.get(i).replace('\\', '/'));
		}
		
		return paths;
    }

	// =================== Private Methods ====================
	
	/**
	 * Finds the paths of all the resources with the class path and extension.
	 * 
	 * @param	classPath - The path of the jar file.
	 * @param	path - The main path of the resource.
	 * @param	excludePath - The continued path to exclude from the list.
	 * @param	extension - The extension of the resource, this can be empty.
	 * @return	Returns a collection of strings with the paths of the matching
	 * resources.
	 */
	private static Collection<String> getResources(String classPath, String path, String excludePath, String extension) {
		
		ArrayList<String> paths = new ArrayList<String>();
		File file = new File(classPath);
		
		if (file.isDirectory()) {
			// Add files from directory if the class path is not a jar file
			paths.addAll(getResourcesFromDirectory(file, classPath, path, excludePath, extension));
		}
		else {
			// Add files from a jar if the class path is not a directory
			paths.addAll(getResourcesFromJarFile(file, classPath, path, excludePath, extension));
		}
		return paths;
	}
	/**
	 * Finds the paths of all the resources within a jar file.
	 * 
	 * @param	file - The jar file to look through.
	 * @param	classPath - The path of the jar file.
	 * @param	path - The main path of the resource.
	 * @param	excludePath - The continued path to exclude from the list.
	 * @param	extension - The extension of the resource, this can be empty.
	 * @return	Returns a collection of strings with the paths of the matching
	 * resources.
	 */
	private static Collection<String> getResourcesFromJarFile(File file, String classPath, String path, String excludePath, String extension) {
		
		ArrayList<String> paths = new ArrayList<String>();
		
		try {
			// Open the jar file
			ZipFile  zipFile = new ZipFile(file);
			
			// Get the contents of the jar file
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
			
			// For each entry in the jar file, check for a matching path:
			while (zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = zipEntries.nextElement();
				String fileName = zipEntry.getName();
				// Make sure the path ends with the appropriate extension and is not a directory
				if (!fileName.endsWith("\\") && !fileName.endsWith("/") && fileName.endsWith(extension)) {
					
					// Compare paths:
					if (fileName.startsWith(path) &&
						(!fileName.startsWith(path + excludePath) ||
						excludePath.isEmpty())) {
						// Try loading without the class path
						paths.add(fileName);
					}
					else if (fileName.startsWith(classPath + "\\" + path) &&
							(!fileName.startsWith(classPath + "\\" + path + excludePath) ||
							excludePath.isEmpty())) {
						// Try loading with the class path with Windows OS separators
						fileName = fileName.substring((classPath + "\\").length());
						paths.add(fileName);
					}
					else if (fileName.startsWith(classPath + "/" + path) &&
							(!fileName.startsWith(classPath + "/" + path + excludePath) ||
							excludePath.isEmpty())) {
						// Try loading with the class path with Mac OS separators
						fileName = fileName.substring((classPath + "/").length());
						paths.add(fileName);
					}
				}
			}

			// Close the jar file
			zipFile.close();
		}
		catch (ZipException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return paths;
	}
	/**
	 * Finds the paths of all the resources within a jar file.
	 * 
	 * @param	file - The directory file to look through.
	 * @param	classPath - The path of the jar file.
	 * @param	path - The main path of the resource.
	 * @param	excludePath - The continued path to exclude from the list.
	 * @param	extension - The extension of the resource, this can be empty.
	 * @return	Returns a collection of strings with the paths of the matching
	 * resources.
	 */
	private static Collection<String> getResourcesFromDirectory(File directory, String classPath, String path, String excludePath, String extension) {
		
		ArrayList<String> paths = new ArrayList<String>();
		
		// Get the contents of the directory
		File[] fileList = directory.listFiles();
		
		// Get for each file in the directory, check for a matching path:
		for (File file : fileList) {
			if (file.isDirectory()) {
				// The file is a directory
				// Load contents from the child directory
				paths.addAll(getResourcesFromDirectory(file, classPath, path, excludePath, extension));
			}
			else {
				// The file is not a directory
				try {
					String fileName = file.getCanonicalPath();
					// Make sure the path ends with the appropriate extension and is not a directory
					if (!fileName.endsWith("\\") && !fileName.endsWith("/") && fileName.endsWith(extension)) {
						
						// Compare paths:
						if (fileName.startsWith(path) &&
						   (!fileName.startsWith(path + excludePath) ||
							excludePath.isEmpty())) {
							// Try loading without the class path
							paths.add(fileName);
						}
						else if (fileName.startsWith(classPath + "\\" + path) &&
								(!fileName.startsWith(classPath + "\\" + path + excludePath) ||
								excludePath.isEmpty())) {
							// Try loading with the class path with Windows OS separators
							fileName = fileName.substring((classPath + "\\").length());
							paths.add(fileName);
						}
						else if (fileName.startsWith(classPath + "/" + path) &&
								(!fileName.startsWith(classPath + "/" + path + excludePath) ||
								excludePath.isEmpty())) {
							// Try loading with the class path with Mac OS separators
							fileName = fileName.substring((classPath + "/").length());
							paths.add(fileName);
						}
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return paths;
	}
}
