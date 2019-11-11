package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.*;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.Button;
import UI.UI;
import UI.UI.Menu;
import helpers.Clock;
import helpers.StateManager;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Menu backMenu;
	private Menu StartAndPause;
	private Texture menuBackGround;
	private Enemy[] enemyTypes;
	private boolean back_menu = false, replay = false;
	private int waveNumber = 0;
	private int indexMap;
	private int soLuongQuan = 5;
	private boolean nextMap = false, priviousMap = false;

	public Game(TileGrid grid, int indexMap) {
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemyTypes[0] = new EnemyAlien(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[0].setSpeed(100);
		enemyTypes[1] = new EnemyUFO(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[1].setSpeed(100);
		enemyTypes[2] = new EnemyPlane(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[2].setSpeed(100);
		waveManager = new WaveManager(enemyTypes, 1, soLuongQuan);
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackGround = QuickLoad("menu_background_2");
		this.indexMap = indexMap;
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.createMenu("TowerPicker", 800, 50, 200, 200, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("RedCannon", "cannonRedFull", 40, 40);
		towerPickerMenu.quickAdd("IceCannon", "cannonIceFull", 40, 40);

		gameUI.addButton("Start", "start", 820, 330, 80, 60);
		gameUI.addButton("Pause", "pause", 910, 330, 80, 60);

		gameUI.addButton("Menu", "HomeMenu", 820, 510, 300, 50);

		gameUI.addButton("BackMap", "back", 820, 420, 80, 80);
		gameUI.addButton("NextMap", "next", 920, 420, 80, 80);
	}

	private void updateUI() {
		gameUI.draw();
		gameUI.drawString(820, 150, "MAP: " + (indexMap + 1));
		gameUI.drawString(840, 200, "Lives: " + player.Lives);
		gameUI.drawString(840, 240, "Cash: $" + player.Cash);
		gameUI.drawString(840, 280, "Wave " + waveManager.getWaveNumber() + "/10");
		gameUI.drawStringSmall(0, 0, StateManager.framesInLastSecond + " fps");
		gameUI.drawStringSmall(815, 100, "Tower Red");
		gameUI.drawStringSmall(900, 100, "Tower Ice");

		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			if (mouuseClicked) {
				if (towerPickerMenu.isButtonClicked("RedCannon")) // Bat su kien khi click vao button
					player.pickTower(new TowerCannonBlue(TowerType.CannonRed, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (towerPickerMenu.isButtonClicked("IceCannon")) // bat su kien khi click vao button
					player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (gameUI.isButtonClicked("Menu")) {
					back_menu = true;
				}
				if (gameUI.isButtonClicked("Start")) {
					Clock.setMultiplier(1);
					waveNumber = waveManager.getWaveNumber();
				}
				if (gameUI.isButtonClicked("Pause")) {
					Clock.setMultiplier(0);
				}
				if (gameUI.isButtonClicked("NextMap") && waveNumber == 0) {
					nextMap = true;
				}
				if (gameUI.isButtonClicked("BackMap") && waveNumber == 0) {
					priviousMap = true;
				}
			}
		}
	}

	public void update() {
		DrawQuadTex(menuBackGround, 800, 0, 200, 600);
		grid.draw();
		if (waveManager.getWaveNumber() == waveNumber) {
			waveManager.update();
		} else if (waveNumber == 0) {
			Clock.setMultiplier(0);
		} else if (waveManager.getWaveNumber() > waveNumber && waveNumber !=0) {
			waveManager = new WaveManager(enemyTypes, 1, soLuongQuan * waveManager.getWaveNumber());
			Clock.setMultiplier(0);
		}

		player.update();
		updateUI();

		if (GameLose() || GameWin()) {
			if (GameLose()) {
				drawGameLose();
				Clock.setMultiplier(0);
				gameUI.addButton("GameOver", "lose", 240, 230, 800, 200);
				gameUI.addButton("Replay", "replay", 275, 400, 140, 50);
				gameUI.addButton("BackMenu", "HomeMenu", 550, 400, 200, 50);

				if (Mouse.isButtonDown(0)) {
					if (gameUI.isButtonClicked("Replay")) {
						setGameReplay(true);
					}
					if (gameUI.isButtonClicked("BackMenu")) {
						setBackMenu(true);
					}
				}
			}
			if (GameWin()) {
				drawGameWin();
				Clock.setMultiplier(0);
				gameUI.addButton("GameWin", "victory", 240, 230, 800, 200);
				gameUI.addButton("Replay", "replay", 275, 400, 140, 50);
				gameUI.addButton("nextMapWin", "nextMap", 550, 400, 200, 50);

				if (Mouse.isButtonDown(0)) {
					if (gameUI.isButtonClicked("Replay")) {
						setGameReplay(true);
					}
					if (gameUI.isButtonClicked("nextMapWin")) {
						setNextMap(true);
					}
				}
			}
		}
	}

	public void drawGameWin() {

	}

	public void drawGameLose() {

	}

	public boolean GameLose() {
		if (player.getGameLose())
			return true;
		return false;
	}

	public boolean GameWin() {
		if (waveManager.getWaveNumber() > player.livesCount)
			return true;
		return false;
	}

	public boolean getBackMenu() {
		return back_menu;
	}

	public void setBackMenu(boolean back_menu) {
		this.back_menu = back_menu;
	}

	public boolean getNextMap() {
		return nextMap;
	}

	public boolean getPriviousMap() {
		return priviousMap;
	}

	public void setNextMap(boolean nextMap) {
		this.nextMap = nextMap;
	}

	public void setPriviousMap(boolean priviousMap) {
		this.priviousMap = priviousMap;
	}

	public boolean getGameReplay() {
		return replay;
	}

	public void setGameReplay(boolean replay) {
		this.replay = replay;
	}

}
