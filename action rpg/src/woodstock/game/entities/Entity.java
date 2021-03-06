package woodstock.game.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import woodstock.game.GameState;
import woodstock.game.Main;
import woodstock.game.level.Level;

public abstract class Entity {

	public int x, y;
	public int w, h;
	public BufferedImage image;
	protected Level level;
	protected boolean isSolid = false;
	public boolean isMob = false;
	public Rectangle collider;
	protected boolean initialized;

	public Entity(Level level, int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.level = level;
		level.entities.add(this);
		w = image.getWidth();
		h = image.getHeight();
		collider = new Rectangle(x, y + 3 * (h / 4), w, h / 4);
	}

	public void tick() {
		if (!initialized)
			init();
		update();
	}

	public abstract void update();

	public void render() {
		if (GameState.camera.contains(this)) {
			Main.g.drawImage(image, x - GameState.camera.x, y - GameState.camera.y, w, h, null);
		}
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		level.tick();
		this.level.entities.remove(this);
		level.entities.add(this);
		this.level = level;
	}

	public void init() {
		if (isSolid) {
			level.colliders.add(collider);
		}
		initialized = true;
	}

	public void makeCollider(int x, int y, int w, int h) {
		collider = new Rectangle(this.x + x, this.y + y, w, h);
	}

}
