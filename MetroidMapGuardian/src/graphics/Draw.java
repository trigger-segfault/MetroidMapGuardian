package graphics;

import geometry.*;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

/**
 * A static class used to draw images, strings, and shapes. It simplifies the
 * process by allowing the use of double precision coordinates.
 * 
 * @author	Robert Jordan
 * @see
 * {@linkplain Vector}
 */
public class Draw {

	// ================== Private Variables ===================
	
	/** The image kept for accessing the graphics object and using font metrics.*/
	private static Image graphicsSlave = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	
	// =================== Public Variables ===================
	
	/** The default font that comes with a graphics object. */
	public static Font		DEFAULT_FONT	= graphicsSlave.getGraphics().getFont();

	/** The font will draw left aligned. */
	public static final int	ALIGN_LEFT		= 0x00;
	/** The font will draw centered aligned. */
	public static final int	ALIGN_CENTER	= 0x01;
	/** The font will draw right aligned. */
	public static final int	ALIGN_RIGHT		= 0x02;
	
	/** The font will draw at the base of its height. */
	public static final int	ALIGN_BASE		= 0x00;
	/** The font will draw at the top of its height. */
	public static final int ALIGN_TOP		= 0x10;
	/** The font will draw at the middle of its height. */
	public static final int	ALIGN_MIDDLE	= 0x20;
	/** The font will draw at the bottom of its height. */
	public static final int	ALIGN_BOTTOM	= 0x30;

	// ======================= Details ========================

	/**
	 * Gets the font metrics for the given font.
	 * 
	 * @param	font - The font to get the metrics of.
	 * @return	Returns the font metrics for the specified font.
	 */
	public static FontMetrics getFontMetrics(Font font) {
		Graphics g = graphicsSlave.getGraphics();
		return g.getFontMetrics(font);
	}
	/**
	 * Gets the glyph vector for the specified font and text.
	 * 
	 * @param	text - the text to use.
	 * @param	font - The font to use.
	 * @return	Returns the glyph vector of the text with the specified font.
	 */
	public static GlyphVector getGlyphVector(String text, Font font) {
		Graphics g = graphicsSlave.getGraphics();
		return font.createGlyphVector(g.getFontMetrics(font).getFontRenderContext(), text);
	}
	/**
	 * Calculates the size of the text with the specified font.
	 * 
	 * @param	text - The text to get the size of.
	 * @param	font - The font to use for the text.
	 * @return	Returns a vector of the size of the text.
	 */
	public static Vector getStringSize(String text, Font font) {
		Graphics g = graphicsSlave.getGraphics();
		return new Vector(g.getFontMetrics(font).stringWidth(text),
				g.getFontMetrics(font).getHeight());
	}
	/**
	 * Calculates the size of the wrapped text with the specified font.
	 * 
	 * @param	text - The text to get the size of.
	 * @param	font - The font to use for the text.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 * @return	Returns a vector of the size of the text.
	 */
	public static Vector getStringWrapSize(String text, Font font, double width, int maxLines, double separation) {
		Graphics g = graphicsSlave.getGraphics();
		String[] lines = getWrappedString(text, font, width, maxLines);
		
		return new Vector(width, ((lines.length - 1) * separation) +
				(lines.length * g.getFontMetrics(font).getHeight()));
	}
	/**
	 * Calculates the wrapped text with the specified font.
	 * 
	 * @param	text - The text to return the wrapped form of.
	 * @param	font - The font to use for the text.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @return	Returns an array of lines of the text or null if there were no lines.
	 */
	public static String[] getWrappedString(String text, Font font, double width, int maxLines) {
		String[] lines = new String[0];
		
		// The remaining text
		String string = text;
		
		// Add lines until the string has been emptied
		for (int i = 0; !string.isEmpty() && (i < maxLines || maxLines == -1); i++) {
			// Extend the array
			String[] newLines = new String[i + 1];
			for (int j = 0; j < i; j++) {
				newLines[j] = lines[j];
			}
			newLines[i] = "";
			lines = newLines;
			
			// The last space/dash where the line will break
			int lastSpace = -1;
			// The last space was a dash
			boolean isDash = false;
			
			if ((getStringSize(string, font).x <= width) && (string.indexOf('\n') == -1)) {
				lines[i] = string;
				string = "";
			}
			for (int j = 0; j < string.length(); j++) {
				// Check for a space or dash
				if (string.charAt(j) == ' ' || string.charAt(j) == '-') {
					lastSpace = j;
					isDash = (string.charAt(j) == '-');
				}
				// If there is a line limit, try to fit "..." in if it is the last line:
				String dotdot = ((i + 1 == maxLines) ? "..." : "");
				
				if (string.charAt(j) == '\n') {
					lines[i] = string.substring(0, j) + dotdot;
					string = string.substring(j + 1, string.length());
					break;
				}
				else if (getStringSize(string.substring(0, j + 1) + dotdot, font).x > width) {
					// If the string passes the line width
					if (lastSpace == -1) {
						// If there was no line break character...
						lines[i] = string.substring(0, j) + dotdot;
						string = string.substring(j, string.length());
					}
					else {
						// Include the line breaker if it was a dash
						lines[i] = string.substring(0, lastSpace + (isDash ? 1 : 0)) + dotdot;
						string = string.substring(lastSpace + 1, string.length());
					}
					break;
				}
				else if (j + 1 == string.length()) {
					// The last line still fits
					lines[i] = string;
					string = "";
					break;
				}
			}
		}
		return lines;
	}
	
	// ====================== Clear Image ======================
	
	/**
	 * Clears the entire image by filling it with the background color.
	 * 
	 * @param	g - The graphics object to draw to.
	 */
	public static void clear(Graphics2D g) {
		java.awt.Rectangle rect = g.getDeviceConfiguration().getBounds();
		g.clearRect(rect.x, rect.y, rect.width, rect.height);
	}
	/**
	 * Clears a rectangular portion of the image by filling it with the background color.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	point - The point of the rectangle.
	 * @param	size - The size of the rectangle.
	 */
	public static void clear(Graphics2D g, Vector point, Vector size) {
		g.clearRect((int)point.x, (int)point.y, (int)size.x, (int)size.y);
	}

	// ====================== Draw String ======================

	/**
	 * Draws the specified text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 */
	public static void drawString(Graphics2D g, String text, double x, double y) {
		drawString(g, text, new Vector(x, y), 0);
	}
	/**
	 * Draws the specified text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 */
	public static void drawString(Graphics2D g, String text, double x, double y, int alignment) {
		drawString(g, text, new Vector(x, y), alignment);
	}
	/**
	 * Draws the specified text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 */
	public static void drawString(Graphics2D g, String text, Vector point) {
		drawString(g, text, point, 0);
	}
	/**
	 * Draws the specified text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 */
	public static void drawString(Graphics2D g, String text, Vector point, int alignment) {

		point = new Vector(point);
		
		// Font width settings
		if ((alignment & 0x03) == ALIGN_CENTER) {
			point.x -= getStringSize(text, g.getFont()).x / 2;
		}
		else if ((alignment & 0x03) == ALIGN_RIGHT) {
			point.x -= getStringSize(text, g.getFont()).x;
		}
		
		// Font height settings
		if ((alignment & 0x30) == ALIGN_TOP) {
			point.y += getFontMetrics(g.getFont()).getAscent();
		}
		else if ((alignment & 0x30) == ALIGN_MIDDLE) {
			point.y += getFontMetrics(g.getFont()).getAscent();
			point.y -= getFontMetrics(g.getFont()).getHeight() / 2;
		}
		else if ((alignment & 0x30) == ALIGN_BOTTOM) {
			point.y -= getFontMetrics(g.getFont()).getDescent();
		}
		
		g.drawString(text, (float)point.x, (float)point.y);
	}
	/**
	 * Draws the specified wrapped text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringWrap(Graphics2D g, String text, double x, double y, double width,
			int maxLines, double separation) {
		drawStringWrap(g, text, new Vector(x, y), 0, width, maxLines, separation);
	}
	/**
	 * Draws the specified wrapped text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringWrap(Graphics2D g, String text, double x, double y, int alignment,
			double width, int maxLines, double separation) {
		drawStringWrap(g, text, new Vector(x, y), alignment, width, maxLines, separation);
	}
	/**
	 * Draws the specified wrapped text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringWrap(Graphics2D g, String text, Vector point,
			double width, int maxLines, double separation) {
		drawStringWrap(g, text, point, 0, width, maxLines, separation);
	}
	/**
	 * Draws the specified wrapped text to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringWrap(Graphics2D g, String text, Vector point, int alignment,
			double width, int maxLines, double separation) {
		
		String[] lines = getWrappedString(text, g.getFont(), width, maxLines);
		
		for (int i = 0; i < lines.length; i++) {
			drawString(g, lines[i], (float)point.x, (float)(point.y + i * (getStringSize("", g.getFont()).y + separation)), alignment);
		}
	}

	// ================== Draw String Outline ==================

	/**
	 * Draws the specified text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 */
	public static void drawStringOutline(Graphics2D g, String text, double x, double y) {
		drawStringOutline(g, text, new Vector(x, y), 0);
	}
	/**
	 * Draws the specified text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 */
	public static void drawStringOutline(Graphics2D g, String text, double x, double y, int alignment) {
		drawStringOutline(g, text, new Vector(x, y), alignment);
	}
	/**
	 * Draws the specified text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 */
	public static void drawStringOutline(Graphics2D g, String text, Vector point) {
		drawStringOutline(g, text, point, 0);
	}
	/**
	 * Draws the specified text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 */
	public static void drawStringOutline(Graphics2D g, String text, Vector point, int alignment) {

		point = new Vector(point);
		
		// Font width settings
		if ((alignment & 0x03) == ALIGN_CENTER) {
			point.x -= getStringSize(text, g.getFont()).x / 2;
		}
		else if ((alignment & 0x03) == ALIGN_RIGHT) {
			point.x -= getStringSize(text, g.getFont()).x;
		}
		
		// Font height settings
		if ((alignment & 0x30) == ALIGN_TOP) {
			point.y += getFontMetrics(g.getFont()).getAscent();
		}
		else if ((alignment & 0x30) == ALIGN_MIDDLE) {
			point.y += getFontMetrics(g.getFont()).getAscent();
			point.y -= getFontMetrics(g.getFont()).getHeight() / 2;
		}
		else if ((alignment & 0x30) == ALIGN_BOTTOM) {
			point.y -= getFontMetrics(g.getFont()).getDescent();
		}
		
		g.draw(getGlyphVector(text, g.getFont()).getOutline((float)point.x, (float)point.y));
	}
	/**
	 * Draws the specified wrapped text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringOutlineWrap(Graphics2D g, String text, double x, double y, double width,
			int maxLines, double separation) {
		drawStringOutlineWrap(g, text, new Vector(x, y), 0, width, maxLines, separation);
	}
	/**
	 * Draws the specified wrapped text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - THe text to use.
	 * @param	x - The x coordinate to draw the text at.
	 * @param	y - The y coordinate to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringOutlineWrap(Graphics2D g, String text, double x, double y, int alignment,
			double width, int maxLines, double separation) {
		drawStringOutlineWrap(g, text, new Vector(x, y), alignment, width, maxLines, separation);
	}
	/**
	 * Draws the specified wrapped text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringOutlineWrap(Graphics2D g, String text, Vector point,
			double width, int maxLines, double separation) {
		drawStringOutlineWrap(g, text, point, 0, width, maxLines, separation);
	}
	/**
	 * Draws the specified wrapped text outline to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	text - The text to use.
	 * @param	point - The point to draw the text at.
	 * @param	alignment - The alignment settings to use.
	 * @param	width - The maximum width of the line.
	 * @param	maxLines - The maximum number of lines, use -1 for infinite.
	 * @param	separation - The space between each line.
	 */
	public static void drawStringOutlineWrap(Graphics2D g, String text, Vector point, int alignment,
			double width, int maxLines, double separation) {
		
		String[] lines = getWrappedString(text, g.getFont(), width, maxLines);
		
		for (int i = 0; i < lines.length; i++) {
			drawStringOutline(g, lines[i], (float)point.x, (float)(point.y + i * (getStringSize("", g.getFont()).y + separation)), alignment);
		}
	}
	
	// ====================== Draw Image =======================

	/**
	 * Draws the specified image to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	image - The image to use.
	 * @param	x - The x coordinate to draw the image at.
	 * @param	y - The y coordinate to draw the image at.
	 */
	public static void drawImage(Graphics2D g, Image image, double x, double y) {
		drawImage(g, image, new Vector(x, y), 0);
	}
	/**
	 * Draws the specified image to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	image - The image to use.
	 * @param	x - The x coordinate to draw the image at.
	 * @param	y - The y coordinate to draw the image at.
	 * @param	w - The scaled width of the image.
	 * @param	h - The scaled height of the image.
	 */
	public static void drawImage(Graphics2D g, Image image, double x, double y, double w, double h) {
		drawImage(g, image, new Vector(x, y), new Vector(w, h));
	}
	/**
	 * Draws the specified image to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	image - The image to use.
	 * @param	x - The x coordinate to draw the image at.
	 * @param	y - The y coordinate to draw the image at.
	 * @param	alignment - The alignment settings to use.
	 */
	public static void drawImage(Graphics2D g, Image image, double x, double y, int alignment) {
		drawImage(g, image, new Vector(x, y), alignment);
	}
	/**
	 * Draws the specified image to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	image - The image to use.
	 * @param	point - The point to draw the image at.
	 */
	public static void drawImage(Graphics2D g, Image image, Vector point) {
		drawImage(g, image, point, 0);
	}
	/**
	 * Draws the specified image to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	image - The image to use.
	 * @param	point - The point to draw the image at.
	 * @param	size - The scaled size of the image.
	 */
	public static void drawImage(Graphics2D g, Image image, Vector point, Vector size) {
		if (image != null) {
			g.drawImage(image, (int)point.x, (int)point.y, (int)size.x, (int)size.y, null);
		}
	}
	/**
	 * Draws the specified image to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	image - The image to use.
	 * @param	point - The point to draw the image at.
	 * @param	alignment - The alignment settings to use.
	 */
	public static void drawImage(Graphics2D g, Image image, Vector point, int alignment) {
		point = new Vector(point);
		
		if (image != null) {
			
			// Image width settings
			if ((alignment & 0x03) == ALIGN_CENTER) {
				point.x -= (double)image.getWidth(null) / 2;
			}
			else if ((alignment & 0x03) == ALIGN_RIGHT) {
				point.x -= (double)image.getWidth(null);
			}
			
			// Image height settings
			if ((alignment & 0x30) == ALIGN_MIDDLE) {
				point.y -= (double)image.getHeight(null) / 2;
			}
			else if ((alignment & 0x30) == ALIGN_BOTTOM) {
				point.y -= (double)image.getHeight(null);
			}
			
			g.drawImage(image, (int)point.x, (int)point.y, null);
		}
	}

	// ====================== Draw Shape ======================
	
	/**
	 * Draws the specified line to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	x1 - The x of the first end point.
	 * @param	y1 - The y of the first end point.
	 * @param	x2 - The x of the second end point.
	 * @param	y2 - The y of the second end point.
	 */
	public static void drawLine(Graphics2D g, double x1, double y1, double x2, double y2) {
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	/**
	 * Draws the specified line to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	end1 - The first end point.
	 * @param	end2 - The second end point.
	 */
	public static void drawLine(Graphics2D g, Vector end1, Vector end2) {
		g.drawLine((int)end1.x, (int)end1.y, (int)end2.x, (int)end2.y);
	}
	/**
	 * Draws the specified line to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	l - The line to draw.
	 */
	public static void drawLine(Graphics2D g, Line l) {
		g.drawLine((int)l.end1.x, (int)l.end1.y, (int)l.end2.x, (int)l.end2.y);
	}
	/**
	 * Draws the specified rectangle to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	x - The x coordinate to draw the rectangle at.
	 * @param	y - The y coordinate to draw the rectangle at.
	 * @param	w - The width of the rectangle.
	 * @param	h - The height of the rectangle.
	 */
	public static void drawRect(Graphics2D g, double x, double y, double w, double h) {
		g.drawRect((int)x, (int)y, (int)w, (int)h);
	}
	/**
	 * Draws the specified rectangle to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	point - The point to draw the rectangle at.
	 * @param	size - The size of the rectangle.
	 */
	public static void drawRect(Graphics2D g, Vector point, Vector size) {
		g.drawRect((int)point.x, (int)point.y, (int)size.x, (int)size.y);
	}
	/**
	 * Draws the specified rectangle to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	r - The rectangle to draw.
	 */
	public static void drawRect(Graphics2D g, Rectangle r) {
		g.drawRect((int)r.point.x, (int)r.point.y, (int)r.size.x, (int)r.size.y);
	}
	/**
	 * Draws the specified ellipse to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	x - The x coordinate to draw the ellipse at.
	 * @param	y - The y coordinate to draw the ellipse at.
	 * @param	w - The width of the ellipse.
	 * @param	h - The height of the ellipse.
	 * @param	origin - True if the x and y coordinates are the origin.
	 */
	public static void drawEllipse(Graphics2D g, double x, double y, double w, double h, boolean origin) {
		if (origin)
			g.drawOval((int)(x - w), (int)(y - h), (int)(w * 2), (int)(h * 2));
		else
			g.drawOval((int)x, (int)y, (int)w, (int)h);
	}
	/**
	 * Draws the specified ellipse to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	point - The point to draw the ellipse at.
	 * @param	size - The size of the ellipse.
	 * @param	origin - True if the x and y coordinates are the origin.
	 */
	public static void drawEllipse(Graphics2D g, Vector point, Vector size, boolean origin) {
		if (origin)
			g.drawOval((int)(point.x - size.x), (int)(point.y - size.y), (int)(size.x * 2), (int)(size.y * 2));
		else
			g.drawOval((int)point.x, (int)point.y, (int)size.x, (int)size.y);
	}
	/**
	 * Draws the specified ellipse to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	e - The ellipse to draw.
	 */
	public static void drawEllipse(Graphics2D g, Ellipse e) {
		g.drawOval((int)e.point.x, (int)e.point.y, (int)e.size.x, (int)e.size.y);
	}
	/**
	 * Draws the specified polygon to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	points - The list of x and y coordinates to add to the polygon.
	 */
	public static void drawPolygon(Graphics2D g, double... points) {
		int   nPoints = (points.length + 1) / 2;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		
		for (int i = 0; i < nPoints; i += 2) {
			xPoints[i / 2] = (int)points[i];
			yPoints[i / 2] = (int)points[i + 1];
		}
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	/**
	 * Draws the specified polygon to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	points - The list of points to add to the polygon.
	 */
	public static void drawPolygon(Graphics2D g, Vector... points) {
		int   nPoints = points.length;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int)points[i].x;
			yPoints[i] = (int)points[i].y;
		}
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	/**
	 * Draws the specified polygon to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	r - The rectangle to draw.
	 */
	public static void drawPolygon(Graphics2D g, Polygon p) {
		int   nPoints = p.npoints;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int)p.points[i].x;
			yPoints[i] = (int)p.points[i].y;
		}
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	// ====================== Draw Shape ======================
	
	/**
	 * Draws the specified rectangle to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	x - The x coordinate to draw the rectangle at.
	 * @param	y - The y coordinate to draw the rectangle at.
	 * @param	w - The width of the rectangle.
	 * @param	h - The height of the rectangle.
	 */
	public static void fillRect(Graphics2D g, double x, double y, double w, double h) {
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}
	/**
	 * Draws the specified rectangle to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	point - The point to draw the rectangle at.
	 * @param	size - The size of the rectangle.
	 */
	public static void fillRect(Graphics2D g, Vector point, Vector size) {
		g.fillRect((int)point.x, (int)point.y, (int)size.x, (int)size.y);
	}
	/**
	 * Draws the specified rectangle to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	r - The rectangle to draw.
	 */
	public static void fillRect(Graphics2D g, Rectangle r) {
		g.fillRect((int)r.point.x, (int)r.point.y, (int)r.size.x, (int)r.size.y);
	}
	/**
	 * Draws the specified ellipse to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	x - The x coordinate to draw the ellipse at.
	 * @param	y - The y coordinate to draw the ellipse at.
	 * @param	w - The width of the ellipse.
	 * @param	h - The height of the ellipse.
	 * @param	origin - True if the x and y coordinates are the origin.
	 */
	public static void fillEllipse(Graphics2D g, double x, double y, double w, double h, boolean origin) {
		if (origin)
			g.fillOval((int)(x - w), (int)(y - h), (int)(w * 2), (int)(h * 2));
		else
			g.fillOval((int)x, (int)y, (int)w, (int)h);
	}
	/**
	 * Draws the specified ellipse to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	point - The point to draw the ellipse at.
	 * @param	size - The size of the ellipse.
	 * @param	origin - True if the x and y coordinates are the origin.
	 */
	public static void fillEllipse(Graphics2D g, Vector point, Vector size, boolean origin) {
		if (origin)
			g.fillOval((int)(point.x - size.x), (int)(point.y - size.y), (int)(size.x * 2), (int)(size.y * 2));
		else
			g.fillOval((int)point.x, (int)point.y, (int)size.x, (int)size.y);
	}
	/**
	 * Draws the specified ellipse to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	e - The ellipse to draw.
	 */
	public static void fillEllipse(Graphics2D g, Ellipse e) {
		g.fillOval((int)e.point.x, (int)e.point.y, (int)e.size.x, (int)e.size.y);
	}
	/**
	 * Draws the specified polygon to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	points - The list of x and y coordinates to add to the polygon.
	 */
	public static void fillPolygon(Graphics2D g, double... points) {
		int   nPoints = (points.length + 1) / 2;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		
		for (int i = 0; i < nPoints; i += 2) {
			xPoints[i / 2] = (int)points[i];
			yPoints[i / 2] = (int)points[i + 1];
		}
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
	/**
	 * Draws the specified polygon to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	points - The list of points to add to the polygon.
	 */
	public static void fillPolygon(Graphics2D g, Vector... points) {
		int   nPoints = points.length;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int)points[i].x;
			yPoints[i] = (int)points[i].y;
		}
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
	/**
	 * Draws the specified polygon to the graphics object.
	 * 
	 * @param	g - The graphics object to draw to.
	 * @param	r - The rectangle to draw.
	 */
	public static void fillPolygon(Graphics2D g, Polygon p) {
		int   nPoints = p.npoints;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int)p.points[i].x;
			yPoints[i] = (int)p.points[i].y;
		}
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
}
