package woodstock.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public BufferedImage sheet;
	
	public SpriteSheet(String imagePath) {
		
		try {
			sheet = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public BufferedImage getImage(int x, int y, int w, int h) {
		return sheet.getSubimage(x, y, w, h);
	}
	
	public BufferedImage[] getAnimation(int startX, int startY, int cellW, int cellH, int numFrames) {
		
		BufferedImage[] frames = new BufferedImage[numFrames];
		for(int i = 0; i < numFrames; i++) {
			
			frames[i] = sheet.getSubimage(startX + (i * cellW), startY, cellW, cellH);
			
		}
		
		
		return frames;
	}

}
