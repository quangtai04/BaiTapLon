package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.*;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Mouse;

import UI.Button;
import UI.UI;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI towerPickerUI;

	public Game(int[][] map) {
		grid = new TileGrid(map);
		waveManager = new WaveManager(
				new Enemy(QuickLoad("ufo"), grid.getTile(10, 8), grid, TILE_SIZE, TILE_SIZE, 70, 25), 2, 2);
		player = new Player(grid, waveManager);
		player.setup();
		towerPickerUI = new UI();
		setupUI();
	}

	private void setupUI() {
		towerPickerUI = new UI();
		//towerPickerUI.addButton("CannonBlue", "cannonGunBlue", 0, 0,40,40);
		//towerPickerUI.addButton("CannonIce", "cannonGun", 40, 0,40,40);
		towerPickerUI.createMenu("TowerPicker", 800, 0,200,600, 2, 0);
		towerPickerUI.getMenu("TowerPicker").addButton(new Button("CannonBlue", QuickLoad("cannonBaseBlue"), 0, 0,40,40));	// Load anh Button
		towerPickerUI.getMenu("TowerPicker").addButton(new Button("CannnonGun", QuickLoad("cannonBase"), 0, 0,40,40));      // Load anh Button
		towerPickerUI.getMenu("TowerPicker").addButton(new Button("CannnonGun", QuickLoad("cannonBase"), 0, 0,40,40));      // Load anh Button
		towerPickerUI.getMenu("TowerPicker").addButton(new Button("CannnonGun", QuickLoad("cannonBase"), 0, 0,40,40));      // Load anh Button
	}

	private void updateUI() {
		towerPickerUI.draw();
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			if(mouuseClicked) {
				if (towerPickerUI.getMenu("TowerPicker").isButtonClicked("CannonBlue"))				// Bat su kien khi click vao button
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (towerPickerUI.getMenu("TowerPicker").isButtonClicked("CannnonGun"))		// bat su kien khi click vao button
					player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}

	public void update() {
		DrawQuadTex(QuickLoad("menu_background"), 800, 0, 200, 600);
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}

}
