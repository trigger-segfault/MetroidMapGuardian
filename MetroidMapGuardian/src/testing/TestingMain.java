package testing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TestingMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Color maskColor = new Color(254, 222, 1);
			Image transformImage	= new ImageIcon("transformImage.png").getImage();
			Image maskImage			= new ImageIcon("maskImage.png").getImage();
			
			int width	= transformImage.getWidth(null);
			int height	= transformImage.getHeight(null);
			
			BufferedImage image		= new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			BufferedImage mask		= new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			image.getGraphics().drawImage(transformImage, 0, 0, null);
			mask.getGraphics().drawImage(maskImage, 0, 0, null);
			
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (mask.getRGB(x, y) == maskColor.getRGB()) {
						int rgb = image.getRGB(x, y);
						
						short alpha	= (short)(((rgb & 0xFF000000) >> 24) & 0x00FF);
						short red	= (short)((rgb & 0xFF0000) >> 16);
						short green	= (short)((rgb & 0xFF00) >> 8);
						short blue	= (short)((rgb & 0xFF));
						
						short average = (short)((red + green + blue) / 3);

						alpha = average;
						red = 255;
						green = 255;
						blue = 255;
						
						int pixelColor = 0;
						pixelColor += (alpha & 0xFF) << 24;
						pixelColor += (red   & 0xFF) << 16;
						pixelColor += (green & 0xFF) << 8;
						pixelColor += (blue  & 0xFF);

						image.setRGB(x, y, pixelColor);
					}
				}
			}
			
			
			File file = new File("fixedImage.png");
			ImageIO.write(image, "png", file);
			
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
