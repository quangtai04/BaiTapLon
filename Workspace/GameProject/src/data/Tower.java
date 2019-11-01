package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public abstract class Tower implements Entity {
	private float x, y;
	private int width, height, damage;
	private Enemy target;
	private Texture texture;

	public Tower(Texture texture, float x, float y, int width, int height) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void update() {

	}

	public void draw() {
		DrawQuadTex(texture, x, y, width, height);
	}

}
