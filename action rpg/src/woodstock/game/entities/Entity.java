package woodstock.game.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import woodstock.game.GameState;
import woodstock.game.Main;
import woodstock.game.level.Level;

public abstract class Entity {

	public int x, y;
	public int w, h;
	public static final int entScale = (int) (4 * Main.SCALE);
	public BufferedImage image;
	protected Level level;
	protected boolean isSolid = false;
	public Rectangle collider;

	public Entity(Level level, int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.level = level;
		level.entities.add(this);
		w = image.getWidth();
		h = image.getHeight();
		collider = new Rectangle(x, y + (h / 2), w, h / 2);
	}

	public void tick() {
		update();
	}

	public abstract void update();

	public void render() {
		if (GameState.camera.contains(this)) {
			Main.g.drawImage(image, x - GameState.camera.x, y - GameState.camera.y, w * entScale, h * entScale, null);
		}
	}
	
	public Level getLevel() {
		return level;
	}

}
