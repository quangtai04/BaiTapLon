package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Mouse;

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
		towerPickerUI.addButton("CannonBlue", "cannonGunBlue", 0, 0);
		towerPickerUI.addButton("CannonIce", "cannonGun", 40, 0);
	}

	private void updateUI() {
		towerPickerUI.draw();
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			if(mouuseClicked) {
				if (towerPickerUI.isButtonClicked("CannonBlue"))
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (towerPickerUI.isButtonClicked("CannonIce"))
					player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}

	public void update() {

		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}

}
