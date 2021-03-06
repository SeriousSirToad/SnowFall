package woodstock.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import woodstock.io.InputHandler;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -5198863677834462653L;

	public int tickCount;

	GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static Main main;

	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	private static final String NAME = "TrainEngine";

	public boolean running = false;
	public JFrame frame = new JFrame();
	static Dimension gameDimension;
	public static InputHandler input;
	Container con;

	public static Menu menu;

	public Main() {

		main = this;

		gameDimension = new Dimension((int) (WIDTH), (int) (HEIGHT));
		frame.setTitle(NAME);
		frame.setSize(gameDimension);
		frame.setPreferredSize(gameDimension);
		frame.setMinimumSize(gameDimension);
		frame.setMaximumSize(gameDimension);
		frame.add(this, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setUndecorated(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.pack();

		System.out.println(frame.getInsets().top + ", " + frame.getInsets().bottom);

		input = new InputHandler(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println(WIDTH + ", " + HEIGHT);
		// SwingUtilities.invokeLater(this);

		// frame.setLayout(null);
		// con = frame.getContentPane();

		menu = new Menu();

	}

	float frames = 0;

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60;
		int ticks = 0;
		float frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		double delta2 = 0;

		while (running) {
			if (!stateInit) {
				stateInit = true;
				GameState.init();
			}
			long now = System.nanoTime();
			double renderTime = 1000000000D / 60;
			delta += (now - lastTime) / nsPerTick;
			delta2 += (now - lastTime) / renderTime;
			lastTime = now;
			boolean shouldRender = true;
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
			}

			while (delta2 >= 1) {
				delta2 -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				render();
				frames++;
			}
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				this.frames = frames;
				ticks = 0;
				frames = 0;
			}

		}
	}

	boolean stateInit = false;

	public void tick() {

		tickCount++;

		if (GameState.running) {
			GameState.player.getLevel().tick();
			GameState.camera.tick();
		} else {
			menu.tick();
		}

	}

	BufferStrategy bs;
	public static Graphics2D g;

	int xOffset = 0;
	int yOffset = 0;

	public void render() {

		// Creating graphics object
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		g = (Graphics2D) bs.getDrawGraphics();
		// General Rendering

		g.clearRect(0, 0, width(), width());

		g.scale(GameState.renderScale, GameState.renderScale);
		GameState.camera.render();

		bs.show();
		g.dispose();

	}

	public void start() {

		running = true;
		new Thread(this).start();

	}

	public static int width() {
		return (int) (WIDTH);
	}

	public static int height() {
		return (int) (HEIGHT);
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
