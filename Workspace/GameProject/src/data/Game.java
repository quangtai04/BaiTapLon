package data;

import static helpers.Artist.QuickLoad;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager  waveManager;
	
	//Temp Variables
	TowerCannon tower;
	
	public Game(int [][]map) {
		grid = new TileGrid(map);
		player = new Player(grid);
		waveManager = new WaveManager(new Enemy(QuickLoad("ufo"), grid.GetTile(10, 8), grid, 40, 40, 40),4,5);
		
		tower = new TowerCannon(QuickLoad("cannonBase"), grid.GetTile(14, 7), 10);
	}
	public void update() {
		
		grid.Draw();
		waveManager.update();
		player.Update();
		
		tower.update();
	}
	
}
