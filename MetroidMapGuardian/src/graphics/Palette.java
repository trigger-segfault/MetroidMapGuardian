package graphics;

import geometry.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.io.IOException;
import java.io.InputStream;

/**
 * A static class to store variables for all the different types of
 * colors used by the program. This stores: colors, fonts, gradients,
 * textures, etc.
 * @author	Robert Jordan
 * @see
 * {@linkplain Draw}
 */
public class Palette {

	// ======================== Colors ========================
	
	public static final Color colorFont1			= new Color(255, 255, 255);
	
	public static final Color colorPanelSolid		= new Color(255, 255, 255);
	public static final Color colorPanelOutline		= new Color(255, 255, 255);
	
	public static final Color colorPanelGradient1	= new Color(255, 255, 255);
	public static final Color colorPanelGradient2	= new Color(255, 255, 255);

	// ====================== Gradients =======================
	
	
	// ======================== Fonts =========================
	
	public static Font fontEurostar = loadFont("resources/fonts/eurostar.ttf");
	
	private static Font loadFont(String path) {
		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream(path);
			return Font.createFont(Font.TRUETYPE_FONT, stream);
		}
		catch (FontFormatException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static GradientPaint getGradient(GradientPaint gradient, Vector point1, Vector point2) {
		return null;
	}
	
}
