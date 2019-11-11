package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private boolean leftMouseButtonDown, rightMouseButtonDown, holdingTower;
	private Tower tempTower;
	public static int Cash, Lives;
	public int livesCount = 1000, cashCount = 1000;

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[6];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.types[1] = TileType.DirtStart;
		this.types[1] = TileType.DirtEnd;
		this.types[5] = TileType.GrassNot;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
		this.holdingTower = false;
		this.tempTower = null;
		Cash = 0;
		Lives = 0;
	}

	// Initialize Cash and Lives values for player
	public void setup() {
		Cash = cashCount;
		Lives = livesCount;
	}

	// Check if player can afford tower, if so: charge player tower cost
	public static boolean modifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;
			System.out.println(Cash);
			return true;
		}
		System.out.println(Cash);
		return false;
	}

	public static boolean modifyCash2(int amount) {
		if (Cash + amount >= 0)
			return true;
		return false;

	}

	public static void modifyLives(int amount) {
		Lives += amount;
	}

	public void update() {
		// Update holding tower
		if (holdingTower) {
			tempTower.setX(getMouseTile().getX());
			tempTower.setY(getMouseTile().getY());
			tempTower.draw();
		}
		// Update all tower in the game
		for (Tower t : towerList) {
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			placeTower();
		}
		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
			System.out.println("Right Clicked");
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);
		rightMouseButtonDown = Mouse.isButtonDown(1);
		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}
		}
	}

	private void placeTower() {
		Tile currentTile = getMouseTile();
		if (holdingTower) {
			Tower tower = tempTower;
			if (modifyCash2(-tempTower.getCost()) && !currentTile.getOcccupied()
					&& currentTile.getType() == TileType.Grass) {
				towerList.add(tempTower);
				currentTile.setOccupied(true);
				holdingTower = false;
				tempTower = null;
				this.Cash = this.Cash - tower.getCost();
			}
		}

	}

	public void pickTower(Tower t) {
		tempTower = t;
		holdingTower = true;
	}

	private Tile getMouseTile() {
		return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
	}

	public boolean getGameLose() {
		if (Lives <= 0)
			return true;
		return false;
	}

	public void setWaveManager(WaveManager waveManager) {
		this.waveManager = waveManager;
	}

}
