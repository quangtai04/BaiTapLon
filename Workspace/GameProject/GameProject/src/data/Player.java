package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import UI.UI;
import UI.UI.Menu;
import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private boolean leftMouseButtonDown, holdingTower;
	private Tower tempTower;
	public static int Cash, Lives;
	public int livesCount = 2, cashCount = 1000;

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
		this.holdingTower = false;
		this.tempTower = null;
		Cash = 0;
		Lives = 0;
	}

	// Cai dat Cash va Lives
	public void setup() {
		Cash = cashCount;
		Lives = livesCount;
	}

	public static boolean modifyCash(int amount) { // Mua tower
		if (Cash + amount >= 0) {
			Cash += amount;
			return true;
		}
		return false;
	}

	public static boolean modifyCash2(int amount) { // Kiem tra xem tien con du khong
		if (Cash + amount >= 0)
			return true;
		return false;

	}

	public static void modifyLives(int amount) {
		Lives += amount;
	}

	public void update() {
		// Cap nhat Tower
		if (Keyboard.next()) {									// Xoa lua chon Tower 
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState())
				tempTower = null;
		} else if (holdingTower && tempTower != null) {
			tempTower.setX(getMouseTile().getX());
			tempTower.setY(getMouseTile().getY());
			tempTower.draw();
		}

		// cap nhat tat ca tower
		for (Tower t : towerList) {
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			placeTower();
		}

		leftMouseButtonDown = Mouse.isButtonDown(0);
		// Tang giam toc do game
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
		if (holdingTower && tempTower != null) {
			Tower tower = tempTower;
			if (modifyCash2(-tempTower.getCost()) && !currentTile.getOcccupied()
					&& currentTile.getType() == TileType.Grass) {
				towerList.add(tempTower);
				currentTile.setOccupied(true); // set lai Tile bi chiem dong
				holdingTower = false;
				tempTower = null;
				modifyCash(-tower.getCost());
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
	
	public void removeTower(int x, int y)	{
		int i = 0;
		while(i<towerList.size()) {
			if((int)towerList.get(i).getX() == x*TILE_SIZE && (int)towerList.get(i).getY() == y*TILE_SIZE)	{
				towerList.remove(i);
				return;
			}
			else
				i++;
		}
	}
	public Tower findTower(int x, int y) {
		Tower towerFind = null;
		int i = 0;
		while(i<towerList.size()) {
			if((int)towerList.get(i).getX() == x*TILE_SIZE && (int)towerList.get(i).getY() == y*TILE_SIZE)	{
				return towerList.get(i);
			}
			else
				i++;
		}
		return towerFind;
	}
	public Tower getTempTower()	{
		return this.tempTower;
	}
}
