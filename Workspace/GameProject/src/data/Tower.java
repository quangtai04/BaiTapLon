package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public abstract class Tower implements Entity {
	private float x, y;
	private int width, height, damage;
	private Enemy target;
	private Texture[] textures;

	public Tower(TowerType type, Tile startTile) {
		this.textures = type.textures;
		this.damage = type.damage;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
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
		for(int i=0; i<textures.length; i++)
			DrawQuadTex(textures[i], x, y, width, height);
	}

}
