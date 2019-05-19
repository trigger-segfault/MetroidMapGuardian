package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JComponent;

import entity.EntityLoader;

import game.GameInstance;
import graphics.Draw;

/**
 * The static class that runs the base of the game.
 * 
 * @author David Jordan
 */
public class Game extends JComponent {
	
	private static final long serialVersionUID = 1L;
	public static Point viewSize = new Point(900, 700);
    public static GameInstance instance;
    public static boolean debugMode = false;
    
	
    /** Setup the canvas and install listeners. **/
	public Game() {
		this.setFocusable(true);
		EntityLoader.registerEntities();
		
		// Install Mouse, Load images, and initialize the game:
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseWheelListener(mouse);
		
        initializeGame();
	}
	
    /** Initialize any game objects. **/
	public void initializeGame() {
		// INITIALIZE OBJECTS:
		ResourceLoader.loadResources();
		instance = new GameInstance();
		instance.initialize();
		
	}
	

    /** Update the game objects. **/
	public void update() {
		// UPDATE EVENTS HERE:
		
		instance.update();
		

		if (Keyboard.getKey(KeyEvent.VK_F3).pressed())
			debugMode = !debugMode;
		if (Keyboard.getKey(KeyEvent.VK_F11).pressed())
			Main.setFullScreenMode(!Main.getFullScreenMode());
		
		repaint();
	}

    /** Draw on the canvas. **/
	public void render(Graphics2D g) {

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// DRAW EVENTS HERE:
		instance.draw(g);
		
		// Draw debig information:
		if (debugMode) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Eurostar", Font.PLAIN, 16));
			DecimalFormat df = new DecimalFormat("#,##0.0##");
			DecimalFormat lf = new DecimalFormat("#,##0");
			
			double fps			= Main.fps;
			long maxMemory		= Runtime.getRuntime().maxMemory();
			long totalMemory	= Runtime.getRuntime().totalMemory();
			long freeMemory		= Runtime.getRuntime().freeMemory();
			double usedMemory	= (double)(Runtime.getRuntime().totalMemory() -
					Runtime.getRuntime().freeMemory()) / (double)Runtime.getRuntime().maxMemory();
			
			double x = 112;
			double y = 8;
			double spacing = 20;

			Draw.drawString(g, "FPS: ", x, y, Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
			Draw.drawString(g, df.format(fps), x, y, Draw.ALIGN_TOP);
			y += spacing;
			Draw.drawString(g, "Max Memory: ", x, y, Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
			Draw.drawString(g, lf.format(maxMemory), x, y, Draw.ALIGN_TOP);
			y += spacing;
			Draw.drawString(g, "Total Memory: ", x, y, Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
			Draw.drawString(g, lf.format(totalMemory), x, y, Draw.ALIGN_TOP);
			y += spacing;
			Draw.drawString(g, "Free Memory: ", x, y, Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
			Draw.drawString(g, lf.format(freeMemory), x, y, Draw.ALIGN_TOP);
			//Draw.drawString(g, df.format(freeMemory), x, y, Draw.ALIGN_TOP);
			y += spacing;
			Draw.drawString(g, "Memory Used: ", x, y, Draw.ALIGN_RIGHT | Draw.ALIGN_TOP);
			Draw.drawString(g, df.format(usedMemory), x, y, Draw.ALIGN_TOP);
		}
	}
	
	@Override
    /** Handle rendering and draw the raw canvas image to the window. **/
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(g.getClipBounds().x, g.getClipBounds().y,
				g.getClipBounds().width, g.getClipBounds().height);
		render(g2);
	}
}
