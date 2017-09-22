package src;

import java.util.Random;

public class Scene {

	private int width;
	private int height;
	public Agent[] agents; // A field of agents
	private Random r;

	public Scene(int w, int h, int n) {
		width = w;
		height = h;
		agents = new Agent[n];
		r = new Random();
		for (int i = 0; i < agents.length; i++) {
			int randx = r.nextInt(width);
			int randy = r.nextInt(height);
			agents[i] = new Agent(1, randx, randy);
		}
	}

	public void turn(float t) {
		for (int i = 0; i < agents.length; i++) {
			agents[i].calcNetForceVector(agents);
		}
		for (int i = 0; i < agents.length; i++) {
			agents[i].calcPos(t);
		}
	}

}
