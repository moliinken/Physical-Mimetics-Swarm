package src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Viewer extends Canvas implements Runnable {

	public static final int HEIGHT = 720;
	public static final int WIDTH = (int) (HEIGHT * 16.0 / 9.0);
	public final String TITLE = "Hexagonal Artificial Physics Swarm";
	public JFrame frame;

	private Thread thread;
	private boolean running;

	private Scene environment;

	public Viewer() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame = new JFrame(TITLE);
		thread = new Thread(this, TITLE);
		environment = new Scene(WIDTH, HEIGHT, 300);
	}

	public void start() {
		thread.start();
		running = true;
	}

	public void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		environment.turn(1f); // update with a delta t of 1 seconds. Updates run at a frequency of 60 Hz, therefore each real-time second is 60 seconds in-simulation.
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.white);
		g2d.fill(new Rectangle2D.Double(0, 0, WIDTH, HEIGHT));
		g2d.setColor(Color.black);

		for (int i = 0; i < environment.agents.length; i++) {
			g2d.fill(new Ellipse2D.Double(environment.agents[i].x, environment.agents[i].y, 5, 5));
		}

		g.dispose();
		bs.show();
	}

	public void run() {
		int delta = 1000 / 1000; // the period, or the amount of milliseconds intended per cycle. T = 1 / f
		int delta1 = 1000 / 144; // a secondary period, same laws as delta
		long current = System.currentTimeMillis(); // the initial time, in milliseconds
		long end = current + delta; // the end time of a cycle of frequency f
		long end1 = current + delta1;
		while (running) {
			current = System.currentTimeMillis(); // find the new current time, in milliseconds
			if (current >= end) { // if has reached end of an update period of 1 / f (primary)
				update();
				end = current + delta; // shift the new end time by the length of a period from the previous update
			}
			if (current >= end1) { // secondary
				render();
				end1 = current + delta1;
			}
		}
	}

	public static void main(String[] args) {
		Viewer viewer = new Viewer();
		viewer.frame.add(viewer);
		viewer.frame.setUndecorated(false);
		viewer.frame.pack();
		viewer.frame.setLocationRelativeTo(null);
		viewer.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewer.frame.setVisible(true);
		viewer.start();
	}

}
