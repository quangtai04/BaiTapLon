package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.*;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.Button;
import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Texture menuBackGround;
	private Enemy[] enemyTypes;

	public Game(TileGrid grid) {
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemyTypes[0] = new EnemyAlien(2, 0, grid);
		enemyTypes[1] = new EnemyUFO(2, 0, grid);
		enemyTypes[2] = new EnemyPlane(2, 0, grid);
		waveManager = new WaveManager(enemyTypes, 3, 2);
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackGround = QuickLoad("menu_background_2");
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.createMenu("TowerPicker", 800, 80, 200, 600, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("BlueCannon", "cannonBaseBlue");
		towerPickerMenu.quickAdd("CannnonGun", "cannonBase");
	}

	private void updateUI() {
		gameUI.draw();
		gameUI.drawString(820, 300, "Lives: " + player.Lives);
		gameUI.drawString(820, 400, "Cash: " + player.Cash);
		gameUI.drawString(820, 500, "Wave " + waveManager.getWaveNumber());
		gameUI.drawString(0, 0, StateManager.framesInLastSecond + " fps");
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			if (mouuseClicked) {
				if (towerPickerMenu.isButtonClicked("BlueCannon")) // Bat su kien khi click vao button
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (towerPickerMenu.isButtonClicked("CannnonGun")) // bat su kien khi click vao button
					player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}

	public void update() {
		DrawQuadTex(menuBackGround, 800, 0, 200, 600);
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}

}
