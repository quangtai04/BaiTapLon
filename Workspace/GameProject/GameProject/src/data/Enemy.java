package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class Enemy implements Entity {
	private int width, height, currentCheckpoint;
	private float speed, x, y, health, startHealth, hiddenHealth;
	private Texture texture, healthBackground, healthForeground, healthBorder;
	private Tile startTile;
	private boolean first, alive;
	private TileGrid grid;

	private ArrayList<Checkpoint> checkpoints;
	private int[] directions;

	public Enemy(int tileX, int tileY, TileGrid grid) {
		this.texture = QuickLoad("enemyFloating");
		this.healthBackground = QuickLoad("healthBackground");
		this.healthForeground = QuickLoad("healthForeground");
		this.healthBorder = QuickLoad("healthBorder");
		this.startTile = grid.getTile(tileX, tileY);
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = TILE_SIZE;
		this.height = TILE_SIZE;
		this.speed = 50;
		this.health = 50;
		this.startHealth = health;
		this.hiddenHealth = health;
		this.grid = grid;
		this.first = true;
		this.alive = true;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		// X direction
		this.directions[0] = 0;
		// Y direction
		this.directions[1] = 0;
		this.directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpoint();
	}
	
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health) {
		this.texture = texture;
		this.healthBackground = QuickLoad("healthBackground");
		this.healthForeground = QuickLoad("healthForeground");
		this.healthBorder = QuickLoad("healthBorder");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.startHealth = health;
		this.hiddenHealth = health;
		this.grid = grid;
		this.first = true;
		this.alive = true;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		// X direction
		this.directions[0] = 0;
		// Y direction
		this.directions[1] = 0;
		this.directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpoint();
	}

	public void update() {
		//Check if it's the first time this class is updated, if so do nothing 
		if (first)
			first = false;
		else {
			if (checkpointReached()) {
				//Check if there are more checkpoints before moving on
				if (currentCheckpoint + 1 == checkpoints.size())
					endOfMazeReached();
				else
					currentCheckpoint++;
			} else {
				//If not at a checkpoints, continue in current direction
				x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
				y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
			}

		}
	}
	
	//Run When last checkpoint is reached by enemy 
	private void endOfMazeReached() {
		Player.modifyLives(-1);
		die();
	}

	private boolean checkpointReached() {
		boolean reached = false;

		Tile t = checkpoints.get(currentCheckpoint).getTile();
		//Check if position reached tile within variance 3 (arbitrary)
		if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
			reached = true;
			x = t.getX();
			y = t.getY();
		}
		return reached;
	}

	private void populateCheckpoint() {
		//Add first checkpoint manually based on startTile
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));

		int counter = 0;
		boolean cont = true;
		while (cont) {
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			//Check if a next direction/ checkpoint exists, end after 20 checkpoint (arbitrary)
			if (currentD[0] == 2 || counter == 20) {
				cont = false;
			} else {
				checkpoints.add(findNextC(checkpoints.get(counter).getTile(),
						directions = findNextD(checkpoints.get(counter).getTile())));
			}
			counter++;
		}
	}

	private Checkpoint findNextC(Tile s, int[] dir) {
		Tile next = null;
		Checkpoint c = null;

		// Boolean to decide if next checkpoint is found
		boolean found = false;
		// Integer to increment each loop
		int counter = 1;
		while (!found) {
			if (s.getXPlace() + dir[0] * counter == grid.getTilesWide()
					|| s.getYPlace() + dir[1] * counter == grid.getTilesHeigh() || s.getType() != grid
							.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()) {
				found = true;
				// Move counter back 1 to find tile before new tiletype
				counter -= 1;
				next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}

			counter++;
		}
		c = new Checkpoint(next, dir[0], dir[1]);
		return c;
	}

	private int[] findNextD(Tile s) {
		int[] dir = new int[2];
		Tile u = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
		Tile r = grid.getTile(s.getXPlace() + 1, s.getYPlace());
		Tile d = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
		Tile l = grid.getTile(s.getXPlace() - 1, s.getYPlace());
		//Check if current inhabited mathches tiletype above, right, down or left
		if (s.getType() == u.getType() && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		} else if (s.getType() == r.getType() && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		} else if (s.getType() == d.getType() && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		} else if (s.getType() == l.getType()) {
			dir[0] = -1;
			dir[1] = 0;
		} else {
			dir[0] = 2;
			dir[1] = 2;
		}

		return dir;

	}

	//Take damage from external source
	public void damage(int amount) {
		health -= amount;
		if (health <= 0) {
			die();
			Player.modifyCash(5);
		}
	}

	private void die() {
		alive = false;

	}

	public void draw() {
		float healthPercentage = health / startHealth;
		//Enemy texture
		DrawQuadTex(texture, x, y, width, height);
		//Health bar textures
		DrawQuadTex(healthBackground, x, y - 16, width, 8);
		DrawQuadTex(healthForeground, x, y - 16, TILE_SIZE * healthPercentage, 8);
		DrawQuadTex(healthBorder, x, y - 16, width, 8);
	}
	
	public void reduceHiddenHealth(float amount) {
		hiddenHealth -= amount;
	}

	public float getHiddenHealth() {
		return hiddenHealth;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setTexture(String textureName) {
		this.texture = QuickLoad(textureName);
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public TileGrid getTileGrid() {
		return grid;
	}

	public boolean isAlive() {
		return alive;
	}
}
