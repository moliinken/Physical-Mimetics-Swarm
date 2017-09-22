package src;

public class Agent {

	public static final double GRAVITATIONAL_CONSTANT = 1200; // The gravitational constant
	public static final float THRESHOLD = 50;
	public int mass; // mass of the agent, usually 1, in kilograms
	public double x, y; // position of the agent in the grid, in meters
	private double vx, vy; // velocity of the agent in the grid, in meters per second
	private double fx, fy; // net force acting on the agent, in Newtons

	public Agent(int mass, int xpos, int ypos) {
		this.mass = mass;
		x = xpos;
		y = ypos;
		vx = 0;
		vy = 0;
		fx = 0;
		fy = 0;
	}

	private void calcForceVector(Agent a) {
		double r = getDistance(this, a);
		int sign;

		if (0 < r && r <= THRESHOLD) {
			sign = 1;
		} else if (r > THRESHOLD && r <= 1.5 * THRESHOLD) {
			sign = -1;
		} else if (r > 1.5 * THRESHOLD) {
			sign = 0;
		} else {
			sign = 0;
		}
		if (Math.random() > 0.7) {
			if (sign == 1) {
				sign = -1;
			} else if (sign == -1) {
				sign = 1;
			}
		}
		if (this.x != a.x && this.y != a.y) {
			fx += sign * GRAVITATIONAL_CONSTANT * a.mass * this.mass / (r * r * r) * ((this.x - a.x));
			fy += sign * GRAVITATIONAL_CONSTANT * a.mass * this.mass / (r * r * r) * ((this.y - a.y));
		}
	}

	public void calcNetForceVector(Agent[] a) {
		fx = 0;
		fy = 0;
		for (int i = 0; i < a.length; i++) {
			calcForceVector(a[i]);
		}
	}

	public void calcPos(float t) {
		vx = fx * t / mass;
		vy = fy * t / mass;
		x += vx * t;
		y += vy * t;
	}

	public double getDistance(Agent a, Agent b) {
		return Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)));
	}

}
