package main;

import game.Profile;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * This class sets up a windows and starts a thread to update and render
 * the Game class.
 * 
 * @author	Robert Jordan
 * @author	David Jordan
 * 
 * @see
 * {@linkplain Game}
 */
public class Main implements Runnable {

	// =================== Private Variables ==================
	
	/** The size of the frames array. */
	private static final int	MAX_TIMES		= 8;
	/** The array of times for each frame used to get the average. */
	private static long[]		frameTimes		= new long[MAX_TIMES];
	/** The current frame in the array of frames. */
	private static int			frameCurrent	= 0;

	// ==================== Public Constants ==================
	
	/**
	 * The name of the game. Classes can reference this when needed. This is
	 * useful in case the name has to be changed.
	 */
	public static final String	GAME_NAME		= "Metroid Map Guardian";
	/**
	 * The default desired update rate of the game in frames-per-second.
	 * This is used to maintain conversion rates for system time to step
	 * time.
	 * 
	 * @see
	 * {@linkplain time.Time}
	 */
	public static final int		FPS				= 60;
	/**
	 * The conversion rate for how long a step lasts.  This value is represented
	 * in milliseconds per frame. See FPS for more details.
	 * 
	 * @see
	 * {@linkplain #FPS},
	 * {@linkplain time.Time}
	 */
	public static final int		STEP_TIME		= 1000 / FPS;
	
	// ==================== Public Variables ==================
	
	/** The variable that states whether the game loop should terminate. */
	public static boolean		running;
	/** The frame component that the game is drawn to. */
	public static JFrame		frame;
	/** The game class running the game. */
	public static Game			game;

	/** The desired update rate of the game for frames-per-second. */
	public static int			fpsDesired;
	/** The current update rate of the game in frames-per-second. */
	public static double		fps;
	
	private static boolean fullScreenEnabled = false;
	
	private static GraphicsDevice graphicsDevice;

	// ====================== Entry Point =====================
	
	/**
	 * The entry point for the program. Everything is run from this function.
	 * 
	 * @param	args - The arguments to execute the program with.
	 */
	public static void main(String [] args) {
		System.setProperty("sun.java2d.d3d", "false");
    	new Main();
	}
	/**
	 * Constructs the main class that will run the program. After the class
	 * has been created a new thread will begin executing it's run function.
	 */
	public Main() {
		//GraphicsEnvironment.getLocalGraphicsEnvironment().
		
		ResourceLoader.preloadResources();
		
		final SplashScreen splash = SplashScreen.getSplashScreen();
		/*if (splash != null) {
			Graphics2D g = splash.createGraphics();
			java.awt.Rectangle bounds = splash.getBounds();
			Draw.drawImage(g, ImageLoader.getImage("splash"), new Vector(bounds.width, bounds.height).scale(0.5));
			g.drawImage(ImageLoader.getImage("splash"), 0, 0, null);
			g.drawImage(ImageLoader.getImage("splash"), bounds.x, bounds.y, null);
			Draw.drawImage(g, ImageLoader.getImage("splash"), new Vector(bounds.x, bounds.y));
			/*int fontSize = 55;
			int x = 260;
			int y = 60;
			int offset = (int)(fontSize * 0.24);
			int spacing = 80;
			int color1 = 255;
			int color2 = 90;
			Color gradient1 = new Color(color1, color1, color1);
			Color gradient2 = new Color(color2, color2, color2);
			
			
			BufferedImage texture = new BufferedImage(ImageLoader.getImage("header_texture").getWidth(null),
													ImageLoader.getImage("header_texture").getHeight(null),
													BufferedImage.TYPE_INT_ARGB);
			Draw.drawImage((Graphics2D)texture.getGraphics(), ImageLoader.getImage("header_texture"), new Vector(0, 0));
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(new Font("Eurostar Black Extended", Font.PLAIN, fontSize));
			int fontHeight = (int)Draw.getStringSize("Metroid", g.getFont()).y - offset * 2;
			//g.setStroke(new BasicStroke(2.0f));
			//g.setPaint(new TexturePaint(texture, new java.awt.geom.Rectangle2D.Double(x, y, x, y + (fontSize + spacing) * 2)));
			g.setPaint(new GradientPaint(x, y + offset, gradient1, x, y + offset + fontHeight, gradient2));
			Draw.drawString(g, "Metroid",	new Vector(x, y), Draw.ALIGN_TOP);
			//Draw.drawLine(g, x, y + offset, x, y + offset + fontHeight);
			y += spacing;
			g.setPaint(new GradientPaint(x, y + offset, gradient1, x, y + offset + fontHeight, gradient2));
			Draw.drawString(g, "Map",		new Vector(x, y), Draw.ALIGN_TOP);
			//Draw.drawLine(g, x, y + offset, x, y + offset + fontHeight);
			y += spacing;
			g.setPaint(new GradientPaint(x, y + offset, gradient1, x, y + offset + fontHeight, gradient2));
			Draw.drawString(g, "Guardian",	new Vector(x, y), Draw.ALIGN_TOP);
			//Draw.drawLine(g, x, y + offset, x, y + offset + fontHeight);
			//splash.update();
			
			try {
				BufferedImage image = new BufferedImage(ImageLoader.getImage("splash").getWidth(null),
													ImageLoader.getImage("splash").getHeight(null),
													BufferedImage.TYPE_INT_ARGB);
				y = 60;
				g = (Graphics2D)image.getGraphics();
				Draw.drawImage(g, ImageLoader.getImage("splash"), 0, 0);
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g.setFont(new Font("Eurostar Black Extended", Font.PLAIN, fontSize));
				fontHeight = (int)Draw.getStringSize("Metroid", g.getFont()).y - offset * 2;
				//g.setStroke(new BasicStroke(2.0f));
				//g.setPaint(new TexturePaint(texture, new java.awt.geom.Rectangle2D.Double(x, y, x, y + (fontSize + spacing) * 2)));
				g.setPaint(new GradientPaint(x, y + offset, gradient1, x, y + offset + fontHeight, gradient2));
				Draw.drawString(g, "Metroid",	new Vector(x, y), Draw.ALIGN_TOP);
				//Draw.drawLine(g, x, y + offset, x, y + offset + fontHeight);
				y += spacing;
				g.setPaint(new GradientPaint(x, y + offset, gradient1, x, y + offset + fontHeight, gradient2));
				Draw.drawString(g, "Map",		new Vector(x, y), Draw.ALIGN_TOP);
				//Draw.drawLine(g, x, y + offset, x, y + offset + fontHeight);
				y += spacing;
				g.setPaint(new GradientPaint(x, y + offset, gradient1, x, y + offset + fontHeight, gradient2));
				Draw.drawString(g, "Guardian",	new Vector(x, y), Draw.ALIGN_TOP);
				//Draw.drawLine(g, x, y + offset, x, y + offset + fontHeight);
				ImageIO.write(image, "png", new File("mySplash.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Cannot load Splash Screen!");
			return;
		}*/

		frame		= new JFrame(GAME_NAME);
		frame.setLayout(new BorderLayout());
		game		= new Game();
		
		graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		fpsDesired	= FPS;
		fps			= 0.0;
		
		// Add the game to the frame's content pane:
		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(game);
		frame.addKeyListener(new Keyboard());
		
		frame.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
			
				if (Game.instance != null) {
					if (!Game.instance.inMenu) {
						new Profile(Game.instance).saveProfile();
					}
				}
				
			}

			public void windowActivated(WindowEvent arg0) {}

			public void windowClosed(WindowEvent arg0) {}

			public void windowDeactivated(WindowEvent arg0) {}

			public void windowDeiconified(WindowEvent arg0) {}

			public void windowIconified(WindowEvent arg0) {}

			public void windowOpened(WindowEvent arg0) {}
		});

		// Set Icon
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(ImageLoader.getImage("icon16"));
		icons.add(ImageLoader.getImage("icon24"));
		icons.add(ImageLoader.getImage("icon32"));
		icons.add(ImageLoader.getImage("icon48"));
		icons.add(ImageLoader.getImage("icon64"));
		icons.add(ImageLoader.getImage("icon96"));
		icons.add(ImageLoader.getImage("icon128"));
		frame.setIconImages(icons);
		
		// Configure the frame settings:
		frame.setPreferredSize(new Dimension(Game.viewSize.x + 18, Game.viewSize.y + 40));
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setResizable(true);
    	
		if (splash != null) {
			splash.close();
		}
		
    	frame.setVisible(true);
    	
    	// Move the frame to the center of the screen:
    	Dimension screenSize = frame.getToolkit().getScreenSize();
    	frame.setLocation((screenSize.width / 2) - (frame.getWidth() / 2), (screenSize.height / 2) - (frame.getHeight() / 2));
    	
    	// Start the thread and request focus for the frame:
    	new Thread(this).start();
    	frame.requestFocusInWindow();
	}
	
	// ======================= Execution ======================
	
	/**
	 * Ends the main game loop by preventing the run loop to continue after
	 * the current step.
	 */
	public static void stop() {
		running = false;
		if (Game.instance != null) {
			if (!Game.instance.inMenu) {
				new Profile(Game.instance).saveProfile();
			}
		}
	}
	/**
	 * The main loop for running the game. This will update and draw the game
	 * every step. The function will sleep until it is time for the next step.
	 * The sleep time is based off how long it took to run the step and the
	 * frames per second constant.
	 */
	public void run() {
    	long time = System.currentTimeMillis();
		running   = true;
		
		while (running) {
			// Update the mouse and keyboard inputs, then update and repaint the game:
			frame.requestFocusInWindow();
			Keyboard.update();
			Mouse.update();
			game.update();
			game.repaint();
			
			updateFPS();
			
			// End the game if the user presses Escape:
			//if (Keyboard.escape.pressed())
			//	Main.stop();
			
			// Wait until the next frame:
			try {
				time += 1000 / fpsDesired;
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// End the game:
		System.exit(0);
	}
	/**
	 * Changes the full screen mode.
	 * 
	 * @param	fullScreen - Whether to enable or disable fullScreen mode.
	 */
	public static void setFullScreenMode(boolean fullScreen) {
		if (fullScreenEnabled && !fullScreen) {
			frame.dispose();
			frame.setUndecorated(false);
			frame.setVisible(true);
			frame.setResizable(true);
			graphicsDevice.setFullScreenWindow(null);
		}
		else if (!fullScreenEnabled && fullScreen) {
			frame.dispose();
			frame.setUndecorated(true);
			frame.setVisible(true);
			frame.setResizable(false);
			graphicsDevice.setFullScreenWindow(frame);
		}
		fullScreenEnabled = fullScreen;
	}
	public static boolean getFullScreenMode() {
		return fullScreenEnabled;
	}
	/**
	 * Updates the frames-per-second variable to give an average, more readable
	 * value.
	 */
	private static void updateFPS() {
		// Get current steps per second
		frameTimes[frameCurrent] = System.currentTimeMillis();
		int frameLast = frameCurrent + 1;
		if (frameLast >= MAX_TIMES) {
			frameLast = 0;
		}
		// Steps per second = 1000 / (steps[current] - steps[current - maxSteps + 1]) * (maxSteps - 1);
		fps = 1000.0 / (double)(frameTimes[frameCurrent] - frameTimes[frameLast]) * (double)(MAX_TIMES - 1);
		
		frameCurrent = frameLast;
	}
	
}


