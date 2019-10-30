package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Projectile {

	private Texture texture;
	private float x, y, speed;
	private int damage;

	public Projectile(Texture texture, float x, float y, float speed, int damage) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
	}
	
	public void update() {
		x += Delta() * speed;
		draw();
	}
	
	public void draw() {
		DrawQuadTex(texture, x, y, 20, 20);
	}
}
